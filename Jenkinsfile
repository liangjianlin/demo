pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Build'
                git clone https://github.com/liangjianlin/demo.git
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
                cd ./demo && mvn clean install
            }
        }
    }
}