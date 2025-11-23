pipeline {
    agent any
    
    environment {
        // Remplacez par votre username Docker Hub
        DOCKERHUB_USERNAME = 'votre-username-dockerhub'
        IMAGE_NAME = 'test-devops'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Récupération du code depuis GitHub...'
                git branch: 'main',
                    url: 'https://github.com/Achrefs16/test-devops.git'
            }
        }
        
        stage('Build Maven') {
            steps {
                echo '🔨 Compilation du projet Spring Boot...'
                sh '''
                    mvn clean package -DskipTests
                    ls -lh target/
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo '🧪 Exécution des tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo '🐳 Construction de l\'image Docker...'
                sh '''
                    docker build -t ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} .
                    docker tag ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest
                '''
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                echo '📤 Push de l\'image vers Docker Hub...'
                sh '''
                    echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin
                    docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}
                    docker push ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest
                    docker logout
                '''
            }
        }
        
        stage('Clean Local Images') {
            steps {
                echo '🧹 Nettoyage des images locales...'
                sh '''
                    docker rmi ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG} || true
                    docker rmi ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest || true
                '''
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline exécuté avec succès!'
            echo "Image disponible : ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:${IMAGE_TAG}"
            echo "Image disponible : ${DOCKERHUB_USERNAME}/${IMAGE_NAME}:latest"
        }
        failure {
            echo '❌ Le pipeline a échoué. Consultez les logs.'
        }
        always {
            echo '🔍 Nettoyage de l\'espace de travail...'
            cleanWs()
        }
    }
}
