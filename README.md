# Simple RESTful microservice demo, jmeter test, docker, Helm chart to Kubernetes
    
## API Test
### Endpoint /greeting ; /greeting1
D:\>curl "http://localhost:8080/greeting"
{"id":6,"content":"Good morning, World!"}

D:\>curl "http://localhost:8080/greeting?name=joe"
{"id":7,"content":"Good morning, joe!"}

D:\>curl "http://localhost:8080/greeting1/joe"
{"id":8,"content":"Good morning, joe!"}

### Endpoint /sendmessage ; /sendmessagemap
D:\>curl -X POST -H "Content-Type:application/json" -d "{ \"id\" : 1, \"content\" :  \"Good morning\" }" "http://localhost:8080/sendmessage"
JSON message received! Your message: message{id: 1, content:Good morning}

D:\>curl -X POST -H "Content-Type:text/plain" -d "Good morning" "http://localhost:8080/sendmessage"
String message received! Your message: Good morning

D:\>curl -X POST -H "Content-Type:application/xml" -d "<message><id>1</id><content>Good morning</content></message>" "http://localhost:8080/sendmessage"
XML message received! Your message: message{id: 1, content:Good morning}

D:\>curl -X POST -d "id=1&content=good morning" "http://localhost:8080/sendmessagemap"
Map message received! Your message: message{id: 1, content:good morning}

D:\helloservice>curl -X POST -H "Content-Type:application/json" -d "@.\src\test\resources\message1.json" "http://localhost:8080/sendmessage"
JSON message received! Your message: message{id: 1, content:Good morning!}

### Endpoint /healthcheck ; /healthcheck2 ; /healthcheck3
D:\>curl localhost:8080/healthcheck?format=short
{"status":"OK"}

D:\>curl localhost:8080/healthcheck?format=full
{"currentTime":"2021-02-05T23:05:30Z","application":"OK"}

D:\>curl localhost:8080/healthcheck?format=long
{"timestamp":"2021-02-05T23:05:39.115+00:00","status":400,"error":"Bad Request","message":"","path":"/healthcheck"}

D:\>curl localhost:8080/healthcheck?format=short -v
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /healthcheck?format=short HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.55.1
> Accept: */*
>
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Fri, 05 Feb 2021 23:18:56 GMT
<
{"status":"OK"}* Connection #0 to host localhost left intact

D:\>curl localhost:8080/healthcheck1?format=full
{"currentTime":"2021-02-06T00:00:29Z","application":"OK"}
D:\>curl localhost:8080/healthcheck2?format=full
{"currentTime":"2021-02-06T00:00:41.158Z", "application": "OK"}
D:\>curl localhost:8080/healthcheck3?format=full
{"currentTime":"2021-02-06T00:01:29.920Z", "application": "OK"}

D:\>curl localhost:8080/healthcheck3?format=f
Bad format
D:\>curl localhost:8080/healthcheck?format=f
{"timestamp":"2021-02-06T00:03:27.726+00:00","status":400,"error":"Bad Request","message":"","path":"/healthcheck"}

D:\>curl localhost:8080/healthcheck3?format=f -v
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /healthcheck3?format=f HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.55.1
> Accept: */*
>
< HTTP/1.1 400
< Content-Type: application/json
< Content-Length: 10
< Date: Sat, 06 Feb 2021 00:04:48 GMT
< Connection: close
<
Bad format* Closing connection 0

## Build / Run commands
; compile and run unit test
mvn clean package

; compile unit test, but not run unit test
mvn clean package -DskipTests

java -jar target/helloservice-0.0.1-SNAPSHOT.jar --server.port=8080
; Ctrl+ C to stop the Tomcat WebServer

## Docker Commands
docker build -t zmg9046/helloservice:tag-1.0.0 -f Dockerfile .
docker run -p 8080:8080 --name helloservice -d zmg9046/helloservice:tag-1.0.0

; if port 8080 is not available, but 9000 is available
docker run -p 9000:8080 --name helloservice -d zmg9046/helloservice:tag-1.0.0

; to check docker image/container
D:\helloservice>docker image ls
REPOSITORY               TAG                   IMAGE ID       CREATED          SIZE
zmg9046/helloservice     tag-1.0.0             328e6004e656   21 minutes ago   123MB
openjdk                  8-jdk-alpine          a3562aa0b991   21 months ago    105MB

D:\helloservice>docker ps -a
CONTAINER ID   IMAGE                            COMMAND                  CREATED              STATUS              PORTS                    NAMES
889a7d51e4a0   zmg9046/helloservice:tag-1.0.0   "/bin/sh -c 'java -j…"   About a minute ago   Up About a minute   0.0.0.0:8080->8080/tcp   helloservice

