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
                cleanWs()  // More reliable workspace cleanup
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/ZudaPradana/sonar.git'
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
                        mvn sonar:sonar \
                        -Dsonar.projectKey=com.example:x_leagues \
                        -Dsonar.projectName='x_leagues' \
                        -Dsonar.host.url=${SONAR_HOST_URL}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
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
