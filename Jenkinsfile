pipeline {
  agent any

  options {
    buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
  }

  stages {
    stage('test') {
      steps {
        sh 'ant -f test.xml -v'
        junit "reports/${env.BUILD_NUMBER}_result.xml"
      }
    }
    stage('build') {
      steps {
        sh 'ant -f build.xml -v'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
    }
  }
}
