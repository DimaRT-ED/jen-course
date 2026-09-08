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
                    sleep 2
                    echo "Hello message: $HELLO_MSG"
                    echo "ENVIRONMENT : $ENVIRONMENT"
                '''
            }
        }
        stage('Clone Repository via SSH') {
            steps {
                withCredentials([sshUserPrivateKey(
                    credentialsId: 'DimaRT-ED', 
                    keyFileVariable: 'SSH_KEY'
                )]) {
                    // Tell Git to use the specific private key file provided by Jenkins
                    // export GIT_SSH_COMMAND="ssh -i ${SSH_KEY} -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"
                    sh """
                        ls -la
                        git clone git@github.com:DimaRT-ED/taasuka_action.git
                        ls -la
                    """
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
