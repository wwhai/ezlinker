FROM openjdk:11.0.8-jre
ADD ./app-0.0.1.jar /APP/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/APP/app.jar", "--spring.profiles.active=docker"]