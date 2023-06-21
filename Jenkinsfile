@Library('my-shared-library') 

def buildPipeline() {
    my-shared-library.buildPipeline()
}

def buildTests() {
    my-shared-library.buildTests()
}




pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    buildPipeline()
                }
            }
        }

        // Other stages of the pipeline

        stage('Test') {
            steps {
                script {
                    buildTests()
                }
            }
        }
    }
}
