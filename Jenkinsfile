pipeline{
  
  agent any

  agentanyenvironment{
    APP_NAME='test'
  }
  
  options{
    //StopthebuildearlyincaseofcompileortestfailuresskipStagesAfterUnstable()
  }
  
  stages{
    
    stage('Detect build type'){
      steps{
        script{
          if(env.BRANCH_NAME=='develop'||env.CHANGE_TARGET=='develop'){
            env.BUILD_TYPE='debug'
          }elseif(env.BRANCH_NAME=='master'||env.CHANGE_TARGET=='master'){
            env.BUILD_TYPE='release'
          }
        }
      }
    }
    
    stage('Compile'){
      steps{
        //Compiletheappanditsdependenciessh'./gradlew compile${BUILD_TYPE}Sources'
      }
    }
    stage('Build'){
      steps{
        //Compiletheappanditsdependenciessh'./gradlew assemble${BUILD_TYPE}'sh'./gradlew generatePomFileForLibraryPublication'
      }
    }
    stage('Publish'){
      steps{
        //ArchivetheAPKssothattheycanbedownloadedfromJenkinsarchiveArtifacts"**/${APP_NAME}-${BUILD_TYPE}.apk"//ArchivetheARRandPOMsothattheycanbedownloadedfromJenkins//archiveArtifacts"**/${APP_NAME}-${BUILD_TYPE}.aar, **/*pom-   default.xml*"
      }
    }
  }
}
