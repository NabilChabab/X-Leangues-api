pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    environment {
        SONAR_HOST_URL = 'http://host.docker.internal:9000'
        SONAR_TOKEN = 'sqa_b444e00ce0c9e9e807b1ba908442bd1547754b11'
        DOCKER_IMAGE = "samurai"
                DOCKER_NETWORK = "samurai-network"
                DB_HOST = "postgres"
                DB_PORT = "5433"
                DB_NAME = "samurai"
                DB_USER = "samurai"
                DB_PASSWORD = "password"
                APP_PORT = "8082"
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
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.projectKey=x-leagues \
                            -Dsonar.projectName='X Leagues' \
                            -Dsonar.host.url=http://host.docker.internal:9000 \
                            -Dsonar.token=${SONAR_TOKEN}
                        """
                    }
                }
            }

           stage('Quality Gate Check') {
                       steps {
                           script {
                               def qualityGate = sh(
                                   script: """
                                   curl -s -u "$SONAR_TOKEN:" \
                                   "$SONAR_HOST_URL/api/qualitygates/project_status?projectKey=x-leagues" \
                                   | jq -r '.projectStatus.status'
                                   """,
                                   returnStdout: true
                               ).trim()
                               if (qualityGate != "OK") {
                                   error "Quality Gate failed! Stopping the build."
                               }
                           }
                       }
                   }
                   stage('Build Docker Image') {
                               steps {
                                   script {
                                       docker.withServer('unix:///var/run/docker.sock') {
                                           sh """
                                               docker build -t ${DOCKER_IMAGE}:latest \
                                               --build-arg DB_URL="jdbc:postgresql://\${DB_HOST}:\${DB_PORT}/\${DB_NAME}" \
                                               --build-arg DB_USERNAME="\${DB_USER}" \
                                               --build-arg DB_PASSWORD="\${DB_PASSWORD}" \
                                               --build-arg SERVER_PORT="\${APP_PORT}" \
                                               .
                                           """
                                       }
                                   }
                               }
                           }

                           stage('Setup Docker Network') {
                               steps {
                                   script {
                                       docker.withServer('unix:///var/run/docker.sock') {
                                           sh """
                                               # Create network if it doesn't exist
                                               docker network create ${DOCKER_NETWORK} || true
                                           """
                                       }
                                   }
                               }
                           }

                           stage('Clean Up Existing Container') {
                               steps {
                                   script {
                                       docker.withServer('unix:///var/run/docker.sock') {
                                           sh '''
                                               # Stop and remove container if it exists
                                               docker ps -a -q --filter "name=hunter-league-app" | xargs -r docker rm -f || true

                                               # Kill any process using our intended port
                                               if lsof -Pi :${APP_PORT} -sTCP:LISTEN -t >/dev/null ; then
                                                   lsof -Pi :${APP_PORT} -sTCP:LISTEN -t | xargs kill -9 || true
                                               fi
                                           '''
                                       }
                                   }
                               }
                           }

                           stage('Run Docker Container') {
                               steps {
                                   script {
                                       docker.withServer('unix:///var/run/docker.sock') {
                                           // Run new container
                                           sh """
                                               # Run the application container
                                               docker run -d \
                                               --name hunter-league-app \
                                               --network ${DOCKER_NETWORK} \
                                               -p \${APP_PORT}:\${APP_PORT} \
                                               -e SPRING_DATASOURCE_URL="jdbc:postgresql://\${DB_HOST}:\${DB_PORT}/\${DB_NAME}" \
                                               -e SPRING_DATASOURCE_USERNAME="\${DB_USER}" \
                                               -e SPRING_DATASOURCE_PASSWORD="\${DB_PASSWORD}" \
                                               -e SERVER_PORT="\${APP_PORT}" \
                                               ${DOCKER_IMAGE}:latest
                                           """

                                           // Wait for container to start
                                           sh """
                                               echo "Waiting for container to start..."
                                               sleep 10
                                               if ! docker ps | grep -q hunter-league-app; then
                                                   echo "Container failed to start. Showing logs:"
                                                   docker logs hunter-league-app
                                                   exit 1
                                               fi
                                           """
                                       }
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
