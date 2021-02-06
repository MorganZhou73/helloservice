
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

            echo "checkoutSCM is completed"
            //sh "echo 'checkoutSCM: Complete'"
        }
        catch(err)
        {
            sh "echo 'checkoutSCM: Failed' "
            throw err
        }
    }
    stage("javaBuild")
    {
        try
        {
		//sh "mvn package -npu -B -PDEV  && ls -l target"
		//publishReports();
		//publishJunitTest();
		sh "echo 'javaBuild: Complete'"
	}
        catch(err)
	{
		sh "echo 'javaBuild: Failed'"
		//publishJunitTest();
		throw err
	}
    }
}

