pipeline {
    agent any

    parameters {
        string(name: 'DOCKER_REGISTRY', defaultValue: 'mahfuzhasan/devops-infinity', description: 'Docker registry')
    }

    environment {
        DOCKER_REGISTRY = "${params.DOCKER_REGISTRY}"
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk-amd64'
        MAVEN_HOME = '/usr/share/maven'
    }

    stages {
        stage('Checkout') {
            steps {
                retry(3) {
                    checkout scm
                }
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    retry(3) {
                        sh "mvn clean install"
                    }
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true

            }
        }

        stage('Docker Compose Build') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    retry(3) {
                        sh "docker-compose build"
                    }
                }
            }
        }

        stage('Docker Compose Up') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    retry(3) {
                        sh "docker-compose up -d"
                    }
                }
            }
        }

        stage('Push Docker Image') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    withDockerRegistry([credentialsId: 'dockerHubCredentials', url: '']) {  // Replace with your Docker Hub credentials ID
                        def tag = env.BRANCH_NAME.replaceAll('/', '-')
                        retry(3) {
                            sh "docker push ${DOCKER_REGISTRY}:${tag}"
                        }
                    }
                }
            }
        }

        stage('Deploy with Ansible') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'myCreds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                        def tag = env.BRANCH_NAME.replaceAll('/', '-')
                        retry(3) {
                            sh "ansible-playbook -i inventory.ini deploy_app.yml --extra-vars 'jenkins_workspace=${WORKSPACE}'"
                        }
                        echo "Deploying the ${tag} branch using Ansible."
                    }
                }
            }
        }
    }

    post {
        always {
            sh "docker-compose down"
        }
    }
}