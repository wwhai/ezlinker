# 前端Docker构建过程
## 1.前端项目构建
- 编译前端
```shell script
git clone https://github.com/ssloth/ezlinker-frontend.git
cd ezlinker-frontend
yarn build
```
- 构建成功以后，把public目录打包：
```shell script
zip index.zip ./public/*
```
- 复制zip到front目录下
## 2.构建容器
确认`Dockerfile`,`index.zip`,`nginx.conf`在同一个目录下，然后执行:
```shell script
docker build -t ezlinker/nginx .
```
如果前面的步骤没问题会有如下输出:
```shell script
Sending build context to Docker daemon  4.608kB
Step 1/13 : FROM alpine
latest: Pulling from library/alpine
aad63a933944: Pull complete
Digest: sha256:b276d875eeed9c7d3f1cfa7edb06b22ed22b14219a7d67c52c56612330348239
Status: Downloaded newer image for alpine:latest
 ---> a187dde48cd2
Step 2/13 : LABEL author="wwhai"
 ---> Running in 12622174c18b
Removing intermediate container 12622174c18b
 ---> a4cf061604d7
Step 3/13 : LABEL emai="cnwwhai@gmail.com"
 ---> Running in eb49e1918134
Removing intermediate container eb49e1918134
 ---> 12382fbe8921
Step 4/13 : USER root
 ---> Running in ac1474ccf87b
Removing intermediate container ac1474ccf87b
 ---> 7e5a5fc46c65
Step 5/13 : RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
 ---> Running in f0307c2fc87c
Removing intermediate container f0307c2fc87c
 ---> abac99b511f8
Step 6/13 : RUN apk --update add nginx
 ---> Running in e175aa19da17
fetch http://mirrors.ustc.edu.cn/alpine/v3.11/main/x86_64/APKINDEX.tar.gz
fetch http://mirrors.ustc.edu.cn/alpine/v3.11/community/x86_64/APKINDEX.tar.gz
(1/2) Installing pcre (8.43-r0)
(2/2) Installing nginx (1.16.1-r6)
Executing nginx-1.16.1-r6.pre-install
Executing busybox-1.31.1-r9.trigger
OK: 7 MiB in 16 packages
Removing intermediate container e175aa19da17
 ---> 7b547689d803
Step 7/13 : COPY ./nginx.conf /etc/nginx/nginx.conf
 ---> 5b2dc69713d7
Step 8/13 : COPY ./index.zip  /EZLINKER/
 ---> 2435c928a411
Step 9/13 : RUN unzip /EZLINKER/index.zip -d /EZLINKER/
 ---> Running in 7bcb34dec7ef
Archive:  /EZLINKER/index.zip
  inflating: index.html
Removing intermediate container 7bcb34dec7ef
 ---> 24f5e8522d05
Step 10/13 : RUN chmod -R 777 /EZLINKER/
 ---> Running in 4e8aac5bc3ca
Removing intermediate container 4e8aac5bc3ca
 ---> 72d491a5a198
Step 11/13 : RUN mkdir /usr/local/nginx/
 ---> Running in f335b95b9870
Removing intermediate container f335b95b9870
 ---> 4e35bc1a75c9
Step 12/13 : EXPOSE 80
 ---> Running in 57bc683cd507
Removing intermediate container 57bc683cd507
 ---> e9064243c0dd
Step 13/13 : ENTRYPOINT ["nginx"]
 ---> Running in 038212fddcf1
Removing intermediate container 038212fddcf1
 ---> 0ce8571236a1
Successfully built 0ce8571236a1
Successfully tagged nnginx:latest
```
执行:
```shell script
docker images
```
看是否有刚才构建的nginx:
```shell script
D:\github\ezlinker\resources\docker\frontend>docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
nnginx              latest              0ce8571236a1        16 seconds ago      8.58MB
alpine              latest              a187dde48cd2        11 days ago         5.6MB
```

## 3.运行测试
执行：
```shell script
docker run -p 8888:80 -it -d --name ezlinker/nginx ezlinker/nginx
```
输出：
```shell script
docker run -p 8888:80 -it -d --name ezlinker/nginx ezlinker/nginx
f751fedea28efd44164a6a0828efec9934212b029c36383e861d3092b2e03a4f
```
然后打开:http://localhost:8888,如果有页面，则说明构建成功。
