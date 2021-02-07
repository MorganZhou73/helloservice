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
			junit 'target/surefire-reports/*.xml'
			
			bat "echo 'UnitTest: Complete'"
		}
		catch(err) {
			bat "echo 'UnitTest: Failed'"
			junit 'target/surefire-reports/*.xml'
			throw err
		}		
	}
	stage("dockerBuild") {
		try {
			bat "docker build -t zmg9046/helloservice:tag-1.0.0 -f Dockerfile ."
			bat "echo 'dockerBuild: Complete'"
		}
		catch(err) {
			bat "echo 'dockerBuild: Failed'"
			throw err
		}
	}	
	stage("dockerDeploy") {
		try {
			bat "docker run -p 9000:9000 --name helloservice -d zmg9046/helloservice:tag-1.0.0"
			bat "echo 'dockerDeploy: Complete'"
		}
		catch(err) {
			bat "echo 'dockerDeploy: Failed'"
			throw err
		}
	}
}
