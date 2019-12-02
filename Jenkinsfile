pipeline {
    agent any
      environment {
	dockerImage ="";
	 DOCKER_IMAGE_TAG = "iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID}"      
    }

    stages {
        stage('Build jar file') { 
            steps {
		withMaven(maven: 'Maven339', jdk: 'JDK8', mavenSettingsConfig: 'bb40ec16-56e1-440a-8fdd-97af1a8b248f', mavenLocalRepo: '${BASE}/maven-repositories/${EXECUTOR_NUMBER}', options: [artifactsPublisher(disabled: true)])
		{
			sh "mvn clean install -U -Pint-test,analysis,sonar,${BUILD_ENVIRONMENT} -Dmaven.test.failure.ignore -DjacocoDirectory=\"${WORKSPACE}/jacoco\""
		}
            }
        }
		stage('Analyze') { 
            steps {
				withMaven(maven: 'Maven339', jdk: 'JDK8', mavenSettingsConfig: 'bb40ec16-56e1-440a-8fdd-97af1a8b248f', mavenLocalRepo: '${BASE}/maven-repositories/${EXECUTOR_NUMBER}', options: [artifactsPublisher(disabled: true)])
				{
					sh "sonar:sonar -U -Psonar,${BUILD_ENVIRONMENT} -DjacocoDirectory=\"${WORKSPACE}/jacoco\" -Dsonar.branch=master"
				}
            }
        }
		stage('Document') { 
            steps {
				withMaven(maven: 'Maven339', jdk: 'JDK8', mavenSettingsConfig: 'bb40ec16-56e1-440a-8fdd-97af1a8b248f', mavenLocalRepo: '${BASE}/maven-repositories/${EXECUTOR_NUMBER}', options: [artifactsPublisher(disabled: true)])
				{
					sh "site:site site:deploy -U -P GENCO -DjciteCommandPath=\"/home/jksadmin/.jenkins/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/JCite/jcite-1.13.0/bin/jcite.sh\""
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
                //sh "docker build -f Dockerfile -t iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID} ." 
		dockerImage = docker.build("${env.DOCKER_IMAGE_TAG}",  '-f ./Dockerfile .')	
                }
            }
        }
        stage('Push image to OCIR') { 
            steps {
                script {
			sh "docker login -u 'fedexoraclecloud/oracleidentitycloudservice/2750344' -p 'Ur6G[M>frZ5qMsWp{<QP' iad.ocir.io"
			dockerImage.push()
			sh "docker rmi -f ${dockerImage.id}"
                }
               }
            }
        
        stage('Deploy Application') {  
		steps {	
			script {	
			    sh("sed -i 's#iad.ocir.io/fedexoraclecloud/fsc/helloworld:latest#iad.ocir.io/fedexoraclecloud/fsc/helloworld:${env.BUILD_ID}#g' ./k8s/dev/*.yml")   			
			    sh("kubectl --namespace=satish-ns apply -f k8s/dev/deployment.yml")
			    sh("kubectl --namespace=satish-ns apply -f k8s/dev/service.yml")
				}
			}
		  }
	}
}
