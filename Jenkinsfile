
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

            echo "pipeline is finished now completed"
            //sh "echo 'checkoutSCM: Complete'"
        }
        catch(err)
        {
            sh "echo 'checkoutSCM: Failed' "
            throw err
        }
    }
}

