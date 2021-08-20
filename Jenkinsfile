pipeline{
  
  agent any

  environment{
    APP_NAME='app'
  }
  
  options{
    //Stopthebuildearlyincaseofcompileortestfailures
    skipStagesAfterUnstable()
  }
  
  stages{
    
    stage('Detect build type'){
      steps{
        script{
          if(env.BRANCH_NAME=='develop'||env.CHANGE_TARGET=='develop'){
            env.BUILD_TYPE='debug'
          }
          if(env.BRANCH_NAME=='master'||env.CHANGE_TARGET=='master'){
            env.BUILD_TYPE='release-unsigned'
          }
        }
      }
    }
    
    stage('Compile'){
      steps{
        //Compiletheappanditsdependencies
        sh'./gradlew compile${BUILD_TYPE}Sources'
      }
    }
    stage('Build'){
      steps{
        //Compiletheappanditsdependencies
        sh'./gradlew assemble${BUILD_TYPE}'
//         sh'./gradlew generatePomFileForLibraryPublication'
      }
    }
    stage('Publish'){
      steps{
        //ArchivetheAPKssothattheycanbedownloadedfromJenkins
        archiveArtifacts"**/${APP_NAME}-${BUILD_TYPE}.apk"
        //ArchivetheARRandPOMsothattheycanbedownloadedfromJenkins//archiveArtifacts"**/${APP_NAME}-${BUILD_TYPE}.aar, **/*pom-   default.xml*"
      }
    }
  }
}
