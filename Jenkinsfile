pipeline {
    agent any
    tools {
        maven 'Maven' // Maven configuré dans Jenkins
    }

    environment {
        SONAR_HOST_URL = 'http://host.docker.internal:9000'  // URL du serveur SonarQube
        SONAR_TOKEN = 'sqa_a8fb74c0b952480d7bf8ef4805dff5acfb81a138' // Token valide
    }

    stages {
        stage('Cleanup Workspace') {
            steps {
                echo "Cleaning up workspace..."
                deleteDir() // Nettoie complètement le workspace
            }
        }

        stage('Checkout') {
            steps {
                echo "Checking out code from Git repository..."
                sh "git clone https://github.com/ZudaPradana/sonar ."
            }
        }

        stage('Build') {
            steps {
                echo "Building the project with Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "Running SonarQube analysis..."
                withSonarQubeEnv('SonarQube Server') { // Assurez-vous que 'SonarQube Server' est configuré
                    sh """
                        mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=com.example:x_leagues \
                        -Dsonar.projectName='x_leagues' \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    echo "Waiting for SonarQube Quality Gate result..."
                    timeout(time: 5, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Quality Gate failed with status: ${qg.status}"
                        } else {
                            echo "Quality Gate passed successfully!"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
