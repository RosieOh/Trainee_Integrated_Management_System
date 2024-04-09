FROM openjdk:17.0.2-jdk-slim-buster AS builder

ARG JAR_FILE=./*.jar

ADD ${JAR_FILE} chunjaeitlms.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/chunjaeitlms.jar"]