node {
    //agent any
	def env = 'dev'
	def project = 'fsc'  
	def appName = 'helloworld'    
	def imageVersion = "${BUILD_NUMBER}.0.0"
	def namespace = 'satish-ns'  
	def image = "iad.ocir.io/fedexoraclecloud/${project}/${appName}"
	def gitRepoUrl = 'https://github.com/satishcheppalli/HelloWorld.git'
	def branch = '*/master'
	def userName = 'fedexoraclecloud/oracleidentitycloudservice/2750344'
	def pwd = 'Ur6G[M>frZ5qMsWp{<QP'
    
//    stages {

        stage('Build jar file') { 
          //  steps {
                //script {
                  //sh "mvn clean install -DskipTests" 
                //}
				withMaven(maven: 'Maven339', jdk: 'JDK8', mavenSettingsConfig: 'bb40ec16-56e1-440a-8fdd-97af1a8b248f', mavenLocalRepo: '${BASE}/maven-repositories/${EXECUTOR_NUMBER}', options: [artifactsPublisher(disabled: true)])
					{
							sh "mvn clean install -DskipTests"
					}
          //  }
        }        
        stage('Build and Create docker image') { 
           // steps {
                script {
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: "${gitRepoUrl}"
                          ]],
                        branches: [ [name: "${branch}"] ]
                      ])
                sh "docker build -f Dockerfile -t ${image}:${imageVersion} ." 
                }
           // }
        }
        stage('Push image to OCIR') { 
           // steps {
                script {
                    def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: "${gitRepoUrl}"
                          ]],
                        branches: [ [name: '*/master'] ]
                      ])
                sh "docker login -u ${userName} -p 'Ur6G[M>frZ5qMsWp{<QP' iad.ocir.io"
    
                sh "docker push ${image}:${imageVersion}" 
              //  env.GIT_COMMIT = "${imageVersion}"
              //  sh "export GIT_COMMIT=${env.GIT_COMMIT}"
                }
             //  }
            }
        
        stage('Deploy Application') {  
			//steps {	
				script {
					/*  def scmVars = checkout([
                        $class: 'GitSCM',
                        doGenerateSubmoduleConfigurations: false,
                        userRemoteConfigs: [[
                            url: "${gitRepoUrl}"
                          ]],
                        branches: [ [name: "${branch}"] ]
                      ])*/
					
           // sh("kubectl get ns ${namespace} || kubectl create ns ${namespace}")    
            //sh("sed -i 's#${image}:latest#${image}:${imageVersion}#g' ./k8s/${env}/*.yml") 
			sh("sed -i 's#iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest#iad.ocir.io/fedexoraclecloud/fsc/helloworld:${imageVersion}#g' ./k8s/${env}/*.yml")
			///sh("sed -i 's#namespace: dev#namespace: satish-ns#g' ./k8s/*.yml")    			
            sh("kubectl --namespace=${namespace} apply -f k8s/${env}/deployment.yml")
            sh("kubectl --namespace=${namespace} apply -f k8s/${env}/service.yml")        
               //sh("kubectl apply -f k8s/deployment.yml")
			   //sh("kubectl apply -f k8s/service.yml")   
						}
				//	}
			  }
//		}
}