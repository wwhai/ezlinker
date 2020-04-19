FROM alpine
LABEL author="wwhai"
LABEL emai="cnwwhai@gmail.com"
USER root
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk --update add nginx
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY ./index.zip  /EZLINKER/
RUN unzip /EZLINKER/index.zip -d /EZLINKER/
RUN chmod -R 777 /EZLINKER/
RUN mkdir /usr/local/nginx/
EXPOSE 80
ENTRYPOINT ["nginx"]