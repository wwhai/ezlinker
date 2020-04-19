## 构建EMQX
```shell script
docker build -t 18059150204/emqx:4.0 .
docker run -it -d  -p 18883:18083  --name  ezlinker-emqx   18059150204/emqx:4.0
```
## 访问
```shell script
http://localhost:18883
```