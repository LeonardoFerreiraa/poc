FROM openjdk:8-jdk-alpine

ADD build/libs/*.jar /app.jar

ENTRYPOINT java $JAVA_ARGS -jar app.jar
