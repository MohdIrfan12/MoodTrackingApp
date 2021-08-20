pipeline {
  agent any
  stages {
    stage('Detect build type') {
      parallel {
        stage('Detect build type') {
          steps {
            sh '''steps {
    script {
      if (env.BRANCH_NAME == \'develop\' || env.CHANGE_TARGET == \'develop\') {
        env.BUILD_TYPE = \'debug\'
      } else if (env.BRANCH_NAME == \'master\' || env.CHANGE_TARGET == \'master\') {
        env.BUILD_TYPE = \'release\'
      }
    }
  }'''
            }
          }

          stage('Compile') {
            steps {
              sh ''' steps {
    // Compile the app and its dependencies
    sh \'./gradlew compile${BUILD_TYPE}Sources\'
  }
'''
              }
            }

            stage('Build') {
              steps {
                sh '''steps {
    // Compile the app and its dependencies
    sh \'./gradlew assemble${BUILD_TYPE}\'
    sh \'./gradlew generatePomFileForLibraryPublication\'
  }'''
                }
              }

              stage('Publish') {
                steps {
                  sh ''' steps {
    // Archive the APKs so that they can be downloaded from Jenkins
    archiveArtifacts "**/${APP_NAME}-${BUILD_TYPE}.apk"
    // Archive the ARR and POM so that they can be downloaded from Jenkins
    // archiveArtifacts "**/${APP_NAME}-${BUILD_TYPE}.aar, **/*pom-   default.xml*"
  }'''
                  }
                }

              }
            }

          }
          environment {
            APP_NAME = '\'test\''
          }
        }