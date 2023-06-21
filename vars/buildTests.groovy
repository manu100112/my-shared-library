def call() {
    pipeline {
        agent any

        stages {
            stage('Test') {
                steps {
                    // Test steps for your project
                    sh 'echo "Running tests"'
                    sh 'mvn test'
                }
            }
        }
    }
}
