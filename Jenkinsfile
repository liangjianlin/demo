pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Build'
                sh 'rm -rf demo'
                sh 'git clone https://github.com/liangjianlin/demo.git'
            }
        }
        stage('Test'){
            steps {
               echo 'Test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy'
                sh 'pwd'
                sh 'mvn spring-boot:run'
            }
        }
    }
}