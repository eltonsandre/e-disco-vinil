# FROM mgorbunov/alpine-jre11
FROM docker.io/java:8u102-jre

EXPOSE 8080:8080

ENV APP_NAME e-discos-vinil
LABEL SERVICE_NAME=$APP_NAME

VOLUME /tmp
ARG JAR_FILE

COPY target/e-discos-vinil*.jar e-discos-vinil.jar


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "/e-discos-vinil.jar"]