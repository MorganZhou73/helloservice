
node
{
  stage ("checkout scm")
    {
        try
        {
            checkout([
             $class: 'GitSCM',
             branches: scm.branches,
             doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
             extensions: scm.extensions,
             userRemoteConfigs: scm.userRemoteConfigs
            ])
            echo "${currentBuild.buildCauses}"
            echo "${currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')}"
            echo "${currentBuild.getBuildCauses('jenkins.branch.BranchEventCause')}"
            //checkout scm
            //If it is triggered by UI do not check [ci skip]
            if ( currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').isEmpty() )
            {
                scmSkip(deleteBuild: true, skipPattern:'.*\\[ci skip\\].*')
            }
            env.GIT_COMMIT=sh returnStdout: true, script: 'echo $(git rev-parse HEAD)'
            env.GIT_COMMIT_MIN=sh returnStdout: true, script: 'echo $(git rev-parse --short=7 HEAD)'
            env.GIT_COMMIT_AUTHOR=sh returnStdout: true, script: 'echo $(git log -1 --pretty=format:%aE)'
            env.GIT_COMMENT=sh returnStdout: true, script: 'echo $(git log -1 --pretty=format:%s)'
        
            sh "echo 'checkoutSCM: Complete'"
        }
        catch(err)
        {
            sh "echo 'checkoutSCM: Failed' "
            throw err
        }
    }
}

