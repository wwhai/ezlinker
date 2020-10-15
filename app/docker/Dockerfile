FROM openjdk:8-jdk-alpine
ADD ./app-0.0.1.jar /APP/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/APP/app.jar"]