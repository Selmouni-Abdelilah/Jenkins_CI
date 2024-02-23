pipeline{
    agent any
    environment {     
        DOCKERHUB_CREDENTIALS= credentials('dockerhubcredentials')     
    } 
    stages{
        stage('Fetch the code') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'GitHubcredentials', url: 'https://github.com/Selmouni-Abdelilah/Jenkins_CI.git']])
            }
        }
        stage('Maven Build'){
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
            post {
                success {
                    echo 'Archiving Artifacts'
                    archiveArtifacts artifacts: 'target/*.war'
                }
            }
        }
        stage('Code Quality Analysis + SAST'){
            steps {
                script {
                    def scannerHome = tool 'Sonar-Scanner';
                    withSonarQubeEnv(credentialsId: 'token_sonar',installationName:'Sonarqube'){
                        //replace http://localhost:9090 with your sonarQube server Url
                        sh """ ${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=library \
                        -Dsonar.projectName=CICD \
                        -Dsonar.host.url='http://localhost:9090' \
                        -Dsonar.sources=src \
                        -Dsonar.java.binaries=target """
                    }
                }       
            }
        }
        stage("Quality Gate"){
            steps{
                timeout(time: 1, unit: 'HOURS') {
                    script{
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
        stage('Maven Test'){
            steps{
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        
        }
        stage('Build Docker Image') {         
            steps{                
                sh 'docker build --rm -t abdelilahone/jenkinsci:$BUILD_NUMBER .'              
      }           
    }
    stage('Login to Docker Hub') {         
        steps{                            
	        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'                            
      }           
    }               
    stage('Push Image to Docker Hub') {         
         steps{                            
	        sh 'docker push abdelilahone/jenkinsci:$BUILD_NUMBER'   
      }           
    } 

    }
}
