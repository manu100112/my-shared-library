pipeline {
    agent {
        node {
            label ''
        }
    }

    options {
        buildDiscarder(logRotator(daysToKeepStr: '7')) // Keep builds for 7 days
    }

    environment {
        _JAVA_OPTIONS = '-Djava.io.tmpdir="${WORKSPACE}/tmp"'
    }

    stages {
        stage('Build Environment') {
            steps {
                deleteDir() // Delete workspace before build starts
                timeout(time: 480, unit: 'MINUTES') {
                    // Add steps or actions inside the timeout block if needed
                }
            }
        }

        stage('Clone Repository') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: 'main']], // Specify the branch you want to clone
                    userRemoteConfigs: [[
                        url: 'https://github.com/manu100112/my-shared-library.git', // Specify the URL of the repository
                        credentialsId: 'b1936281-4135-48cc-a606-92c35202591b' // Optional: If authentication is required
                    ]]
                ])
            }
        }

        stage('Shell Execution') {
            steps {
                catchError(buildResult: 'UNSTABLE') {
                script {
                    def shellScripts = [
                    "scan-demo.sh",
                    "scan-accessair.sh",
                    "scan-dns.sh",
                    "scan-sie.sh",
                    "scan-smeg.sh",
                    "scan-tangibility.sh",
                    "scan-toast.sh",
                    "scan-ieu.sh"
                ]

                for (def scriptFile : shellScripts) {
                    def scriptOutput = sh(
                        returnStdout: true,
                        script: "chmod a+x ${scriptFile} && ./${scriptFile}"
                    )

                    // Print the script output
                    println(scriptOutput)
                }
            }
        }
    }
}
