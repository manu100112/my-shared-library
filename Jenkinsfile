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
                script {
                    def shellScripts = [
                        "scan-demo": "scan-demo.sh",
                        "scan-airaccess": "scan-accessair.sh",
                        "scan-dns": "scan-dns.sh",
                        "scan-sie": "scan-sie.sh",
                        "scan-smeg": "scan-smeg.sh",
                        "scan-tangibility": "scan-tangibility.sh",
                        "scan-toast": "scan-toast.sh",
                        "scan-ieu": "scan-ieu.sh"
                    ]
                    
                    def failures = [:]
                    
                    shellScripts.each { scriptName, scriptFile ->
                        failures[scriptName] = {
                            stage(scriptName) {
                                steps {
                                    script {
                                        sh "chmod a+x ${scriptFile}"
                                        def scriptOutput = sh(
                                            returnStdout: true,
                                            returnStatus: true,
                                            script: "./${scriptFile}"
                                        )

                                        // Print the script output
                                        println(scriptOutput.out)

                                        // Check the script exit status
                                        if (scriptOutput.returnStatus != 0) {
                                            error("Shell script execution failed: ${scriptFile}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    parallel(failures)
                }
            }
        }
    }
}
