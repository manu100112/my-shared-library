// my-shared-library/vars/buildPipeline.groovy
def call() {
    // Build steps for your project
    sh 'echo "Building the project"'
    sh 'mvn clean package'
}
