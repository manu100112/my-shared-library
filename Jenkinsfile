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


        stage('Build Triggers') {
            steps {
                build(job: 'build', propagate: true, wait: true)
            }
        }

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
        
        stage('Artifacts') {
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
        }

        stage('Shell Execution') {
            steps {
                parallel(
                    "scan-demo": {
                        script {
                            sh 'chmod a+x scan-demo.sh'
                            sh './scan-demo.sh'
                    
                        }
                    },
                    "scan-airaccess": {
                        script {
                            sh 'chmod a+x scan-accessair.sh'
                            sh './scan-accessair.sh'
                        }
                    },
                    "scan-dns": {
                        script {
                            sh 'chmod a+x scan-dns.sh'
                            sh './scan-dns.sh'
                        }
                    },
                    "scan-sie": {
                        script {
                            sh 'chmod a+x scan-sie.sh'
                            sh './scan-sie.sh'
                        }
                    },
                    "scan-smeg": {
                        script {
                            sh 'chmod a+x scan-smeg.sh'
                            sh './scan-smeg.sh'
                        }
                    },
                    "scan-tangibility": {
                        script {
                            sh 'chmod a+x scan-tangibility.sh'
                            sh './scan-tangibility.sh'
                        }
                    },
                    "scan-toast": {
                        script {
                            sh 'chmod a+x scan-toast.sh'
                            sh './scan-toast.sh'
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
