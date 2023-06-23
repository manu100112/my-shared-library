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

        /*stage('Artifacts') {
            steps {
                parallel(
                    "scan-demo": {
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
        parallel(
            "scan-demo": {
                script {
                    runScanJob("jt")
                }
            },
            "scan-ieu": {
                script {
                    runScanJob("ieu")
                }
            }
        )
    }
}

def runScanJob(String prefix) {
    return {
        // Steps for Scan Job
        def scriptFilePath = "${env.WORKSPACE}/${prefix}.sh"
        sh "chmod +x ${scriptFilePath}" // Ensure the shell script is executable
        sh "bash ${scriptFilePath}" // Execute the shell script
    }
}
}
