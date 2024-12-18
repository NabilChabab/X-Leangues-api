pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        SONAR_HOST_URL = 'http://host.docker.internal:9000'
        SONAR_TOKEN = 'sqa_a8fb74c0b952480d7bf8ef4805dff5acfb81a138'  // Use Jenkins credentials
    }

    stages {
            stage('Cleanup Workspace') {
                steps {
                    cleanWs()
                }
            }

            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'https://github.com/NabilChabab/X-Leangues-api.git'
                }
            }

            stage('Build') {
                steps {
                    sh 'mvn clean package -DskipTests'
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv('SonarQube Server') {
                        sh '''
                            mvn verify sonar:sonar \
                            -Dsonar.projectKey=x-leagues \
                            -Dsonar.projectName="X Leagues" \
                            -Dsonar.host.url=${SONAR_HOST_URL}
                        '''
                    }
                }
            }

            stage('Quality Gate') {
                steps {
                    script {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }

        post {
            success {
                echo "Pipeline completed successfully!"
            }
            failure {
                echo "Pipeline encountered an error."
            }
        }
}
