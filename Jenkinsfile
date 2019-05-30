pipeline {
    agent any
    
    stages {

        stage('Build jar file') { 
            steps {
                script {
                  sh "mvn clean install -DskipTests" 
                }
            }
        }        
        stage('Build and Create docker image') { 
            steps {
                script {
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: 'https://github.com/satishcheppalli/HelloWorld.git'
                          ]],
                        branches: [ [name: '*/master'] ]
                      ])
                sh "docker build -f Dockerfile -t iad.ocir.io/fedexoraclecloud/fsc/HelloWorld:${scmVars.GIT_COMMIT} ." 
                }
            }
        }
        stage('Push image to OCIR') { 
            steps {
                script {
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: 'https://github.com/satishcheppalli/HelloWorld.git'
                          ]],
                        branches: [ [name: '*/master'] ]
                      ])
                sh "docker login -u 'fedexoraclecloud/oracleidentitycloudservice/2750344' -p 'Ur6G[M>frZ5qMsWp{<QP' iad.ocir.io"
    
                sh "docker push iad.ocir.io/fedexoraclecloud/fsc/HelloWorld:${scmVars.GIT_COMMIT}" 
                env.GIT_COMMIT = scmVars.GIT_COMMIT
                sh "export GIT_COMMIT=${env.GIT_COMMIT}"
                }
               }
            }
        
        stage('Deploy Application') {  
			steps {	
				script {
					  def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: 'https://github.com/satishcheppalli/HelloWorld.git'
                          ]],
                        branches: [ [name: '*/master'] ]
                      ])
					
           // sh("kubectl get ns ${namespace} || kubectl create ns ${namespace}")    
            sh("sed -i 's#iad.ocir.io/fedexoraclecloud/fsc/HelloWorld:latest#iad.ocir.io/fedexoraclecloud/fsc/HelloWorld:${scmVars.GIT_COMMIT}#g' ./k8s/satish-ns/*.yml")    
            sh("kubectl --namespace=satish-ns apply -f k8s/satish-ns/deployment.yml")
            sh("kubectl --namespace=satish-ns apply -f k8s/satish-ns/service.yml")        
               
						}
					}
			  }
		}
	}
}