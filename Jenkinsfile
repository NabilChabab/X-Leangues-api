pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        SONAR_HOST_URL = 'http://host.docker.internal:9000'
        SONAR_TOKEN = 'sqa_b444e00ce0c9e9e807b1ba908442bd1547754b11'  // Use Jenkins credentials
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
