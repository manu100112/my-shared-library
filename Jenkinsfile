pipeline {
    agent {
        label ''
    }

    options {
        buildDiscarder(logRotator(daysToKeepStr: '7'))
        timestamps()
    }
    
    environment {
        _JAVA_OPTIONS = '-Djava.io.tmpdir="${WORKSPACE}/tmp"'
    }
    
    stages {
        stage('Build Triggers') {
            parallel {
                stage('Scan Demo') {
                    steps {
                        build job: 'scan-demo', propagate: true
                    }
                }
                stage('Scan IEU') {
                    steps {
                        build job: 'scan-ieu', propagate: true
                    }
                }
            }
        }
        
        stage('Build Environment') {
            parallel {
                stage('Delete Workspace Demo') {
                    steps {
                        deleteDir()
                    }
                }
                stage('Delete Workspace IEU') {
                    steps {
                        deleteDir()
                    }
                }
            }
        }
   
        stage('Artifacts') {
            parallel {
                stage('Copy Artifacts Demo') {
                    steps {
                        copyArtifacts(projectName: 'scan-demo', target: 'release/scan-demo', fingerprintArtifacts: true)
                    }
                }
                stage('Copy Artifacts IEU') {
                    steps {
                        copyArtifacts(projectName: 'scan-ieu', target: 'release/scan-ieu', fingerprintArtifacts: true)
                    }
                }
            }
        }
        
        stage('Shell Execution') {
            parallel {
                stage('Shell Execution Demo') {
                    steps {
                        sh './scan-demo.sh'
                    }
                }
                stage('Shell Execution IEU') {
                    steps {
                        sh './scan-ieu.sh'
                    }
                }
            }
        }
    }
}
