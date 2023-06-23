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
                        executeShell(jobName: 'scan-demo', scriptName: 'scan-demo.sh')
                    }
                }
                stage('scan-ieu') {
                    steps {
                        executeShell(jobName: 'scan-ieu', scriptName: 'scan-ieu.sh')
                    }
                }
            }
        }
    }
}

def buildJob(jobName) {
    build job: jobName, propagate: true
}

def deleteWorkspace(jobName) {
    deleteDir()
}

def copyArtifacts(jobName, projectNames, artifactNames) {
    for (int i = 0; i < projectNames.size(); i++) {
        def projectName = projectNames[i]
        def artifactName = artifactNames[i]
        copyArtifacts(projectName: projectName, target: "release/${jobName}${artifactName}", fingerprintArtifacts: true)
    }
}

def executeShell(jobName, scriptName) {
    sh "./${scriptName}" // Execute the shell script
}
