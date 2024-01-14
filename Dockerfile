# FROM openjdk:8-jdk-alpine
#FROM openjdk:11.0.16-slim
FROM eclipse-temurin:17-jdk

# docker build --build-arg ARG_JASYPT_PASSWORD=abcdef -t image:tag
ARG ARG_JASYPT_PASSWORD="default"
ARG ARG_ACTIVE_PROFILES="prd"
ENV JASYPT_PASSWORD=$ARG_JASYPT_PASSWORD
ENV ACTIVE_PROFILES=$ARG_ACTIVE_PROFILES

WORKDIR /app/
COPY build/libs/ja-hello-world.jar /app/ja-hello-world.jar
ENTRYPOINT ["java", "-Djasypt.encryptor.password=$JASYPT_PASSWORD", "-jar", "ja-hello-world.jar", "--spring.profiles.active=$ACTIVE_PROFILES"]
