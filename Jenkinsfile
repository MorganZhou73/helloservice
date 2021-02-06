node
{
	stage ("checkoutSCM") {
		try{
			checkout([
			 $class: 'GitSCM',
			 branches: scm.branches,
			 doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
			 extensions: scm.extensions,
			 userRemoteConfigs: scm.userRemoteConfigs
			])

			bat "echo 'checkoutSCM: complete'"
		}
		catch(err) {
			bat "echo 'checkoutSCM: Failed' "
			throw err
		}
	}
	stage("javaBuild") {
		try {
			bat 'mvn clean package -DskipTests' 
			bat "echo 'javaBuild: Complete'"
		}
		catch(err) {
			bat "echo 'javaBuild: Failed'"
			throw err
		}
	}
	stage("UnitTest") {
		try {
			bat 'mvn test'
			bat "junit 'target/surefire-reports/*.xml'"
			//publishJunitTest();
			bat "echo 'UnitTest: Complete'"
		}
		catch(err) {
			bat "echo 'UnitTest: Failed'"
			//publishJunitTest();
			throw err
		}		
	}
}
