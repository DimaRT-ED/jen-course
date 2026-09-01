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
                script {
                    echo "Hello message: ${param.HELLO_MSG}"
                    echo "ENV-1 : ${env.ENV1}"
                }
                sh '''
                    echo "Hello message: $HELLO_MSG"
                    echo "ENV-1 : $ENV1"
                '''
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
