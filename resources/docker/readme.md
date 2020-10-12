# 注意：本目录下的dockerFile是开发过程使用的，没事不要自己做实验
起环境
```shell script
docker-compose -f ./docker-compose-win10.yml -p ezkinker_env up -d
```
1. 清空数据库
```Sql

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE ez_project;
TRUNCATE ez_product;
TRUNCATE ez_device;
TRUNCATE ez_mqtt_topic;
TRUNCATE ez_module;
TRUNCATE ez_module_template;
TRUNCATE ez_schedule_info;
TRUNCATE ez_schedule_template;
TRUNCATE qrtz_triggers;
TRUNCATE qrtz_job_details;
TRUNCATE qrtz_blob_triggers;
BEGIN;
INSERT INTO `ez_device`
VALUES (1,
        0,
        0,
        'EZLINKER-C1',
        'EZLINKER-C1',
        'EZLINKER-C1',
        'EZLINKER-C1',
        'EZLINKER-C1',
        'EZLINKER-C1',
        NULL,
        NULL,
        'EZLINKER',
        'EZLINKER-C1',
        'EZLINKER',
        1,
        'EZLINKER-C1_TOKEN',
        '[]',
        'EZLINKER-C1',
        0,
        0,
        '2020-09-15 21:33:29');
INSERT INTO `ez_device`
VALUES (2,
        0,
        0,
        'EZLINKER-C2',
        'EZLINKER-C2',
        'EZLINKER-C2',
        'EZLINKER-C2',
        'EZLINKER-C2',
        'EZLINKER-C2',
        NULL,
        NULL,
        'EZLINKER',
        'EZLINKER-C2',
        'EZLINKER',
        1,
        'EZLINKER-C2_TOKEN',
        '[]',
        'EZLINKER-C2',
        0,
        0,
        '2020-09-15 21:33:29');
COMMIT;

```

2。 清空Docker环境
```shell script
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker volume prune
docker network prune

```