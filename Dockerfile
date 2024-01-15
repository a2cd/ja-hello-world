# FROM openjdk:8-jdk-alpine
#FROM openjdk:11.0.16-slim
#FROM eclipse-temurin:17-jdk
#FROM eclipse-temurin:17-jdk-alpine
FROM eclipse-temurin:17-jre-alpine

# docker build --build-arg ARG_JASYPT_PASSWORD=abcdef -t image:tag
ARG ARG_JASYPT_PASSWORD=""
ENV JASYPT_PASSWORD=$ARG_JASYPT_PASSWORD
ENV JVM_XMS="512m"
ENV ACTIVE_PROFILES="prd"

WORKDIR /app/
COPY build/libs/ja-hello-world.jar /app/ja-hello-world.jar
ENTRYPOINT ["java", "-Xms$JVM_XMS", "-XX:+UseZGC", "-Djasypt.encryptor.password=$JASYPT_PASSWORD", "-jar", "ja-hello-world.jar", "--spring.profiles.active=$ACTIVE_PROFILES"]
EXPOSE 8080
