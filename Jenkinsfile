pipeline{
  agent any

  stages {
    
    stage("Build Backend") {
      steps {
        echo 'Building the application....'
        echo 'Executing Gradle....'
        withGradle() {
          sh 'chmod +x ./gradlew'
          sh './gradlew -v'
          sh './gradlew clean build -x test'
        }
      }
    }
    
    stage("run frontend") {
      steps {
        echo 'Testing the application....'
        echo 'Frontend the application....'
      }
    }
    
    stage("deploy") {
      steps {
        echo 'Deploying the application....'
      }
    }
  }
}
