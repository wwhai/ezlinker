#!/bin/zsh
mvn package -Pdocker
docker push ezlinker/ezlinker:latest