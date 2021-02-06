
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
		mvn install
		//publishReports();
		//publishJunitTest();
		echo 'javaBuild: Complete'
	}
        catch(err)
	{
		echo 'javaBuild: Failed'
		//publishJunitTest();
		throw err
	}
    }
}

