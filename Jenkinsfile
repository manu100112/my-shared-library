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
                parallel(
                    "scan-demo": {
                        script {
                            sh 'ls'
                            sh 'pwd'
                            sh 'chmod a+x scan-demo.sh'
                            sh 'scan-demo.sh > scan-demo.log'
                            sh 'ls'
                            sh 'cat scan-demo.log'
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
