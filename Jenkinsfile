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
                    cleanWs() // Ensure the workspace is clean before starting
                }
            }

            stage('Checkout') {
                steps {
                    git branch: 'main', url: 'https://github.com/NabilChabab/X-Leangues-api.git'
                }
            }

            stage('Build') {
                steps {
                    sh 'mvn clean install -DskipTests' // Using install for complete build
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv('SonarQube Server') {
                        sh '''
                            mvn sonar:sonar \
                            -Dsonar.projectKey=com.example:x_leagues \
                            -Dsonar.projectName="x_leagues" \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                }
            }

            stage('Quality Gate') {
                steps {
                    waitForQualityGate abortPipeline: true // Abort if quality gate fails
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
