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
        stage('Parallel Stages') {
            parallel {
                stage('Read file 2.txt') {
                    steps {
                        retry(3) {
                            sh '''
                                sleep 3
                                ls -la
                                cat 2.txt
                            '''
                        }
                    }
                }
                stage('Create file 2.txt') {
                    steps {
                        sh'''
                            sleep 5
                            echo "HELLO FROM STAGE 2" >> 2.txt
                        '''
                    }
                }
            }
        }
    }
}
