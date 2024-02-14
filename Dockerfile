# syntax=docker/dockerfile:1
ARG MAVEN_VERSION=3.8.7-eclipse-temurin-17-focal
ARG ECLIPSE_TEMURIN_VERSION=17.0.5_8-jre-alpine

#build image
FROM maven:$MAVEN_VERSION as build
WORKDIR /app/server
COPY  . .
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

#run image
FROM eclipse-temurin:$ECLIPSE_TEMURIN_VERSION as RUN
COPY --from=build ["/app/server/target/*-exec.jar", "/app/server/Project Management System-exec.jar"]
WORKDIR /app/server

EXPOSE 8080
CMD [ "java", "-jar","Project Management System-exec.jar"]






