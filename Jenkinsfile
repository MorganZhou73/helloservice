
node
{
    stage ("checkoutSCM")
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

            bat "echo 'checkoutSCM: complete'"
            //sh "echo 'checkoutSCM: Complete'"
        }
        catch(err)
        {
            bat "echo 'checkoutSCM: Failed' "
            throw err
        }
    }
    stage("javaBuild")
    {
        try
        {
		//bat 'mvn clean package -DskipTests' 
		//publishReports();
		//publishJunitTest();
		bat "echo 'javaBuild: Complete'"
	}
        catch(err)
	{
		echo 'javaBuild: Failed'
		//publishJunitTest();
		throw err
	}
    }
}

