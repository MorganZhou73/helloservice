# For Java 8
FROM openjdk:8-jdk-alpine

# For Java 11, try this
# FROM adoptopenjdk/openjdk11:alpine-jre

MAINTAINER unistar.com

VOLUME /tmp
EXPOSE 9000

COPY target/*.jar helloservice.jar

ENTRYPOINT java -jar helloservice.jar
