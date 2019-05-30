FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/app
RUN addgroup spring && adduser -S spring spring && echo spring: | chpasswd && chown -R spring:spring /opt/app

USER spring
COPY target/*.jar /opt/app/server.jar
EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar /opt/app/server.jar
