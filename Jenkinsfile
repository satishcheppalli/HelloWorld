pipeline {
    agent any
      environment {
       def scmVars="";
	def image="\$ (docker images | grep 'helloworld' | awk { print \$3 })"	      
    }

    stages {
        stage('Build jar file') { 
            steps {
		withMaven(maven: 'Maven339', jdk: 'JDK8', mavenSettingsConfig: 'bb40ec16-56e1-440a-8fdd-97af1a8b248f', mavenLocalRepo: '${BASE}/maven-repositories/${EXECUTOR_NUMBER}', options: [artifactsPublisher(disabled: true)])
		{
			sh "mvn clean install -DskipTests"
		}
            }
        }    
        stage('Build and Create docker image') { 
            steps {
                script {
                        scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: 'https://github.com/satishcheppalli/HelloWorld.git'
                          ]],
                        branches: [ [name: '*/master'] ]
                      ])
                sh "docker build -f Dockerfile -t iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID} ." 
		//sh "docker build -f Dockerfile -t iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest ."	
			sh "docker tag ${env.image} iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest"	
                }
            }
        }
        stage('Push image to OCIR') { 
            steps {
                script {
                sh "docker login -u 'fedexoraclecloud/oracleidentitycloudservice/2750344' -p 'Ur6G[M>frZ5qMsWp{<QP' iad.ocir.io"
                sh "docker push iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID}" 
		// sh "docker push iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest"	
                //env.GIT_COMMIT = scmVars.GIT_COMMIT
                //sh "export GIT_COMMIT=${env.GIT_COMMIT}"
		sh "docker rmi -f ${env.image}"
                }
               }
            }
        
        stage('Deploy Application') {  
			steps {	
				script {	
				   // sh("sed -i 's#iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest#iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID}#g' ./k8s/dev/*.yml")   			
				    sh("kubectl --namespace=satish-ns apply -f k8s/dev/deployment.yml")
				    sh("kubectl --namespace=satish-ns apply -f k8s/dev/service.yml")
						}
					}
			  }
		}
}
