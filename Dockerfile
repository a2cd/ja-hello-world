# FROM openjdk:8-jdk-alpine
#FROM openjdk:11.0.16-slim
#FROM eclipse-temurin:17-jdk
#FROM eclipse-temurin:17-jdk-alpine
FROM eclipse-temurin:17-jre-alpine

# docker build --build-arg ARG_JASYPT_PASSWORD=abcdef -t image:tag
ARG ARG_JASYPT_PASSWORD=""
ARG ARG_FILE_NAME=ja-hello-world.jar

ENV JASYPT_PASSWORD=$ARG_JASYPT_PASSWORD
ENV JVM_MAX_HEAP="256m"
ENV ACTIVE_PROFILES="prd"

WORKDIR /app/
COPY build/libs/$ARG_FILE_NAME /app/$ARG_FILE_NAME
ENTRYPOINT ["java", "-Xms$JVM_MAX_HEAP", "-XX:+UseZGC", "-Djasypt.encryptor.password=$JASYPT_PASSWORD", "-jar", "$ARG_FILE_NAME", "--spring.profiles.active=$ACTIVE_PROFILES"]
EXPOSE 8080
