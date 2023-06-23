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
            parallel {
                stage('scan-demo') {
                    steps {
                        buildJob('scan-demo')
                    }
                }
                stage('scan-ieu') {
                    steps {
                        buildJob('scan-ieu')
                    }
                }
            }
        }
        
        stage('Build Environment') {
            parallel {
                stage('scan-demo') {
                    steps {
                        deleteWorkspace('scan-demo')
                    }
                }
                stage('scan-ieu') {
                    steps {
                        deleteWorkspace('scan-ieu')
                    }
                }
            }
        }
   
        stage('Artifacts') {
            parallel {
                stage('scan-demo') {
                    steps {
                        copyArtifacts(jobName: 'scan-demo', projectNames: ['packageJT', 'commit'], artifactNames: ['scan-demo-artifact.jar', 'scan-demo-commit.txt'])
                    }
                }
                stage('scan-ieu') {
                    steps {
                        copyArtifacts(jobName: 'scan-ieu', projectNames: ['LegacyBuildV2', 'commit'], artifactNames: ['scan-ieu-artifact.jar', 'scan-ieu-commit.txt'])
                    }
                }
            }
        }
        
        stage('Shell Execution') {
            parallel {
                stage('scan-demo') {
                    steps {
                        executeShell('scan-demo', 'scan-demo.sh')
                    }
                }
                stage('scan-ieu') {
                    steps {
                        executeShell('scan-ieu', 'scan-ieu.sh')
                    }
                }
            }
        }
    }
}

def buildJob(jobName) {
    stage('Build') {
        steps {
            build job: jobName, propagate: true
        }
    }
}

def deleteWorkspace(jobName) {
    stage('Delete Workspace') {
        steps {
            deleteDir()
        }
    }
}

def copyArtifacts(jobName, projectNames, artifactNames) {
    stage('Copy Artifacts') {
        steps {
            for (int i = 0; i < projectNames.size(); i++) {
                def projectName = projectNames[i]
                def artifactName = artifactNames[i]
                copyArtifacts(projectName: projectName, target: "release/${artifactName}", fingerprintArtifacts: true)
            }
        }
    }
}

def executeShell(jobName, scriptName) {
    stage('Shell Execution') {
        steps {
            sh "./${scriptName}" // Execute the shell script
        }
    }
}
