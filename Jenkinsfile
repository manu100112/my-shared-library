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
                        script {
                            buildJob('scan-demo')
                        }
                    }
                }
                stage('scan-ieu') {
                    steps {
                        script {
                            buildJob('scan-ieu')
                        }
                    }
                }
            }
        }
        
        stage('Build Environment') {
            parallel {
                stage('scan-demo') {
                    steps {
                        script {
                            deleteWorkspace('scan-demo')
                        }
                    }
                }
                stage('scan-ieu') {
                    steps {
                        script {
                            deleteWorkspace('scan-ieu')
                        }
                    }
                }
            }
        }
   
        stage('Artifacts') {
            parallel {
                stage('scan-demo') {
                    steps {
                        script {
                            copyArtifacts('scan-demo', ['packageJT', 'commit'], ['scan-demo-artifact.jar', 'scan-demo-commit.txt'])
                        }
                    }
                }
                stage('scan-ieu') {
                    steps {
                        script {
                            copyArtifacts('scan-ieu', ['LegacyBuildV2', 'commit'], ['scan-ieu-artifact.jar', 'scan-ieu-commit.txt'])
                        }
                    }
                }
            }
        }
        
        stage('Shell Execution') {
            parallel {
                stage('scan-demo') {
                    steps {
                        script {
                            executeShell('scan-demo', 'scan-demo.sh')
                        }
                    }
                }
                stage('scan-ieu') {
                    steps {
                        script {
                            executeShell('scan-ieu', 'scan-ieu.sh')
                        }
                    }
                }
            }
        }
    }
}

def buildJob(jobName) {
    pipeline {
        agent {
        node {
            label ''
        }
    }

        stages {
            stage('Build') {
                steps {
                    script {
                        build job: jobName, propagate: true
                    }
                }
            }
        }
    }
}

def deleteWorkspace(jobName) {
    pipeline {
        agent {
        node {
            label ''
        }
    }

        stages {
            stage('Delete Workspace') {
                steps {
                    script {
                        deleteDir()
                    }
                }
            }
        }
    }
}

def copyArtifacts(jobName, projectNames, artifactNames) {
    pipeline {
        agent {
        node {
            label ''
        }
    }

        stages {
            stage('Copy Artifacts') {
                steps {
                    script {
                        for (int i = 0; i < projectNames.size(); i++) {
                            def projectName = projectNames[i]
                            def artifactName = artifactNames[i]
                            copyArtifacts(projectName: projectName, target: "release/${jobName}${artifactName}", fingerprintArtifacts: true)
                        }
                    }
                }
            }
        }
    }
}

def executeShell(jobName, scriptName) {
    pipeline {
        agent {
        node {
            label ''
        }
    }

        stages {
            stage('Shell Execution') {
                steps {
                    script {
                        sh "./${scriptName}" // Execute the shell script
                    }
                }
            }
        }
    }
}
