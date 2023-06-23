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
                            sh 'ls'
                            sh 'pwd'
                            sh 'chmod a+x scan-demo.sh'
                            sh './scan-demo.sh'
                        }
                    },
                    "scan-ieu": {
                        script {
                            sh 'chmod a+x scan-ieu.sh'
                            sh './scan-ieu.sh'
                        }
                    }
                )
            }
        }
    }
}