; to remove the container and image	from docker
docker stop helloservice
docker rm helloservice
docker image rm zmg9046/helloservice:tag-1.0.0

; use docker-compose
docker-compose -f docker-compose.yml build
docker-compose -f docker-compose.yml up -d

docker-compose -f docker-compose.yml down
	
### Push to Docker hub https://hub.docker.com/

docker login
docker push zmg9046/helloservice:tag-1.0.0

### Helm chart to Kubernetes
PS C:\helloservice> helm create hellochart

PS C:\helloservice> kubectl config get-contexts
PS C:\helloservice> kubectl create ns hello

PS C:\helloservice> kubectl get ns
PS C:\helloservice> helm install hellochart --namespace hello --generate-name
  ; -- Or
PS C:\helloservice> helm install hellochart123 hellochart --namespace hello

PS C:\helloservice> kubectl -n hello get all
NAME                                READY   STATUS    RESTARTS   AGE
pod/helloservice-699fcf4f8b-62ww6   0/1     Running   2          58s

NAME                   TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
service/helloservice   ClusterIP   10.101.73.160   <none>        9000/TCP   58s

NAME                           READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/helloservice   0/1     1            0           58s

NAME                                      DESIRED   CURRENT   READY   AGE
replicaset.apps/helloservice-699fcf4f8b   1         1         0       58s

PS C:\helloservice> kubectl -n hello describe pod/helloservice-699fcf4f8b-62ww6

PS C:\helloservice> helm ls --namespace hello
PS C:\helloservice> kubectl -n hello delete service/helloservice
PS C:\helloservice> kubectl -n hello delete deployment.apps/helloservice

PS C:\helloservice> kubectl -n hello port-forward service/helloservice 9000:8080
; then access http://localhost:9000/healthcheck?format=full   
; -- port 8080 is defined in Dockerfile and \hellochart\values.yaml

$ curl http://localhost:9000/greeting1/joe

PS C:\helloservice> helm uninstall hellochart-1612744231 --namespace hello
$ helm --namespace hello uninstall hellochart123

PS C:\helloservice> git add .
PS C:\helloservice> git commit -am "add helm chart"
PS C:\helloservice> git push

PS C:\helloservice> helm package hellochart
Successfully packaged chart and saved it to: C:\helloservice\hellochart-0.1.0.tgz

## Jmeter test
cd jmeter
$ docker build -t zmg9046/hellojmeter:tag-1.0.0 -f ./Dockerfile .
$ helm install hellojmeter001 charts --namespace hello
$ kubectl -n hello get all
	NAME                              READY   STATUS    RESTARTS   AGE
	pod/hello-qa-xst66                1/1     Running   0          58s
	; to keep the STATUS as "Running" (otherwise "Completed") for checking logs, need add "sleep 9000" in entrypoint.sh
	
$ export POD_NAME=`kubectl get po -n hello | grep hello-qa | awk '{print $1}'`; 

$ echo $POD_NAME
hello-qa-xst66

$ kubectl -n hello logs pod/$POD_NAME

; the following 2 commands not work in MINGW64 (git bash) ; but work in PowerShell
$ kubectl cp hello/$POD_NAME:/qa/greetingTest.jtl /Temp/greetingTest.jtl
$ kubectl -n hello exec -it $POD_NAME -- bash
Unable to use a TTY - input is not a terminal or the right kind of file


PS E:\helloservice> docker build -t zmg9046/hellojmeter:tag-1.0.0 -f jmeter/Dockerfile jmeter/.
PS E:\helloservice> helm install hellojmeter001 jmeter/charts --namespace hello

PS E:\> kubectl get po -n hello

PS E:\> kubectl -n hello exec -it hello-qa-xst66 -- bash

root@hello-qa-xst66:/qa# ls
entrypoint.sh  greetingTest.jmx  greetingTest.jtl  jmeter.log
; the greetingTest.jtl is the result of jmeter tests
root@hello-qa-xst66:/qa# cat greetingTest.jtl
timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect
1615131559103,53,greetingTest1,200,,Thread Group 1-1,text,true,,214,130,1,1,http://helloservice:8080/greeting,50,0,35
1615131559160,4,greetingTest2,200,,Thread Group 1-1,text,true,,213,139,1,1,http://helloservice:8080/greeting?name=joe,4,0,0
1615131559165,4,greeting1Test,200,,Thread Group 1-1,text,true,,213,135,1,1,http://helloservice:8080/greeting1/joe,4,0,0

PS E:\> kubectl cp hello/hello-qa-xst66:/qa/greetingTest.jtl /Temp/greetingTest.jtl
PS E:\> kubectl cp hello/hello-qa-xst66:/qa/jmeter.log /Temp/jmeter.log

$ helm --namespace hello delete hellojmeter001
$ docker image rm zmg9046/hellojmeter:tag-1.0.0

