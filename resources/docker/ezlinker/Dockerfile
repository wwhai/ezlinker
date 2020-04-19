FROM openjdk:8-jdk-alpine
LABEL author="wwhai"
LABEL emai="cnwwhai@gmail.com"
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
ADD app-0.0.1.jar /EZLINKER/app.jar
EXPOSE 2500 2501
RUN cd /EZLINKER/
RUN java -jar app.jar