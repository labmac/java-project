pipeline {
  agent none

  stages {
    stage('Unit Tests') {
      agent {
        label 'apache'
      }
      steps {
        sh 'export GIT_BRANCH=$(git describe --all | awk -F "/" \'{print $NF}\')'
        echo "${env.GIT_BRANCH}"
        sh 'ant -f test.xml -v'
        junit 'reports/result.xml'
      }
    }
    stage('build') {
      agent {
        label 'apache'
      }
      steps {
        sh 'ant -f build.xml -v'
      }
      post {
        always {
          archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
        }
      }
    }
    stage('deploy') {
      agent {
        label 'apache'
      }
      steps {
        sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
      }
    }
    stage("Running on CentOS") {
      agent {
        label 'CentOS'
      }
      steps {
        sh "wget http://brandon4231.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
        sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"
      }
    }
    stage('Promote Development to Master') {
      agent {
        label 'apache'
      }
      when {
        expression { $env.GIT_BRANCH == 'development' }
      }
      steps {
        sh 'git stash'
        sh "git tag ${env.BUILD_NUMBER}"
        sh "git push origin ${env.BUILD_NUMBER}"
        sh 'git checkout master'
        sh 'git pull origin master'
        sh 'git merge development'
        sh 'git push origin master'
      }
    }
  }
}
