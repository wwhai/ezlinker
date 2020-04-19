FROM  18059150204/erlangenv:0.0.1
LABEL author="wwhai"
LABEL emai="cnwwhai@gmail.com"

ADD ./emqx-4.0-alpine.zip /
RUN unzip /emqx-4.0-alpine.zip -d /
RUN chmod -R 777 /emqx
ADD ./start.sh /
RUN chmod +x /start.sh
EXPOSE 1883 1884 18083 4369
ENTRYPOINT ["sh","/start.sh"]