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
                            sh '#!/bin/bash
set -e


### Download Depencenes from S3 ####
_st="${HOME}/.st"
mkdir -p "${_st}"
time aws s3 sync --quiet s3://sts-devops/_st "${_st}"


#sleep 30
#echo failing task
#cd thisisnotalocation


# clear docker files
docker system prune -f
set +e
docker rm -f $(docker ps -a -q)
set -e

# Clear/Create a temporary directory
rm -rf tmp
mkdir tmp

prefix="jt"
port="3092"

echo "Run installer"
java -jar release/${prefix}Installer.jar
#if [ "$?" -ne 0 ]; then
#    exit 1
#fi

newServer=`ls | sort | grep ${prefix}Server20 | tail -1`
ln -s $newServer ${prefix}Server
DOWNLOADS=${WORKSPACE}/../downloads/
mkdir -p ${DOWNLOADS}
echo "build"


function retry {
  local max_attempts=${ATTEMPTS-6} ##ATTEMPTS (default 6)
  local timeout=${TIMEOUT-1}       ##TIMEOUT in seconds (default 1.) doubles on each attempt
  local attempt=0
  local exitCode=0
  
  set +e
  while [[ $attempt < $max_attempts ]]
  do
    "$@" && { 
      exitCode=0
      break 
    }
    exitCode=$?

    if [[ $exitCode == 0 ]]
    then
      break
    fi

    echo "Failure! Retrying in $timeout.." 1>&2
    sleep $timeout
    attempt=$(( attempt + 1 ))
    timeout=$(( timeout * 2 ))
  done
  set -e

  if [[ $exitCode != 0 ]]
  then
    echo "You've failed me for the last time! ($@)" 1>&2
  fi

  return $exitCode
}

browser=chrome
docker_net=host

set +e
retry docker run -d --net ${docker_net} -v /dev/shm:/dev/shm selenium/standalone-${browser}:3
jobs
retry curl -L localhost:4444/wd/hub/status
set -e

#cd ..
echo "done"


cd ${prefix}Server

CACHE_DIR=/tmp/scan/${prefix}/cache
mkdir -p ${CACHE_DIR}

DBPASS=$(aws secretsmanager get-secret-value --region ap-southeast-2 --secret-id common_secrets | jq --raw-output .SecretString | jq -r ."PGPASSWORD")

echo "create configuration"

echo "DBTYPE=POSTGRESQL" > conf/common.properties
echo "DBUSER=postgres" >> conf/common.properties
echo "DBPASSWORD=${DBPASS}" >> conf/common.properties
echo "CACHE_DIR=${CACHE_DIR}" >> conf/common.properties

echo "AREA=TEST" >>conf/common.properties
echo "DBSOURCE=st-serverless.cluster-codd4snrsonl.ap-southeast-2.rds.amazonaws.com/aspc_master" >>conf/common.properties
echo "download.tmp.dir=${DOWNLOADS}" >>conf/common.properties

echo "# 1 aspc_engine" >>conf/common.properties
echo "# 2 aspc_app" >>conf/common.properties

echo "private.db.layers=1,2" >>conf/common.properties

retry java -jar launcher.jar download

java -jar launcher.jar build
#/xenv/selenium/selenium.sh &

#change port number
replace="s/Connector port=\"8080\"/Connector port=\"${port}\"/g; s/<Server.*>/<Server port=\"-1\">/g; s/<Connector.*AJP.*>//g"

sed "$replace" < server/conf/server.xml >server/conf/server.xml2

mv server/conf/server.xml2 server/conf/server.xml

#increase/set max memory for tomcat server 
echo "" >> conf/server.properties
echo "MAX_MEMORY=4G" >> conf/server.properties


echo "---TimeBeforeServer----"
time=$ date
echo "-----------"


bin/server.sh &

echo "---TimeAfterServer----"
time=$ date
echo "-----------"

set -x
SECONDS=0
until [ "`curl --silent --show-error --connect-timeout 1 -I http://localhost:${port} | grep 'HTTP/1.1'`" != "" ];
do
  SECONDS=$((SECONDS+10))
  if [ "$SECONDS" -ge 300 ]; then
   	echo "Could not connect to the server within 5 minutes."
   	exit 1;
  fi
  sleep 10
done
set +x

cd ..
ls
cp selenese-runner.jar /tmp/
./scanAWSSuite.sh demoscan.json

echo "clean up..."

pkill -P $$'
                            //sh 'chmod a+x scan-demo.sh'
                            //sh './scan-demo.sh'
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
