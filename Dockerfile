FROM openjdk:11-jdk-slim

WORKDIR /opt/schedule

COPY /target/schedule*.jar schedule.jar

EXPOSE 8080

CMD java -jar schedule.jar