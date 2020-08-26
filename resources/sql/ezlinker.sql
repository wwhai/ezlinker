/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 106.75.57.171:53306
 Source Schema         : ezlinker

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 26/08/2020 19:09:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ez_aliyun_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_aliyun_config`;
CREATE TABLE `ez_aliyun_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `access_key` varchar(64) DEFAULT NULL COMMENT '阿里云访问Key',
  `secret` varchar(200) DEFAULT NULL COMMENT '阿里云密钥',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='阿里云密钥配置表';

-- ----------------------------
-- Table structure for ez_aliyun_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_aliyun_sms_config`;
CREATE TABLE `ez_aliyun_sms_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `region_id` varchar(64) DEFAULT NULL COMMENT '区域',
  `account_name` varchar(200) DEFAULT NULL COMMENT '账户名',
  `from` varchar(200) DEFAULT NULL COMMENT '发信人(自己)',
  `address_type` varchar(200) DEFAULT NULL COMMENT '地址类型(默认为1)',
  `tag_name` varchar(200) DEFAULT NULL COMMENT '标签,平台申请以后会发',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='阿里云短信参数配置表';

-- ----------------------------
-- Table structure for ez_cloud_app
-- ----------------------------
DROP TABLE IF EXISTS `ez_cloud_app`;
CREATE TABLE `ez_cloud_app` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL COMMENT 'User ID',
  `name` varchar(20) NOT NULL COMMENT 'UI显示的标签',
  `description` varchar(200) DEFAULT NULL,
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='云APP';

