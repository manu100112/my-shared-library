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


        /*stage('Build Triggers') {
            steps {
                build(job: 'build', propagate: true, wait: true)
            }
        }*/

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
        
        /*stage('Artifacts') {
            steps {
                parallel(
                    "scan-demo": {
                        copyArtifacts(projectName: 'packageJT', target: 'release/jtInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-airaccess": {
                        copyArtifacts(projectName: 'packageJT', target: 'release/jtInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-dns": {
                        copyArtifacts(projectName: 'packageJT', target: 'release/jtInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-sie": {
                        copyArtifacts(projectName: 'LegacyBuildV2', target: 'release/sieInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-smeg": {
                        copyArtifacts(projectName: 'PackageSMEG', target: 'release/smegInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-tangibility": {
                        copyArtifacts(projectName: 'LegacyBuildV2', target: 'release/tanInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-toast": {
                        copyArtifacts(projectName: 'packageJT', target: 'release/jtInstaller.jar,release/commit', fingerprintArtifacts: true)
                    },
                    "scan-ieu": {
                        copyArtifacts(projectName: 'LegacyBuildV2', target: 'release/ieuInstaller.jar,release/commit', fingerprintArtifacts: true)
                    }
                )
            }
        }*/

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
