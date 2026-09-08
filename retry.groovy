pipeline {
    agent { label 'master'}
    options {
        timeout(time: 10, unit: 'MINUTES')
        timestamps()  // Add timestamps to console output
    }
    environment {
        OWNER = "Dima"
        PROJECT = "Jenkins course"
    }
    stages {
        stage('CLEAN') {
            steps {
                echo "--- START CLEAN ---"
                cleanWs()
                echo "--- FINISH CLEAN ---"
            }
        }
        stage('parallel') {
            steps {
                parallel(
                    job1: {
                        build job: 'jjjjob1',
                               parameters: [string(name: 'NAME', value: "HELLO PARALLEL ____  1 !!!")],
                               wait: true,
                               propagate: true
                    },
                    job2: {
                        build job: 'jjjjob2',
                               parameters: [string(name: 'NAME', value: "HELLO PARALLEL ____  2 !!!")],
                               wait: true,
                               propagate: true
                    }
                )
            }
        }
    }
}
