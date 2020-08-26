# 注意：本目录下的dockerFile是开发过程使用的，没事不要自己做实验
```shell script
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE ez_schedule_info;
TRUNCATE ez_schedule_template;
TRUNCATE qrtz_triggers;
TRUNCATE qrtz_job_details;
TRUNCATE qrtz_blob_triggers;
```