-- ----------------------------
-- Table structure for ez_cloud_function
-- ----------------------------
DROP TABLE IF EXISTS `ez_cloud_function`;
CREATE TABLE `ez_cloud_function` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `cloud_app_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL COMMENT 'User ID',
  `label` varchar(20) NOT NULL COMMENT 'UI显示的标签',
  `enable` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否开启',
  `description` varchar(200) DEFAULT NULL COMMENT 'Commet',
  `script` text NOT NULL COMMENT 'Script content',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='云函数';

-- ----------------------------
-- Records of ez_cloud_function
-- ----------------------------
BEGIN;
INSERT INTO `ez_cloud_function` VALUES (1, NULL, 1, '获取用户数据云函数', 1, '获取用户数据云函数获取用户数据云函数', '    public CloudFunctionController(HttpServletRequest httpServletRequest) {\r\n        super(httpServletRequest);\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
INSERT INTO `ez_cloud_function` VALUES (2, NULL, 1, '返回设备', 1, '获取用户数据云函数获取用户数据云函数', '    protected R get(@PathVariable Long id) throws XException {\r\n        CloudFunction cloudFunction = iCloudFunctionService.getById(id);\r\n        if (cloudFunction == null) {\r\n            throw new BizException(\"CloudFunction not exists\", \"云函数不存在\");\r\n\r\n        }\r\n        return data(cloudFunction);\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
INSERT INTO `ez_cloud_function` VALUES (3, NULL, 1, '读取系统参数', 1, '获取用户数据云函数获取用户数据云函数', '    @Bean\r\n    public HandlerInterceptor permissionInterceptor() {\r\n        return new PermissionInterceptor();\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
INSERT INTO `ez_cloud_function` VALUES (4, NULL, 1, '读取Java版本', 1, '获取用户数据云函数获取用户数据云函数', '    protected R get(@PathVariable Long id) throws XException {\r\n        CloudFunction cloudFunction = iCloudFunctionService.getById(id);\r\n        if (cloudFunction == null) {\r\n            throw new BizException(\"CloudFunction not exists\", \"云函数不存在\");\r\n\r\n        }\r\n        return data(cloudFunction);\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
INSERT INTO `ez_cloud_function` VALUES (5, NULL, 1, '获取用户数据云函数', 1, '获取用户数据云函数获取用户数据云函数', '    protected R get(@PathVariable Long id) throws XException {\r\n        CloudFunction cloudFunction = iCloudFunctionService.getById(id);\r\n        if (cloudFunction == null) {\r\n            throw new BizException(\"CloudFunction not exists\", \"云函数不存在\");\r\n\r\n        }\r\n        return data(cloudFunction);\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
INSERT INTO `ez_cloud_function` VALUES (6, NULL, 1, '高级脚本', 1, '获取用户数据云函数获取用户数据云函数', '    protected R get(@PathVariable Long id) throws XException {\r\n        CloudFunction cloudFunction = iCloudFunctionService.getById(id);\r\n        if (cloudFunction == null) {\r\n            throw new BizException(\"CloudFunction not exists\", \"云函数不存在\");\r\n\r\n        }\r\n        return data(cloudFunction);\r\n    }\r\n', 0, 0, '2020-04-12 18:19:35');
COMMIT;

-- ----------------------------
-- Table structure for ez_device
-- ----------------------------
DROP TABLE IF EXISTS `ez_device`;
CREATE TABLE `ez_device` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL COMMENT '项目',
  `product_id` int NOT NULL COMMENT '产品',
  `protocol` int DEFAULT NULL COMMENT '协议类型',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `logo` varchar(200) NOT NULL COMMENT 'Logo',
  `location` varchar(20) DEFAULT NULL COMMENT '地理位置',
  `model` varchar(20) DEFAULT NULL COMMENT '型号',
  `industry` varchar(50) DEFAULT NULL COMMENT '厂家',
  `sn` varchar(64) NOT NULL COMMENT '序列号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `last_active` datetime DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '1:在线;2:离线;3未激活',
  `statuses` json DEFAULT NULL COMMENT '运行的状态',
  `username` varchar(200) DEFAULT NULL COMMENT 'MQTT用户名',
  `client_id` varchar(200) DEFAULT NULL COMMENT 'MQTT ClientID',
  `password` varchar(200) DEFAULT NULL COMMENT 'MQTT密码',
  `is_superuser` int DEFAULT NULL COMMENT '是否超级权限',
  `token` varchar(255) DEFAULT NULL COMMENT '认证token',
  `parameters` json DEFAULT NULL COMMENT '参数',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(1) unsigned zerofill NOT NULL COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='实际设备，是产品的一个实例。在EMQX中扮演 的是USER,支持两种认证形式:clientId和Username\\password.';

-- ----------------------------
-- Table structure for ez_device_config_export_format_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_device_config_export_format_config`;
CREATE TABLE `ez_device_config_export_format_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `value` varchar(16) DEFAULT NULL COMMENT '值',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='导出配置的格式，比如XML或者INI等等';

-- ----------------------------
-- Records of ez_device_config_export_format_config
-- ----------------------------
BEGIN;
INSERT INTO `ez_device_config_export_format_config` VALUES (1, 'INI', 'INI格式', 0, 0, '2020-05-04 22:47:34');
INSERT INTO `ez_device_config_export_format_config` VALUES (2, 'CSV', 'CSV格式', 0, 0, '2020-05-04 22:47:54');
INSERT INTO `ez_device_config_export_format_config` VALUES (3, 'JSON', 'JSON格式', 0, 0, '2020-05-04 22:48:08');
COMMIT;

-- ----------------------------
-- Table structure for ez_device_protocol_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_device_protocol_config`;
CREATE TABLE `ez_device_protocol_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `label` varchar(64) DEFAULT NULL COMMENT '标签',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `value` int DEFAULT '0' COMMENT '配置值',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='设备协议配置表';

-- ----------------------------
-- Records of ez_device_protocol_config
-- ----------------------------
BEGIN;
INSERT INTO `ez_device_protocol_config` VALUES (1, 'MQTT', 'MQTT', 0, 'MQTT协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config` VALUES (2, 'COAP', 'COAP', 1, 'COAP协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config` VALUES (3, 'HTTP', 'HTTP', 2, 'HTTP协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config` VALUES (4, 'TCP', 'TCP', 3, 'TCP协议', 0, 0, '2020-03-17 22:30:21');
COMMIT;

-- ----------------------------
-- Table structure for ez_device_rom_support_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_device_rom_support_config`;
CREATE TABLE `ez_device_rom_support_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `value` varchar(16) DEFAULT NULL COMMENT '值',
  `program_location` varchar(1000) DEFAULT NULL COMMENT '固件代码位置',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='支持的固件';

-- ----------------------------
-- Records of ez_device_rom_support_config
-- ----------------------------
BEGIN;
INSERT INTO `ez_device_rom_support_config` VALUES (1, 'STM32', 'https://github.com/wwhai/STM32-MQTT.git', '通用STM32开发板', 0, 0, '2020-05-04 22:49:07');
INSERT INTO `ez_device_rom_support_config` VALUES (2, 'ESP8266', 'https://github.com/wwhai/ezlinker_arduino_sdk.git', 'ESP8266模块', 0, 0, '2020-05-04 22:49:30');
INSERT INTO `ez_device_rom_support_config` VALUES (3, 'ESP32', 'https://github.com/wwhai/ezlinker_arduino_sdk.git', 'ESP32模块', 0, 0, '2020-05-04 22:49:45');
INSERT INTO `ez_device_rom_support_config` VALUES (4, 'RaspberryPi', 'https://github.com/wwhai/PythonMqttClient.git', '树莓派', 0, 0, '2020-05-04 22:50:18');
INSERT INTO `ez_device_rom_support_config` VALUES (5, 'Arduino', 'https://github.com/wwhai/ezlinker_arduino_sdk.git', '通用Arduino', 0, 0, '2020-05-05 13:49:35');
INSERT INTO `ez_device_rom_support_config` VALUES (6, 'NB-IOT-MQTT', 'https://github.com/wwhai/NB-IOT-MQTT.git', '战旗开发板MINI-V2', 0, 0, '2020-05-05 14:09:02');
COMMIT;

-- ----------------------------
-- Table structure for ez_dictionary_key
-- ----------------------------
DROP TABLE IF EXISTS `ez_dictionary_key`;
CREATE TABLE `ez_dictionary_key` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `table_name` varchar(64) DEFAULT NULL COMMENT '表名',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `label` varchar(200) DEFAULT NULL COMMENT '显示的文本',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典的项';

-- ----------------------------
-- Table structure for ez_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS `ez_dictionary_value`;
CREATE TABLE `ez_dictionary_value` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `key_id` int DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '显示的文本',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典的值';

-- ----------------------------
-- Table structure for ez_emqx_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_emqx_config`;
CREATE TABLE `ez_emqx_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `ip` varchar(16) DEFAULT NULL COMMENT 'EMQX IP地址',
  `app_id` varchar(200) DEFAULT NULL COMMENT 'HTTP接口的ID',
  `secret` varchar(200) DEFAULT NULL COMMENT 'APP接口密钥',
  `port` int DEFAULT NULL COMMENT 'HTTP接口端口',
  `node_name` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `state` int DEFAULT NULL COMMENT '0:离线;1:在线',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='EMQX配置表';

-- ----------------------------
-- Records of ez_emqx_config
-- ----------------------------
BEGIN;
INSERT INTO `ez_emqx_config` VALUES (1, '127.0.0.1', 'admin', 'public', 18001, 'emqx@node1.emqx.io', 1, '默认本地节点1', 9, 0, '2020-03-06 13:56:13');
INSERT INTO `ez_emqx_config` VALUES (2, '127.0.0.1', 'admin', 'public', 18002, 'emqx@node2.emqx.io', 1, '默认本地节点2', 7, 0, '2020-03-06 13:56:13');
COMMIT;

-- ----------------------------
-- Table structure for ez_feature
-- ----------------------------
DROP TABLE IF EXISTS `ez_feature`;
CREATE TABLE `ez_feature` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL COMMENT '所属产品',
  `label` varchar(20) DEFAULT NULL,
  `type` int DEFAULT NULL COMMENT '类型',
  `cmd_key` varchar(20) NOT NULL,
  `cmd_values` json NOT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(4) unsigned zerofill NOT NULL DEFAULT '0000',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='产品的功能（特性），和设备是多对多的关系，通过中间表关联起来';

-- ----------------------------
-- Table structure for ez_icon_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_icon_config`;
CREATE TABLE `ez_icon_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `url` varchar(500) DEFAULT NULL COMMENT 'URL',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='后端维持的图表库配置表';

-- ----------------------------
-- Table structure for ez_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_module`;
CREATE TABLE `ez_module` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `device_id` int DEFAULT NULL COMMENT '绑定的设备',
  `type` int DEFAULT NULL COMMENT '模块类型',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `token` varchar(255) DEFAULT NULL COMMENT 'Token',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `data_areas` json DEFAULT NULL COMMENT '数据域',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='设备上面的模块，和设备是多对一关系';

-- ----------------------------
-- Records of ez_module
-- ----------------------------
BEGIN;
INSERT INTO `ez_module` VALUES (1, 1, NULL, 'shot', 'Em+J++MwoFg4My8+xApjgPce+aoVPqPBOc4s9jSjulDwboqiDoPM0ggYRlBzo/YBqzmdxm1Vm3apJueWoH8WjSXH3DDma4OtSaIJrvYugJucw1soyalSUB3KTW4iN8xI5vgwz2K3M5jh1VV74UC1ym6BJ72LNLGkPb0rZNAGDcs=', NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[{\"type\": 1, \"field\": \"cmd\", \"description\": \"按钮事件\", \"defaultValue\": \"0\"}]', NULL, 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_module` VALUES (2, 2, NULL, 'shot', 'kMZo6VIbjsnCsGJm1H0+iAdkhq2GyRwqzmNCuvIvNObR/3A6lZLfv6POqETP72Seyne6dtF+OjbTqIwxpH7wdGlbTRqJqimWWWZiaq8Oj27XrbL0/mI/R1/z8UvMd7NiwelpTfKqX4KVUV7Ju7Uk0OgSTrrbEhgD4P6mnpmqC0A=', NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[{\"type\": 1, \"field\": \"cmd\", \"description\": \"按钮事件\", \"defaultValue\": \"0\"}]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module` VALUES (3, 2, NULL, 'swtich', 'Ju8mZ5CPSbviEvY9qE504yHNGfIwjOddHurws3FtHDluEd2OOYz8MqAorhv/cyuvyzDfNoaJ/fTXbIS/aXDR/suQ5/kyujDM0d8gkVpBLLwk1maIUsfjNBiYfERhEdt3zIOx1/7SC9FJrSAe7qSzKV54b39aW8/sK08qlmQvglg=', NULL, 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', '[{\"type\": 3, \"field\": \"state\", \"description\": \"开关状态\", \"defaultValue\": \"false\"}]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module` VALUES (4, 2, NULL, '数据急', 'esYXNTcGaTlOmrY42ax4jTl0bbPCywm34m7fHqxPDShlHOd46fENTBBK5JFlMUMyoNIz3NeGXgjfcmOe9C6i/Xo44OE6lA8cffN2vg34UbHXarjhsdbaCxoQILF4vbkKApx7F/Ts/22uju3idUbrY487yIp6Ej0vh1c687SK/B0=', NULL, NULL, '[{\"type\": 1, \"field\": \"数值\", \"description\": \"1\", \"defaultValue\": \"0\"}, {\"type\": 2, \"field\": \"备注\", \"description\": \"2\", \"defaultValue\": \"default\"}]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module` VALUES (5, 3, NULL, 'shot', 'fO8bdpQTWXiQHTS+xjtgA1P25SiKnsT49l3mu1TodEdCNfw8bgiLmkHrJ3W5zE7+Zseyc4IdTud9CkvFuXGGVccOo5docMsMST01nbvgOewofgWrLxVnvQ94e0LGpoe1uYEUy+zexopEWwgNTN+BjYwbTvxq+QhEvJzeWWTGqQQ=', NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[{\"type\": 1, \"field\": \"cmd\", \"description\": \"按钮事件\", \"defaultValue\": \"0\"}]', NULL, 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_module` VALUES (6, 3, NULL, 'swtich', 'dAPBhhvI1oHDsJ8xypjM/EIyT8SlN1TdZRmrNdmAyERabHfb6VVuWWYewlSay24/rwnM21hbP9uTCKJ6MWA3oor18fhqDONPVyx7Z/VOQSwdz8lf91GGkEziZ0cEn3QDxyeAAQMfyM5Rovp7+Dl9XmEMQ5i0icxHPkPEV4lHGIs=', NULL, 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', '[{\"type\": 3, \"field\": \"state\", \"description\": \"开关状态\", \"defaultValue\": \"false\"}]', NULL, 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_module` VALUES (7, 3, NULL, '数据急', 'kt0fxjuBvfMpylSJsJR7jzVFDYUcC4sPNNUVlLnYT1D1lE2w7Ln+HB/m3tBRaSzs0/AQIkh0VCZqeGob9H5xCjBrAI3xgonh6k/PX+XWxJU2ZfwHKMTJoFe2kk9SqZVXuBSxK5KcxWnFBXhBUXFunY28QTq0NUcY1w6gLUKYEnM=', NULL, NULL, '[{\"type\": 1, \"field\": \"数值\", \"description\": \"1\", \"defaultValue\": \"0\"}, {\"type\": 2, \"field\": \"备注\", \"description\": \"2\", \"defaultValue\": \"default\"}]', NULL, 0, 0, '2020-04-05 14:42:21');
COMMIT;

-- ----------------------------
-- Table structure for ez_module_template
-- ----------------------------
DROP TABLE IF EXISTS `ez_module_template`;
CREATE TABLE `ez_module_template` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `product_id` int NOT NULL COMMENT '产品ID',
  `type` varchar(30) DEFAULT NULL COMMENT '模块类型',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `action_config` json DEFAULT NULL,
  `data_areas` json DEFAULT NULL COMMENT '数据域',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='产品上面的模块模板';

-- ----------------------------
-- Records of ez_module_template
-- ----------------------------
BEGIN;
INSERT INTO `ez_module_template` VALUES (1, 1, 'BUTTON', '按钮模块', 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', NULL, '[{\"type\": 1, \"field\": \"cmd\", \"description\": \"按钮事件\", \"defaultValue\": \"0\"}]', '按钮', 0, 0, '2020-03-24 21:30:46');
INSERT INTO `ez_module_template` VALUES (2, 1, 'SWITCH', '开关模块', 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', NULL, '[{\"type\": 3, \"field\": \"state\", \"description\": \"开关状态\", \"defaultValue\": \"false\"}]', '开关', 0, 0, '2020-03-31 22:52:26');
COMMIT;

-- ----------------------------
-- Table structure for ez_module_template_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_module_template_config`;
CREATE TABLE `ez_module_template_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `type` int DEFAULT '0' COMMENT '类型，1：控件，2：视图',
  `label` varchar(64) DEFAULT NULL COMMENT '标签',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `action_config` json DEFAULT NULL COMMENT '事件配置',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='模块类型配置表';

-- ----------------------------
-- Records of ez_module_template_config
-- ----------------------------
BEGIN;
INSERT INTO `ez_module_template_config` VALUES (1, 1, '自定义类型', 'https://s1.ax1x.com/2020/03/19/8yUsN8.png', '[{\"action\": \"sendData\", \"trigger\": \"onClick\"}]', 'CUSTOMIZE', '自定义类型', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_template_config` VALUES (2, 1, '按钮', 'https://s1.ax1x.com/2020/03/19/8yUajA.png', '[{\"action\": \"sendData\", \"trigger\": \"onClick\"}]', 'BUTTON', '按钮', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_template_config` VALUES (3, 1, '开关', 'https://s1.ax1x.com/2020/03/19/8yUwnI.png', '[{\"action\": \"sendData\", \"trigger\": \"onClick\"}]', 'SWITCH', '开关', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_template_config` VALUES (4, 1, '进度条', 'https://s1.ax1x.com/2020/03/19/8yU0Bt.png', '[{\"action\": \"sendData\", \"trigger\": \"onClick\"}]', 'PROGRESS_BAR', '进度条', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_template_config` VALUES (5, 2, '数据体', 'https://s1.ax1x.com/2020/03/19/8yUrAf.png', '[{\"action\": \"sendData\", \"trigger\": \"onClick\"}]', 'DATA_ENTITY', '数据体', 0, 0, '2020-03-17 22:10:08');
COMMIT;

-- ----------------------------
-- Table structure for ez_mqtt_topic
-- ----------------------------
DROP TABLE IF EXISTS `ez_mqtt_topic`;
CREATE TABLE `ez_mqtt_topic` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `device_id` int DEFAULT NULL COMMENT '设备',
  `client_id` varchar(64) DEFAULT NULL COMMENT 'MQTT客户端ID',
  `allow` int DEFAULT '1' COMMENT '是否允许连接: 0=拒绝1=允许',
  `type` tinyint DEFAULT '0' COMMENT '类型 1：S2C；2：C2S；3：STATUS；4：GROUP',
  `access` int DEFAULT '1' COMMENT '行为类型: 1=订阅2=发布3=订阅+发布',
  `ip` varchar(16) DEFAULT NULL COMMENT 'IP',
  `username` varchar(255) DEFAULT NULL COMMENT 'MQTT用户名',
  `topic` varchar(200) DEFAULT NULL COMMENT '路由',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(1) unsigned zerofill DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='MQTT的TOPIC记录';

-- ----------------------------
-- Records of ez_mqtt_topic
-- ----------------------------
BEGIN;
INSERT INTO `ez_mqtt_topic` VALUES (1, 1, '4d45d94142276ad38364049c56d8ed43', 1, 1, 2, '0.0.0.0', '265447ee58775710646ea18d1fe2469e', 'mqtt/out/4d45d94142276ad38364049c56d8ed43/s2c', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic` VALUES (2, 1, '4d45d94142276ad38364049c56d8ed43', 1, 2, 1, '0.0.0.0', '265447ee58775710646ea18d1fe2469e', 'mqtt/in/4d45d94142276ad38364049c56d8ed43/c2s', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic` VALUES (3, 1, '4d45d94142276ad38364049c56d8ed43', 1, 3, 1, '0.0.0.0', '265447ee58775710646ea18d1fe2469e', 'mqtt/in/4d45d94142276ad38364049c56d8ed43/status', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic` VALUES (4, 3, 'cfb54735067705280df30e6028d16aa1', 1, 1, 2, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f', 'mqtt/out/cfb54735067705280df30e6028d16aa1/s2c', 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_mqtt_topic` VALUES (5, 3, 'cfb54735067705280df30e6028d16aa1', 1, 2, 1, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f', 'mqtt/in/cfb54735067705280df30e6028d16aa1/c2s', 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_mqtt_topic` VALUES (6, 3, 'cfb54735067705280df30e6028d16aa1', 1, 3, 1, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f', 'mqtt/in/cfb54735067705280df30e6028d16aa1/status', 0, 0, '2020-04-05 14:42:21');
COMMIT;

-- ----------------------------
-- Table structure for ez_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_permission`;
CREATE TABLE `ez_permission` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `sort` int unsigned DEFAULT '0' COMMENT '排序值',
  `type` int NOT NULL COMMENT '类型：1目录，2动作',
  `visible` tinyint unsigned DEFAULT '0' COMMENT '是否显示在UI上',
  `label` varchar(20) NOT NULL COMMENT 'UI显示的标签',
  `name` varchar(20) NOT NULL COMMENT '名称（英文）',
  `resource` varchar(60) NOT NULL COMMENT '请求路径',
  `parent` int NOT NULL COMMENT '父资源，如果是0则为顶级',
  `description` varchar(200) DEFAULT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户权限';

-- ----------------------------
-- Records of ez_permission
-- ----------------------------
BEGIN;
INSERT INTO `ez_permission` VALUES (1, 0, 1, 0, '主页', 'HOME', '/home', 0, '主页', 0, 0, '2020-03-06 10:21:06');
INSERT INTO `ez_permission` VALUES (2, 0, 1, 0, '个人中心', 'PERSONAL_INFO', '/personalInfo', 0, '个人中心', 0, 0, '2020-03-06 10:21:06');
INSERT INTO `ez_permission` VALUES (3, 0, 1, 0, '项目管理', 'PROJECT_MANAGER', '/projects', 0, '项目管理', 0, 0, '2020-03-06 10:21:06');
COMMIT;

-- ----------------------------
-- Table structure for ez_product
-- ----------------------------
DROP TABLE IF EXISTS `ez_product`;
CREATE TABLE `ez_product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `project_id` int NOT NULL COMMENT '项目ID',
  `name` varchar(20) NOT NULL COMMENT '产品名称',
  `logo` varchar(200) NOT NULL COMMENT '产品logo',
  `type` varchar(4) DEFAULT NULL COMMENT '类型',
  `state` int DEFAULT NULL COMMENT '当0设备的时候,状态为:设计中;当已经有设备以后,状态为:生产中,此时不可更改关键数据.',
  `layout` json DEFAULT NULL COMMENT '布局描述',
  `protocol` int DEFAULT NULL COMMENT '协议类型',
  `parameters` json DEFAULT NULL COMMENT '参数',
  `description` varchar(200) DEFAULT NULL COMMENT '描述文字',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(1) unsigned zerofill NOT NULL COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='产品（设备的抽象模板）';

-- ----------------------------
-- Records of ez_product
-- ----------------------------
BEGIN;
INSERT INTO `ez_product` VALUES (1, 1, '这是个测试产品1', 'https://s2.ax1x.com/2020/03/03/3fztzV.th.png', '1', NULL, NULL, 0, '[{\"type\": 1, \"unit\": null, \"field\": \"p1\", \"description\": \"\", \"defaultValue\": \"0\"}]', 'testtt', 0, 0, '2020-08-22 23:49:01');
COMMIT;

-- ----------------------------
-- Table structure for ez_project
-- ----------------------------
DROP TABLE IF EXISTS `ez_project`;
CREATE TABLE `ez_project` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `logo` varchar(200) DEFAULT NULL,
  `type` int NOT NULL COMMENT '类型',
  `state` int DEFAULT NULL COMMENT '状态',
  `description` varchar(200) DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='项目';

-- ----------------------------
-- Records of ez_project
-- ----------------------------
BEGIN;
INSERT INTO `ez_project` VALUES (1, '测试项目A', 'https://s2.ax1x.com/2020/03/03/3fz6Rx.th.png', 2, 0, '这是个测试数据', NULL, 0, 0, '2020-08-22 23:48:21');
COMMIT;

-- ----------------------------
-- Table structure for ez_relation_device_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_device_module`;
CREATE TABLE `ez_relation_device_module` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `device_id` int NOT NULL COMMENT '设备',
  `module_id` int NOT NULL COMMENT ' 模块',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ez_relation_feature_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_feature_module`;
CREATE TABLE `ez_relation_feature_module` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `feature_id` int NOT NULL COMMENT '功能',
  `module_id` int NOT NULL COMMENT ' 模块',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ez_relation_module_visual_style
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_module_visual_style`;
CREATE TABLE `ez_relation_module_visual_style` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `module_id` int NOT NULL COMMENT '模块ID',
  `visual_style_id` int NOT NULL COMMENT '视觉效果ID',
  `fields` varchar(200) NOT NULL COMMENT '需要展示的数据字段',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='模块和可视化视觉展示效果关联表';

-- ----------------------------
-- Table structure for ez_relation_product_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_product_module`;
CREATE TABLE `ez_relation_product_module` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `product_id` int NOT NULL COMMENT '设备',
  `module_id` int NOT NULL COMMENT ' 模块',
  `record_version` int DEFAULT NULL,
  `x` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ez_relation_protocol_type_module_type
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_protocol_type_module_type`;
CREATE TABLE `ez_relation_protocol_type_module_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `protocol_type_id` int NOT NULL COMMENT '协议类型',
  `module_type_id` int NOT NULL COMMENT '模块类型',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='XXX协议支持的XXX模块类型';

-- ----------------------------
-- Table structure for ez_relation_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_role_permission`;
CREATE TABLE `ez_relation_role_permission` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `permission_value` int DEFAULT NULL COMMENT '权限值',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色和权限关联表';

-- ----------------------------
-- Records of ez_relation_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `ez_relation_role_permission` VALUES (1, 1, 1, 1, 0, 0, '2020-03-06 00:05:37');
INSERT INTO `ez_relation_role_permission` VALUES (2, 1, 2, 1, 0, 0, '2020-03-06 00:05:37');
COMMIT;

-- ----------------------------
-- Table structure for ez_relation_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_permission`;
CREATE TABLE `ez_relation_user_permission` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `user_id` int NOT NULL COMMENT '用户ID',
  `permission_id` int NOT NULL COMMENT '资源路径ID',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户和资源路径关系表';

-- ----------------------------
-- Table structure for ez_relation_user_project
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_project`;
CREATE TABLE `ez_relation_user_project` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `user_id` int NOT NULL COMMENT '用户ID',
  `project_id` int NOT NULL COMMENT '项目ID',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户和项目关联表，用来给用户授权具体项目';

-- ----------------------------
-- Table structure for ez_relation_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_role`;
CREATE TABLE `ez_relation_user_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户-角色关联';

-- ----------------------------
-- Records of ez_relation_user_role
-- ----------------------------
BEGIN;
INSERT INTO `ez_relation_user_role` VALUES (1, 1, 1, 0, 0, '2020-03-06 00:10:59');
INSERT INTO `ez_relation_user_role` VALUES (2, 1, 2, 0, 0, '2020-03-06 00:10:59');
COMMIT;

-- ----------------------------
-- Table structure for ez_relation_user_subuser
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_subuser`;
CREATE TABLE `ez_relation_user_subuser` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `user_id` int NOT NULL,
  `sub_user_id` int NOT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-子用户关联';

-- ----------------------------
-- Table structure for ez_resource_authorize
-- ----------------------------
DROP TABLE IF EXISTS `ez_resource_authorize`;
CREATE TABLE `ez_resource_authorize` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `master_id` varchar(255) NOT NULL COMMENT '作用者',
  `master_table` varchar(255) DEFAULT NULL COMMENT '作用者表名',
  `slaver_id` int NOT NULL COMMENT '被作用的对象',
  `slaver_table` varchar(255) DEFAULT NULL COMMENT '被作用者的表名',
  `authorize_value` int NOT NULL COMMENT '授权值',
  `x` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除',
  `record_version` int unsigned NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='资源授权表,记录所有存在权限关系的双方';

-- ----------------------------
-- Table structure for ez_role
-- ----------------------------
DROP TABLE IF EXISTS `ez_role`;
CREATE TABLE `ez_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `label` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `parent` int NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色';

-- ----------------------------
-- Records of ez_role
-- ----------------------------
BEGIN;
INSERT INTO `ez_role` VALUES (1, '超级管理员', 'SUPER_ADMIN', 0, '超级管理员', 0, 0, '2020-03-05 23:59:38');
INSERT INTO `ez_role` VALUES (2, '普通用户', 'PERSONAL', 0, '普通用户', 0, 0, '2020-03-05 23:59:38');
COMMIT;

-- ----------------------------
-- Table structure for ez_schedule_info
-- ----------------------------
DROP TABLE IF EXISTS `ez_schedule_info`;
CREATE TABLE `ez_schedule_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `device_id` int DEFAULT NULL COMMENT '作用方ID',
  `job_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务组名称',
  `trigger_name` varchar(500) DEFAULT NULL COMMENT '触发器名称',
  `trigger_group` varchar(500) DEFAULT NULL COMMENT '触发器组',
  `cron_expression` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表达式',
  `execute_class` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '目标执行类类名',
  `is_start` tinyint(1) DEFAULT NULL COMMENT '是否启动',
  `status` int DEFAULT '1' COMMENT '0:停止,1:暂停:2:启动',
  `points` json DEFAULT NULL COMMENT '作用的设备列表',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务描述',
  `schedule_data` json DEFAULT NULL COMMENT '指令内容',
  `record_version` int DEFAULT NULL,
  `x` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='实际的定时任务';

-- ----------------------------
-- Records of ez_schedule_info
-- ----------------------------
BEGIN;
INSERT INTO `ez_schedule_info` VALUES (1, 1, 'task001', 'DEFAULT_JOB_GROUP', NULL, 'DEFAULT_TRIGGER_GROUP', '0/1 * * * * ? ', NULL, NULL, 2, '[1, 2]', 'a task', '{\"K\": \"KK\", \"V\": \"VV\"}', 0, 0, '2020-08-24 23:56:21');
INSERT INTO `ez_schedule_info` VALUES (2, 1, 'task001', 'DEFAULT_JOB_GROUP', NULL, 'DEFAULT_TRIGGER_GROUP', '0/1 * * * * ? ', NULL, NULL, 2, '[1, 2]', 'a task', '{\"K\": \"KK\", \"V\": \"VV\"}', 0, 0, '2020-08-24 23:57:35');
COMMIT;

-- ----------------------------
-- Table structure for ez_schedule_template
-- ----------------------------
DROP TABLE IF EXISTS `ez_schedule_template`;
CREATE TABLE `ez_schedule_template` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL COMMENT '定时任务作用的产品',
  `task_name` varchar(500) DEFAULT NULL COMMENT '任务名称',
  `schedule_data` json DEFAULT NULL COMMENT '指令内容',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'CRON',
  `task_description` varchar(500) DEFAULT NULL COMMENT '任务描述',
  `record_version` int DEFAULT NULL,
  `x` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务模板';

-- ----------------------------
-- Table structure for ez_shared_device
-- ----------------------------
DROP TABLE IF EXISTS `ez_shared_device`;
CREATE TABLE `ez_shared_device` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `user_id` int NOT NULL COMMENT '用户ID',
  `device_id` int NOT NULL COMMENT '设备ID',
  `record_version` int NOT NULL DEFAULT '0',
  `x` tinyint(1) unsigned zerofill NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='共享设备记录表';

-- ----------------------------
-- Table structure for ez_stream_integration
-- ----------------------------
DROP TABLE IF EXISTS `ez_stream_integration`;
CREATE TABLE `ez_stream_integration` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `module_id` int unsigned NOT NULL COMMENT '设备ID',
  `token` varchar(100) DEFAULT NULL COMMENT '令牌',
  `secret` varchar(100) DEFAULT NULL COMMENT '密钥',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='流媒体推送密钥';

-- ----------------------------
-- Table structure for ez_user
-- ----------------------------
DROP TABLE IF EXISTS `ez_user`;
CREATE TABLE `ez_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `avatar` varchar(10) DEFAULT NULL COMMENT '头像地址,是一个数字，标记一个PNG图片',
  `phone` varchar(11) NOT NULL COMMENT '手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `real_name` varchar(20) DEFAULT NULL COMMENT '实名',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `user_type` int DEFAULT NULL COMMENT '用户类型：普通用户【1】，企业用户【2】，VIP用户【3】',
  `status` int DEFAULT NULL COMMENT '账户状态：正常【1】，冻结【2】，过期【3】',
  `user_profile_id` int NOT NULL COMMENT '扩展信息',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `last_login_ip` varchar(16) DEFAULT NULL COMMENT '上次登录IP',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `record_version` int unsigned NOT NULL COMMENT '记录版本',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统用户';

-- ----------------------------
-- Records of ez_user
-- ----------------------------
BEGIN;
INSERT INTO `ez_user` VALUES (1, 'ezlinker', '5F4DCC3B5AA765D61D8327DEB882CF99', '1', '18059150204', 'ezlinker@ez.com.cn', 'EZLinker官方', 'EZLINKER-OfficialA', 1, 1, 0, '2020-08-22 23:47:45', '192.168.1.69', 0, 310, '2019-11-11 22:23:02');
INSERT INTO `ez_user` VALUES (2, 'wwhai', '5F4DCC3B5AA765D61D8327DEB882CF99', '2', '18059150205', 'wwhai@qq.com', '程序猿老王', 'NeighborOldWang', 1, 1, 0, NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user` VALUES (3, 'liuchun', '5F4DCC3B5AA765D61D8327DEB882CF99', '3', '18059150206', 'liuchun@qq.com', '刘总', 'CEO.Liu', 1, 1, 0, NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user` VALUES (4, 'liuzy', '5F4DCC3B5AA765D61D8327DEB882CF99', '4', '18059150207', 'lzy@qq.com', '小刘', 'CTO.Liu', 1, 1, 0, NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user` VALUES (6, 'username', '25d55ad283aa400af464c76d713c07ad', '1', '15122225555', '123@qq.com', NULL, NULL, NULL, 2, 7, NULL, NULL, 0, 0, '2019-12-19 21:44:53');
INSERT INTO `ez_user` VALUES (7, 'LiuZeYu', '25d55ad283aa400af464c76d713c07ad', '1', '18659199666', 'LiuZeYu@qq.com', NULL, NULL, NULL, 2, 8, NULL, NULL, 0, 0, '2019-12-19 22:26:22');
COMMIT;

-- ----------------------------
-- Table structure for ez_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `ez_user_profile`;
CREATE TABLE `ez_user_profile` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `region` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `area` varchar(30) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `domain` varchar(50) DEFAULT NULL,
  `personal_remark` varchar(200) DEFAULT NULL,
  `certification` varchar(255) DEFAULT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `wechat` varchar(15) DEFAULT NULL,
  `x` tinyint unsigned DEFAULT '0',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户的详情';

-- ----------------------------
-- Records of ez_user_profile
-- ----------------------------
BEGIN;
INSERT INTO `ez_user_profile` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:28:56');
INSERT INTO `ez_user_profile` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:29:57');
INSERT INTO `ez_user_profile` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:31:01');
INSERT INTO `ez_user_profile` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:34:38');
INSERT INTO `ez_user_profile` VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:42:23');
INSERT INTO `ez_user_profile` VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:43:52');
INSERT INTO `ez_user_profile` VALUES (7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:44:53');
INSERT INTO `ez_user_profile` VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 22:26:22');
COMMIT;

-- ----------------------------
-- Table structure for ez_visual_style
-- ----------------------------
DROP TABLE IF EXISTS `ez_visual_style`;
CREATE TABLE `ez_visual_style` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL COMMENT '可视化视觉名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `description` varchar(200) DEFAULT NULL COMMENT '可视化视觉的描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='可视化视觉展示效果';

-- ----------------------------
-- Table structure for ez_visual_style_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_visual_style_config`;
CREATE TABLE `ez_visual_style_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(20) NOT NULL COMMENT '可视化视觉名称',
  `label` varchar(255) DEFAULT NULL COMMENT 'UI文本',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `description` varchar(200) DEFAULT NULL COMMENT '可视化视觉的描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='可视化视觉展示效果';

-- ----------------------------
-- Table structure for ez_wx_app_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_wx_app_config`;
CREATE TABLE `ez_wx_app_config` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(64) DEFAULT NULL COMMENT 'EZLINKER授权密钥名称',
  `token` varchar(200) DEFAULT NULL COMMENT 'EZLINKER授权密钥',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `record_version` int NOT NULL DEFAULT '0' COMMENT '记录版本',
  `x` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='微信小程序密钥配置表';

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactoryBean', 'TRIGGER=>TID:-DID:1', 'TestGroup', '0/1 * * * * ? ', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_fired_triggers` VALUES ('schedulerFactoryBean', 'NON_CLUSTERED1598278162748', 'TRIGGER_com.ezlinker.app.modules.schedule.job.ScheduleSendDataJob_1_1', 'COMMAND_GROUP', 'NON_CLUSTERED', 1598278172551, 1598278173000, 5, 'ACQUIRED', NULL, NULL, '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactoryBean', 'JOB=>TID:-DID:1', 'TestGroup', 'JUST_A_JOB', 'com.ezlinker.app.modules.schedule.job.ScheduleSendDataJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactoryBean', 'TRIGGER=>TID:-DID:1', 'TestGroup', 'JOB=>TID:-DID:1', 'TestGroup', 'JUST_A_TRIGGER', 1598284581000, -1, 5, 'WAITING', 'CRON', 1598284581000, 0, NULL, 2, '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
