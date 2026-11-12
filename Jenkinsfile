// ==========================================================
// Jenkins Declarative Pipeline: CCS342 DevOps Assignment
// Student: KALAIVARSAN V (Roll No: 25)
// Topic: Sensor Calibration Management System
// ==========================================================

pipeline {
    agent any

    environment {
        APP_NAME = 'sensor-calibration-system'
        DOCKER_IMAGE = 'kalaivarsan/sensor-calibration-mgmt'
        BUILD_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {
        stage('1. Checkout SCM') {
            steps {
                echo '=== Stage 1: Checking out code from GitHub ==='
                git branch: 'main', url: 'https://github.com/KALAIVARSAN2006/sensor-calibration-system.git'
            }
        }

        stage('2. Maven Compile & Static Analysis') {
            steps {
                echo '=== Stage 2: Compiling Java Sources and POM verification ==='
                bat 'mvn clean compile'
            }
        }

        stage('3. Run Automated Unit & Drift Tests') {
            steps {
                echo '=== Stage 3: Running JUnit 5 Sensor Drift Calculation Tests ==='
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4. Package Artifact') {
            steps {
                echo '=== Stage 4: Packaging Spring Boot Executable JAR ==='
                bat 'mvn package -DskipTests'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('5. Docker Build & Tag') {
            steps {
                echo '=== Stage 5: Building Container Image ==='
                bat "docker build -t ${DOCKER_IMAGE}:${BUILD_VERSION} -t ${DOCKER_IMAGE}:latest ."
            }
        }

        stage('6. Ansible Provision & Deploy') {
            steps {
                echo '=== Stage 6: Running Ansible Playbook for Deployment ==='
                bat 'ansible-playbook -i devops/ansible/hosts.ini devops/ansible/deploy.yml --extra-vars "app_version=${BUILD_VERSION}"'
            }
        }

        stage('7. Health & Smoke Test') {
            steps {
                echo '=== Stage 7: Verifying Production REST API Health ==='
                bat 'curl -f http://localhost:8080/api/health || exit 1'
            }
        }
    }

    post {
        success {
            echo "SUCCESS: Pipeline completed successfully for ${APP_NAME} Build #${BUILD_NUMBER}"
        }
        failure {
            echo "FAILURE: Build #${BUILD_NUMBER} failed. Sending alert notification."
        }
    }
}