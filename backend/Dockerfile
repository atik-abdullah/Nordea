#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER Atik

RUN apt-get update
RUN apt-get install -y maven
COPY pom.xml /usr/local/backend/pom.xml
COPY src /usr/local/backend/src
WORKDIR /usr/local/backend
RUN mvn clean package 

#execute the application
ENTRYPOINT ["java","-jar","target/backend-1.0-SNAPSHOT.jar"]
