pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Build'
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
            }
        }
    }
}