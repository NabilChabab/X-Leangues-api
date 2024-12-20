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
        // Add Docker Hub credentials
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
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
                sh """
                    docker build -t ${DOCKER_IMAGE}:latest \
                    --build-arg DB_URL="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}" \
                    --build-arg DB_USERNAME="${DB_USER}" \
                    --build-arg DB_PASSWORD="${DB_PASSWORD}" \
                    --build-arg SERVER_PORT="${APP_PORT}" \
                    .
                """
            }
        }

        stage('Setup Docker Network') {
            steps {
                sh """
                    if ! docker network inspect ${DOCKER_NETWORK} >/dev/null 2>&1; then
                        echo "Creating Docker network: ${DOCKER_NETWORK}"
                        docker network create ${DOCKER_NETWORK}
                    else
                        echo "Docker network ${DOCKER_NETWORK} already exists."
                    fi
                """
            }
        }

        stage('Clean Up Existing Container') {
            steps {
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

        stage('Run Docker Container') {
            steps {
                sh """
                    docker run -d \
                        --name hunter-league-app \
                        --network ${DOCKER_NETWORK} \
                        -p ${APP_PORT}:${APP_PORT} \
                        -e SPRING_DATASOURCE_URL="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}" \
                        -e SPRING_DATASOURCE_USERNAME="${DB_USER}" \
                        -e SPRING_DATASOURCE_PASSWORD="${DB_PASSWORD}" \
                        -e SERVER_PORT="${APP_PORT}" \
                        ${DOCKER_IMAGE}:latest

                    sleep 10

                    if ! docker ps | grep -q hunter-league-app; then
                        echo "Application container failed to start."
                        docker logs hunter-league-app
                        exit 1
                    fi
                """
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                    sh """
                        echo ${DOCKER_HUB_PASSWORD} | docker login -u ${DOCKER_HUB_USERNAME} --password-stdin
                        docker tag ${DOCKER_IMAGE}:latest ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE}:latest
                        docker push ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE}:latest
                    """
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
