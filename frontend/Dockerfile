#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER Atik

RUN apt-get update
RUN apt-get install -y maven
RUN rm -rf frontend/generated
COPY pom.xml /usr/local/frontend/pom.xml
COPY src /usr/local/frontend/src
COPY frontend /usr/local/frontend/frontend
WORKDIR /usr/local/frontend
RUN mvn clean package -Pproduction -Dvaadin.force.production.build=true

#Execute the application
ENTRYPOINT ["java","-Dvaadin.productionMode=true","-Dspring.profiles.active=production","-jar","target/frontend-1.0-SNAPSHOT.jar"]
