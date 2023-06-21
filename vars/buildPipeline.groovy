def call() {
    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    // Build steps for your project
                    sh 'echo "Building the project"'
                    sh 'mvn clean package'
                }
            }
        }
    }
}
