pipeline {
    agent { label 'master'}
    options {
        timeout(time: 10, unit: 'MINUTES')
        timestamps()  // Add timestamps to console output
    }
    parameters {
        string(name: 'HELLO_MSG', defaultValue: 'HELLO WORLDDDD !!!', description: 'bla-bla-bla')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'production'], description: 'Deploy environment')
    }
    environment {
        ENV1 = 'env-1-value'
        ENV2 = 'env-2-value'
    }

    stages {
        stage('CLEAN') {
            steps {
                echo "--- START CLEAN ---"
                cleanWs()
                echo "--- FINISH CLEAN ---"
            }
        }
        stage('Say Hello') {
            steps {
                sh 'pwd'
                sh '''
                    echo "Hello message: $HELLO_MSG"
                    echo "ENV-1 : $ENV1"
                    env
                    ls -la
                '''
            }
        }
        stage('Stage 2') {
            steps {
                sh 'pwd'
                retry(3) {
                    sh '''
                        cat stam.txt
                        sh 'sleep 5'
                    '''
                }
                timeout(time: 5, unit: 'SECONDS') {
                    sh 'sleep 5'
                    sh 'echo  sleeping'
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
        always {
            echo "always"
        }
    }
}
