/*
 Navicat Premium Data Transfer

 Source Server         : 赵雷的服务器
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 112.74.44.130:3306
 Source Schema         : ezlinker

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 07/04/2020 22:16:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ez_aliyun_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_aliyun_config`;
CREATE TABLE `ez_aliyun_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `access_key`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '阿里云访问Key',
    `secret`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '阿里云密钥',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '阿里云密钥配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_aliyun_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_aliyun_sms_config`;
CREATE TABLE `ez_aliyun_sms_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `region_id`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '区域',
    `account_name`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '账户名',
    `from`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '发信人(自己)',
    `address_type`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '地址类型(默认为1)',
    `tag_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '标签,平台申请以后会发',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '阿里云短信参数配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_cloud_function
-- ----------------------------
DROP TABLE IF EXISTS `ez_cloud_function`;
CREATE TABLE `ez_cloud_function`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `label`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'UI显示的标签',
    `enable`         tinyint(4) UNSIGNED                                           NOT NULL DEFAULT 1 COMMENT '是否开启',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'Commet',
    `script`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT 'Script content',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0,
    `create_time`    datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '云函数'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_device
-- ----------------------------
DROP TABLE IF EXISTS `ez_device`;
CREATE TABLE `ez_device`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `project_id`     int(11)                                                       NOT NULL COMMENT '项目',
    `product_id`     int(11)                                                       NOT NULL COMMENT '产品',
    `protocol`       int(11)                                                       NULL     DEFAULT NULL COMMENT '协议类型',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称',
    `logo`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Logo',
    `location`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '地理位置',
    `model`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '型号',
    `industry`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '厂家',
    `sn`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '序列号',
    `type`           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '类型',
    `last_active`    datetime(0)                                                   NULL     DEFAULT NULL,
    `state`          int(11)                                                       NULL     DEFAULT NULL COMMENT '1:在线;2:离线;3未激活',
    `statuses`       json                                                          NULL COMMENT '运行的状态',
    `username`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'MQTT用户名',
    `client_id`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'MQTT ClientID',
    `password`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'MQTT密码',
    `is_superuser`   int(11)                                                       NULL     DEFAULT NULL COMMENT '是否超级权限',
    `token`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '认证token',
    `parameters`     json                                                          NULL COMMENT '参数',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL                                  NOT NULL COMMENT '是否删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '实际设备，是产品的一个实例。在EMQX中扮演 的是USER,支持两种认证形式:clientId和Username\\password.' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_device
-- ----------------------------
INSERT INTO `ez_device` VALUES (1, 1, 1, 1, '炮管', 'icon9', NULL, NULL, NULL, 'SN402422806463254528', NULL, NULL, NULL, NULL, '265447ee58775710646ea18d1fe2469e', '4d45d94142276ad38364049c56d8ed43', '36496ebe4dc9db5482aa9d0a6959a932', NULL, NULL, '[{\"type\": 1, \"field\": \"name\", \"description\": \"名字\", \"defaultValue\": \"default\"}]', '描述文本:2019-12-07 20:29:49', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_device` VALUES (2, 1, 1, 1, 'pao', 'icon9', NULL, NULL, NULL, 'SN406666673375023104', NULL, NULL, NULL, NULL, 'fd74b4c0d50f62c7e6e01945f8d48398', '1cffbdb3d8a2e19461c4c3faa9fd7786', '8e3518fe70600eb24ba4b894f4a6bb70', 0, NULL, '[{\"type\": 1, \"field\": \"name\", \"description\": \"名字\", \"defaultValue\": \"default\"}]', '跑【奥【傲', 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_device` VALUES (3, 1, 1, 1, '请问哦', 'icon9', NULL, NULL, NULL, 'SN406668520051576832', NULL, NULL, NULL, NULL, 'e29936f9443fdd17d520ed731cfd564f', 'cfb54735067705280df30e6028d16aa1', '3288fd3bc137e5895e3e188a2fcabc7c', 0, NULL, '[{\"type\": 1, \"field\": \"name\", \"description\": \"名字\", \"defaultValue\": \"default\"}]', '我企鹅武器', 0, 0, '2020-04-05 14:42:21');

-- ----------------------------
-- Table structure for ez_device_protocol_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_device_protocol_config`;
CREATE TABLE `ez_device_protocol_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `label`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标签',
    `name`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '名称',
    `value`          int(11)                                                       NULL     DEFAULT 0 COMMENT '配置值',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '设备协议配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_device_protocol_config
-- ----------------------------
INSERT INTO `ez_device_protocol_config`
VALUES (1, 'MQTT', 'MQTT', 0, 'MQTT协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config`
VALUES (2, 'COAP', 'COAP', 1, 'COAP协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config`
VALUES (3, 'HTTP', 'HTTP', 2, 'HTTP协议', 0, 0, '2020-03-17 22:30:21');
INSERT INTO `ez_device_protocol_config`
VALUES (4, 'TCP', 'TCP', 3, 'TCP协议', 0, 0, '2020-03-17 22:30:21');

-- ----------------------------
-- Table structure for ez_dictionary_key
-- ----------------------------
DROP TABLE IF EXISTS `ez_dictionary_key`;
CREATE TABLE `ez_dictionary_key`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `table_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '表名',
    `name`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '名称',
    `label`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '显示的文本',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典的项'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS `ez_dictionary_value`;
CREATE TABLE `ez_dictionary_value`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `key_id`         int(11)                                                       NULL     DEFAULT NULL,
    `value`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '名称',
    `label`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '显示的文本',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0,
    `create_time`    datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '字典的值'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_emqx_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_emqx_config`;
CREATE TABLE `ez_emqx_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `ip`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'EMQX IP地址',
    `app_id`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'HTTP接口的ID',
    `secret`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'APP接口密钥',
    `port`           int(11)                                                       NULL     DEFAULT NULL COMMENT 'HTTP接口端口',
    `node_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '节点名称',
    `state`          int(11)                                                       NULL     DEFAULT NULL COMMENT '0:离线;1:在线',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'EMQX配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_emqx_config
-- ----------------------------
INSERT INTO `ez_emqx_config`
VALUES (1, 'localhost', 'a4315772bf6dd', 'MjkyMTE2MDIwMjE2MTIwNjk1NzYzMzQ5OTIxNDI0OTk4NDA', 8081, 'emqx@127.0.0.1', 0,
        '默认本地节点1', 2, 0, '2020-03-06 13:56:13');
INSERT INTO `ez_emqx_config`
VALUES (2, 'localhost', '1', '1', 8081, 'node2@127.0.0.1', NULL, '默认本地节点2', 0, 0, '2020-03-06 13:56:13');

-- ----------------------------
-- Table structure for ez_feature
-- ----------------------------
DROP TABLE IF EXISTS `ez_feature`;
CREATE TABLE `ez_feature`
(
    `id`             int(10) UNSIGNED                                             NOT NULL AUTO_INCREMENT,
    `product_id`     int(11)                                                      NULL     DEFAULT NULL COMMENT '所属产品',
    `label`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `type`           int(11)                                                      NULL     DEFAULT NULL COMMENT '类型',
    `cmd_key`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `cmd_values`     json                                                         NOT NULL,
    `record_version` int(11)                                                      NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(4) UNSIGNED ZEROFILL                                 NOT NULL DEFAULT 0000,
    `create_time`    datetime(0)                                                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '产品的功能（特性），和设备是多对多的关系，通过中间表关联起来'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_icon_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_icon_config`;
CREATE TABLE `ez_icon_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '名称',
    `url`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'URL',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '后端维持的图表库配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_module`;
CREATE TABLE `ez_module`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `device_id`      int(11)                                                       NULL     DEFAULT NULL COMMENT '绑定的设备',
    `type`           int(11)                                                       NULL     DEFAULT NULL COMMENT '模块类型',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称',
    `token`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'Token',
    `model`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '型号',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图标',
    `data_areas`     json                                                          NULL COMMENT '数据域',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '设备上面的模块，和设备是多对一关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_module
-- ----------------------------
INSERT INTO `ez_module`
VALUES (1, 1, NULL, 'shot',
        'Em+J++MwoFg4My8+xApjgPce+aoVPqPBOc4s9jSjulDwboqiDoPM0ggYRlBzo/YBqzmdxm1Vm3apJueWoH8WjSXH3DDma4OtSaIJrvYugJucw1soyalSUB3KTW4iN8xI5vgwz2K3M5jh1VV74UC1ym6BJ72LNLGkPb0rZNAGDcs=',
        NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[
    {
      \"type\": 1,
      \"field\": \"cmd\",
      \"description\": \"按钮事件\",
      \"defaultValue\": \"0\"
    }
  ]', NULL, 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_module`
VALUES (2, 2, NULL, 'shot',
        'kMZo6VIbjsnCsGJm1H0+iAdkhq2GyRwqzmNCuvIvNObR/3A6lZLfv6POqETP72Seyne6dtF+OjbTqIwxpH7wdGlbTRqJqimWWWZiaq8Oj27XrbL0/mI/R1/z8UvMd7NiwelpTfKqX4KVUV7Ju7Uk0OgSTrrbEhgD4P6mnpmqC0A=',
        NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[
    {
      \"type\": 1,
      \"field\": \"cmd\",
      \"description\": \"按钮事件\",
      \"defaultValue\": \"0\"
    }
  ]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module`
VALUES (3, 2, NULL, 'swtich',
        'Ju8mZ5CPSbviEvY9qE504yHNGfIwjOddHurws3FtHDluEd2OOYz8MqAorhv/cyuvyzDfNoaJ/fTXbIS/aXDR/suQ5/kyujDM0d8gkVpBLLwk1maIUsfjNBiYfERhEdt3zIOx1/7SC9FJrSAe7qSzKV54b39aW8/sK08qlmQvglg=',
        NULL, 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', '[
    {
      \"type\": 3,
      \"field\": \"state\",
      \"description\": \"开关状态\",
      \"defaultValue\": \"false\"
    }
  ]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module`
VALUES (4, 2, NULL, '数据急',
        'esYXNTcGaTlOmrY42ax4jTl0bbPCywm34m7fHqxPDShlHOd46fENTBBK5JFlMUMyoNIz3NeGXgjfcmOe9C6i/Xo44OE6lA8cffN2vg34UbHXarjhsdbaCxoQILF4vbkKApx7F/Ts/22uju3idUbrY487yIp6Ej0vh1c687SK/B0=',
        NULL, NULL, '[
    {
      \"type\": 1,
      \"field\": \"数值\",
      \"description\": \"1\",
      \"defaultValue\": \"0\"
    },
    {
      \"type\": 2,
      \"field\": \"备注\",
      \"description\": \"2\",
      \"defaultValue\": \"default\"
    }
  ]', NULL, 0, 0, '2020-04-05 14:35:01');
INSERT INTO `ez_module`
VALUES (5, 3, NULL, 'shot',
        'fO8bdpQTWXiQHTS+xjtgA1P25SiKnsT49l3mu1TodEdCNfw8bgiLmkHrJ3W5zE7+Zseyc4IdTud9CkvFuXGGVccOo5docMsMST01nbvgOewofgWrLxVnvQ94e0LGpoe1uYEUy+zexopEWwgNTN+BjYwbTvxq+QhEvJzeWWTGqQQ=',
        NULL, 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[
    {
      \"type\": 1,
      \"field\": \"cmd\",
      \"description\": \"按钮事件\",
      \"defaultValue\": \"0\"
    }
  ]', NULL, 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_module`
VALUES (6, 3, NULL, 'swtich',
        'dAPBhhvI1oHDsJ8xypjM/EIyT8SlN1TdZRmrNdmAyERabHfb6VVuWWYewlSay24/rwnM21hbP9uTCKJ6MWA3oor18fhqDONPVyx7Z/VOQSwdz8lf91GGkEziZ0cEn3QDxyeAAQMfyM5Rovp7+Dl9XmEMQ5i0icxHPkPEV4lHGIs=',
        NULL, 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', '[
    {
      \"type\": 3,
      \"field\": \"state\",
      \"description\": \"开关状态\",
      \"defaultValue\": \"false\"
    }
  ]', NULL, 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_module` VALUES (7, 3, NULL, '数据急', 'kt0fxjuBvfMpylSJsJR7jzVFDYUcC4sPNNUVlLnYT1D1lE2w7Ln+HB/m3tBRaSzs0/AQIkh0VCZqeGob9H5xCjBrAI3xgonh6k/PX+XWxJU2ZfwHKMTJoFe2kk9SqZVXuBSxK5KcxWnFBXhBUXFunY28QTq0NUcY1w6gLUKYEnM=', NULL, NULL, '[{\"type\": 1, \"field\": \"数值\", \"description\": \"1\", \"defaultValue\": \"0\"}, {\"type\": 2, \"field\": \"备注\", \"description\": \"2\", \"defaultValue\": \"default\"}]', NULL, 0, 0, '2020-04-05 14:42:21');

-- ----------------------------
-- Table structure for ez_module_template
-- ----------------------------
DROP TABLE IF EXISTS `ez_module_template`;
CREATE TABLE `ez_module_template`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `product_id`     int(11)                                                       NOT NULL COMMENT '产品ID',
    `type`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '模块类型',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图标',
    `data_areas`     json                                                          NULL COMMENT '数据域',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '产品上面的模块模板'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_module_template
-- ----------------------------
INSERT INTO `ez_module_template`
VALUES (1, 1, 'BUTTON', 'shot', 'https://s2.ax1x.com/2020/03/03/3fzUMT.png', '[
  {
    \"type\": 1,
    \"field\": \"cmd\",
    \"description\": \"按钮事件\",
    \"defaultValue\": \"0\"
  }
]', '开炮啊', 0, 0, '2020-03-24 21:30:46');
INSERT INTO `ez_module_template`
VALUES (2, 1, 'SWITCH', 'swtich', 'https://s2.ax1x.com/2020/03/03/3fz0Z4.th.png', '[
  {
    \"type\": 3,
    \"field\": \"state\",
    \"description\": \"开关状态\",
    \"defaultValue\": \"false\"
  }
]', '开关', 0, 0, '2020-03-31 22:52:26');
INSERT INTO `ez_module_template`
VALUES (3, 1, 'DATA_ENTITY', '数据急', NULL, '[
  {
    \"type\": 1,
    \"field\": \"数值\",
    \"description\": \"1\",
    \"defaultValue\": \"0\"
  },
  {
    \"type\": 2,
    \"field\": \"备注\",
    \"description\": \"2\",
    \"defaultValue\": \"default\"
  }
]', '数据啊', 0, 0, '2020-04-05 14:28:49');

-- ----------------------------
-- Table structure for ez_module_type_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_module_type_config`;
CREATE TABLE `ez_module_type_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `type`           int(11)                                                       NULL     DEFAULT 0 COMMENT '类型，1：控件，2：视图',
    `label`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标签',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图标',
    `name`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '名称',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '模块类型配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_module_type_config
-- ----------------------------
INSERT INTO `ez_module_type_config`
VALUES (1, 0, '自定义类型', 'https://s1.ax1x.com/2020/03/19/8yUsN8.png', 'CUSTOMIZE', '自定义类型', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config`
VALUES (2, 1, '按钮', 'https://s1.ax1x.com/2020/03/19/8yUajA.png', 'BUTTON', '按钮', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config`
VALUES (3, 1, '按钮组', 'https://s1.ax1x.com/2020/03/19/8yUUcd.png', 'BUTTON_GROUP', '按钮组', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config`
VALUES (4, 1, '开关', 'https://s1.ax1x.com/2020/03/19/8yUwnI.png', 'SWITCH', '开关', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config`
VALUES (5, 1, '开关组', 'https://s1.ax1x.com/2020/03/19/8yUvHx.png', 'SWITCH_GROUP', '开关组', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config`
VALUES (6, 1, '进度条', 'https://s1.ax1x.com/2020/03/19/8yU0Bt.png', 'PROGRESS_BAR', '进度条', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config` VALUES (7, 2, '数据体', 'https://s1.ax1x.com/2020/03/19/8yUrAf.png', 'DATA_ENTITY', '数据体', 0, 0, '2020-03-17 22:10:08');
INSERT INTO `ez_module_type_config` VALUES (8, 3, '视频流', 'https://s1.ax1x.com/2020/03/19/8yUN1H.png', 'STREAM', '视频流', 0, 0, '2020-03-17 22:10:08');

-- ----------------------------
-- Table structure for ez_mqtt_topic
-- ----------------------------
DROP TABLE IF EXISTS `ez_mqtt_topic`;
CREATE TABLE `ez_mqtt_topic`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `device_id`      int(11)                                                       NULL     DEFAULT NULL COMMENT '设备',
    `client_id`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'MQTT客户端ID',
    `allow`          int(11)                                                       NULL     DEFAULT 1 COMMENT '是否允许连接: 0=拒绝1=允许',
    `type`           tinyint(4)                                                    NULL     DEFAULT 0 COMMENT '类型 1：S2C；2：C2S；3：STATUS；4：GROUP',
    `access`         int(11)                                                       NULL     DEFAULT 1 COMMENT '行为类型: 1=订阅2=发布3=订阅+发布',
    `ip`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'IP',
    `username`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'MQTT用户名',
    `topic`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '路由',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL                                  NULL     DEFAULT NULL,
    `create_time`    datetime(0)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'MQTT的TOPIC记录'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_mqtt_topic
-- ----------------------------
INSERT INTO `ez_mqtt_topic`
VALUES (1, 1, '4d45d94142276ad38364049c56d8ed43', 1, 1, 2, '0.0.0.0', '265447ee58775710646ea18d1fe2469e',
        'mqtt/out/4d45d94142276ad38364049c56d8ed43/s2c', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic`
VALUES (2, 1, '4d45d94142276ad38364049c56d8ed43', 1, 2, 1, '0.0.0.0', '265447ee58775710646ea18d1fe2469e',
        'mqtt/in/4d45d94142276ad38364049c56d8ed43/c2s', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic`
VALUES (3, 1, '4d45d94142276ad38364049c56d8ed43', 1, 3, 1, '0.0.0.0', '265447ee58775710646ea18d1fe2469e',
        'mqtt/in/4d45d94142276ad38364049c56d8ed43/status', 0, 0, '2020-03-24 21:31:24');
INSERT INTO `ez_mqtt_topic`
VALUES (4, 3, 'cfb54735067705280df30e6028d16aa1', 1, 1, 2, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f',
        'mqtt/out/cfb54735067705280df30e6028d16aa1/s2c', 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_mqtt_topic`
VALUES (5, 3, 'cfb54735067705280df30e6028d16aa1', 1, 2, 1, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f',
        'mqtt/in/cfb54735067705280df30e6028d16aa1/c2s', 0, 0, '2020-04-05 14:42:21');
INSERT INTO `ez_mqtt_topic`
VALUES (6, 3, 'cfb54735067705280df30e6028d16aa1', 1, 3, 1, '0.0.0.0', 'e29936f9443fdd17d520ed731cfd564f',
        'mqtt/in/cfb54735067705280df30e6028d16aa1/status', 0, 0, '2020-04-05 14:42:21');

-- ----------------------------
-- Table structure for ez_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_permission`;
CREATE TABLE `ez_permission`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `sort`           int(10) UNSIGNED                                              NULL     DEFAULT 0 COMMENT '排序值',
    `type`           int(11)                                                       NOT NULL COMMENT '类型：1目录，2动作',
    `visible`        tinyint(3) UNSIGNED                                           NULL     DEFAULT 0 COMMENT '是否显示在UI上',
    `label`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'UI显示的标签',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '名称（英文）',
    `resource`       varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '请求路径',
    `parent`         int(11)                                                       NOT NULL COMMENT '父资源，如果是0则为顶级',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0,
    `create_time`    datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户权限'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_permission
-- ----------------------------
INSERT INTO `ez_permission`
VALUES (1, 0, 1, 0, '主页', 'HOME', '/home', 0, '主页', 0, 0, '2020-03-06 10:21:06');
INSERT INTO `ez_permission`
VALUES (2, 0, 1, 0, '个人中心', 'PERSONAL_INFO', '/personalInfo', 0, '个人中心', 0, 0, '2020-03-06 10:21:06');
INSERT INTO `ez_permission`
VALUES (3, 0, 1, 0, '项目管理', 'PROJECT_MANAGER', '/projects', 0, '项目管理', 0, 0, '2020-03-06 10:21:06');

-- ----------------------------
-- Table structure for ez_product
-- ----------------------------
DROP TABLE IF EXISTS `ez_product`;
CREATE TABLE `ez_product`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `project_id`     int(11)                                                       NOT NULL COMMENT '项目ID',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '产品名称',
    `logo`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品logo',
    `type`           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '类型',
    `state`          int(11)                                                       NULL     DEFAULT NULL COMMENT '当0设备的时候,状态为:设计中;当已经有设备以后,状态为:生产中,此时不可更改关键数据.',
    `layout`         json                                                          NULL COMMENT '布局描述',
    `protocol`       int(11)                                                       NULL     DEFAULT NULL COMMENT '协议类型',
    `parameters`     json                                                          NULL COMMENT '参数',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述文字',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL                                  NOT NULL COMMENT '是否删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '产品（设备的抽象模板）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_product
-- ----------------------------
INSERT INTO `ez_product` VALUES (1, 1, '炮管', 'icon9', '0', NULL, NULL, 1, '[{\"type\": 1, \"field\": \"name\", \"description\": \"名字\", \"defaultValue\": \"default\"}]', '害怕', 0, 0, '2020-03-24 21:29:26');

-- ----------------------------
-- Table structure for ez_project
-- ----------------------------
DROP TABLE IF EXISTS `ez_project`;
CREATE TABLE `ez_project`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT,
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `logo`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `type`           int(11)                                                       NOT NULL COMMENT '类型',
    `state`          int(11)                                                       NULL     DEFAULT NULL COMMENT '状态',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `location`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0,
    `create_time`    datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_project
-- ----------------------------
INSERT INTO `ez_project` VALUES (1, '激光炮', 'https://s2.ax1x.com/2020/03/03/3fz6Rx.th.png', 1, 0, '曾经的激光跑', NULL, 0, 0, '2020-03-24 21:27:43');

-- ----------------------------
-- Table structure for ez_relation_device_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_device_module`;
CREATE TABLE `ez_relation_device_module`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `device_id`      int(11)                      NOT NULL COMMENT '设备',
    `module_id`      int(11)                      NOT NULL COMMENT ' 模块',
    `record_version` int(11)                      NOT NULL DEFAULT 0,
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_relation_feature_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_feature_module`;
CREATE TABLE `ez_relation_feature_module`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `feature_id`     int(11)                      NOT NULL COMMENT '功能',
    `module_id`      int(11)                      NOT NULL COMMENT ' 模块',
    `record_version` int(11)                      NOT NULL DEFAULT 0,
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_relation_module_visual_style
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_module_visual_style`;
CREATE TABLE `ez_relation_module_visual_style`
(
    `id`              int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `module_id`       int(11)                                                       NOT NULL COMMENT '模块ID',
    `visual_style_id` int(11)                                                       NOT NULL COMMENT '视觉效果ID',
    `fields`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '需要展示的数据字段',
    `record_version`  int(11)                                                       NOT NULL DEFAULT 0,
    `x`               tinyint(1) UNSIGNED ZEROFILL                                  NOT NULL,
    `create_time`     datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '模块和可视化视觉展示效果关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_relation_product_module
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_product_module`;
CREATE TABLE `ez_relation_product_module`
(
    `id`             int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `product_id`     int(11)          NOT NULL COMMENT '设备',
    `module_id`      int(11)          NOT NULL COMMENT ' 模块',
    `record_version` int(11)          NULL DEFAULT NULL,
    `x`              tinyint(1)       NULL DEFAULT NULL,
    `create_time`    datetime(0)      NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_relation_product_module
-- ----------------------------
INSERT INTO `ez_relation_product_module`
VALUES (2, 4, 3, 1, 0, '2020-02-26 10:49:22');

-- ----------------------------
-- Table structure for ez_relation_product_tag
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_product_tag`;
CREATE TABLE `ez_relation_product_tag`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `tag_id`         int(11)                      NOT NULL,
    `product_id`     int(11)                      NOT NULL,
    `record_version` int(11)                      NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'Tag 产品关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_relation_product_tag
-- ----------------------------
INSERT INTO `ez_relation_product_tag`
VALUES (4, 4, 4, 0, 0, '2020-02-23 22:21:29');
INSERT INTO `ez_relation_product_tag`
VALUES (5, 5, 5, 0, 0, '2020-03-15 17:23:48');
INSERT INTO `ez_relation_product_tag`
VALUES (6, 6, 5, 0, 0, '2020-03-15 17:23:48');
INSERT INTO `ez_relation_product_tag`
VALUES (7, 7, 1, 0, 0, '2020-03-24 21:29:27');

-- ----------------------------
-- Table structure for ez_relation_protocol_type_module_type
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_protocol_type_module_type`;
CREATE TABLE `ez_relation_protocol_type_module_type`
(
    `id`               int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `protocol_type_id` int(11)                      NOT NULL COMMENT '协议类型',
    `module_type_id`   int(11)                      NOT NULL COMMENT '模块类型',
    `record_version`   int(11)                      NOT NULL DEFAULT 0,
    `x`                tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`      datetime(0)                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'XXX协议支持的XXX模块类型'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_relation_protocol_type_module_type
-- ----------------------------
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (1, 1, 1, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (2, 1, 2, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (3, 1, 3, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (4, 1, 4, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (5, 1, 5, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type`
VALUES (6, 1, 6, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (7, 1, 7, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (8, 1, 8, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (9, 1, 9, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (10, 2, 1, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (11, 2, 7, 0, 0, '2020-03-19 22:57:45');
INSERT INTO `ez_relation_protocol_type_module_type` VALUES (12, 2, 9, 0, 0, '2020-03-19 22:57:45');

-- ----------------------------
-- Table structure for ez_relation_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_role_permission`;
CREATE TABLE `ez_relation_role_permission`
(
    `id`               int(10) UNSIGNED    NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `role_id`          int(11)             NOT NULL,
    `permission_id`    int(11)             NOT NULL,
    `permission_value` int(11)             NULL     DEFAULT NULL COMMENT '权限值',
    `record_version`   int(11)             NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`                tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
    `create_time`      datetime(0)         NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色和权限关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_relation_role_permission
-- ----------------------------
INSERT INTO `ez_relation_role_permission`
VALUES (1, 1, 1, 1, 0, 0, '2020-03-06 00:05:37');
INSERT INTO `ez_relation_role_permission`
VALUES (2, 1, 2, 1, 0, 0, '2020-03-06 00:05:37');

-- ----------------------------
-- Table structure for ez_relation_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_permission`;
CREATE TABLE `ez_relation_user_permission`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `user_id`        int(11)                      NOT NULL COMMENT '用户ID',
    `permission_id`  int(11)                      NOT NULL COMMENT '资源路径ID',
    `record_version` int(11)                      NOT NULL DEFAULT 0,
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户和资源路径关系表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_relation_user_project
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_project`;
CREATE TABLE `ez_relation_user_project`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `user_id`        int(11)                      NOT NULL COMMENT '用户ID',
    `project_id`     int(11)                      NOT NULL COMMENT '项目ID',
    `record_version` int(11)                      NOT NULL DEFAULT 0,
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户和项目关联表，用来给用户授权具体项目'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_relation_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_role`;
CREATE TABLE `ez_relation_user_role`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_id`        int(11)                      NOT NULL,
    `role_id`        int(11)                      NOT NULL,
    `record_version` int(11)                      NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户-角色关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_relation_user_role
-- ----------------------------
INSERT INTO `ez_relation_user_role`
VALUES (1, 1, 1, 0, 0, '2020-03-06 00:10:59');
INSERT INTO `ez_relation_user_role`
VALUES (2, 1, 2, 0, 0, '2020-03-06 00:10:59');

-- ----------------------------
-- Table structure for ez_relation_user_subuser
-- ----------------------------
DROP TABLE IF EXISTS `ez_relation_user_subuser`;
CREATE TABLE `ez_relation_user_subuser`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `user_id`        int(11)                      NOT NULL,
    `sub_user_id`    int(11)                      NOT NULL,
    `record_version` int(11)                      NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户-子用户关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_resource_authorize
-- ----------------------------
DROP TABLE IF EXISTS `ez_resource_authorize`;
CREATE TABLE `ez_resource_authorize`
(
    `id`              int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `master_id`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作用者',
    `master_table`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '作用者表名',
    `slaver_id`       int(11)                                                       NOT NULL COMMENT '被作用的对象',
    `slaver_table`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '被作用者的表名',
    `authorize_value` int(11)                                                       NOT NULL COMMENT '授权值',
    `x`               tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '删除',
    `record_version`  int(10) UNSIGNED                                              NOT NULL DEFAULT 0 COMMENT '版本',
    `create_time`     datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '资源授权表,记录所有存在权限关系的双方'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_role
-- ----------------------------
DROP TABLE IF EXISTS `ez_role`;
CREATE TABLE `ez_role`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `label`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `parent`         int(11)                                                       NOT NULL,
    `description`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0,
    `create_time`    datetime(0)                                                   NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户角色'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_role
-- ----------------------------
INSERT INTO `ez_role`
VALUES (1, '超级管理员', 'SUPER_ADMIN', 0, '超级管理员', 0, 0, '2020-03-05 23:59:38');
INSERT INTO `ez_role`
VALUES (2, '普通用户', 'PERSONAL', 0, '普通用户', 0, 0, '2020-03-05 23:59:38');

-- ----------------------------
-- Table structure for ez_schedule_info
-- ----------------------------
DROP TABLE IF EXISTS `ez_schedule_info`;
CREATE TABLE `ez_schedule_info`
(
    `id`                      int(10) UNSIGNED                                        NOT NULL AUTO_INCREMENT,
    `link_id`                 int(11)                                                 NULL DEFAULT NULL COMMENT '作用方ID',
    `task_description`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
    `task_name`               varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `task_group`              varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务组名称',
    `trigger_name`            varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发器名称',
    `trigger_group`           varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '触发器组',
    `trigger_cron_expression` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表达式',
    `execute_class_name`      varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标执行类类名',
    `execute_method_name`     varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行类的具体执行方法',
    `target_table`            text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '数据目标所在表集合\",\"分割用于统计',
    `is_start`                tinyint(1)                                              NULL DEFAULT NULL COMMENT '是否启动',
    `status`                  int(11)                                                 NULL DEFAULT 1 COMMENT '0:停止,1:暂停:2:启动',
    `updated_id`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人id',
    `schedule_data`           json                                                    NULL COMMENT '指令内容',
    `updated_time`            datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    `record_version`          int(11)                                                 NULL DEFAULT NULL,
    `x`                       tinyint(4)                                              NULL DEFAULT NULL,
    `create_time`             datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '实际的定时任务'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_schedule_template
-- ----------------------------
DROP TABLE IF EXISTS `ez_schedule_template`;
CREATE TABLE `ez_schedule_template`
(
    `id`                      int(10) UNSIGNED                                        NOT NULL AUTO_INCREMENT,
    `product_id`              int(11)                                                 NULL DEFAULT NULL COMMENT '定时任务作用的产品',
    `task_name`               varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `task_description`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
    `trigger_cron_expression` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'CronTab表达式',
    `schedule_data`           json                                                    NULL COMMENT '指令内容',
    `updated_time`            datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    `record_version`          int(11)                                                 NULL DEFAULT NULL,
    `x`                       tinyint(4)                                              NULL DEFAULT NULL,
    `create_time`             datetime(0)                                             NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '定时任务模板'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_shared_device
-- ----------------------------
DROP TABLE IF EXISTS `ez_shared_device`;
CREATE TABLE `ez_shared_device`
(
    `id`             int(10) UNSIGNED             NOT NULL AUTO_INCREMENT COMMENT 'pk',
    `user_id`        int(11)                      NOT NULL COMMENT '用户ID',
    `device_id`      int(11)                      NOT NULL COMMENT '设备ID',
    `record_version` int(11)                      NOT NULL DEFAULT 0,
    `x`              tinyint(1) UNSIGNED ZEROFILL NOT NULL,
    `create_time`    datetime(0)                  NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '共享设备记录表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_stream_integration
-- ----------------------------
DROP TABLE IF EXISTS `ez_stream_integration`;
CREATE TABLE `ez_stream_integration`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `module_id`      int(10) UNSIGNED                                              NOT NULL COMMENT '设备ID',
    `token`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '令牌',
    `secret`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '密钥',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '流媒体推送密钥'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_tag
-- ----------------------------
DROP TABLE IF EXISTS `ez_tag`;
CREATE TABLE `ez_tag`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Tagname',
    `link_id`        int(11)                                                       NOT NULL COMMENT '作用的对象的ID',
    `x`              tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '删除',
    `record_version` int(10) UNSIGNED                                              NOT NULL DEFAULT 0 COMMENT '版本',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '设备Tag表，用来给设备绑定多个tag'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_tag
-- ----------------------------
INSERT INTO `ez_tag`
VALUES (4, 'door', 4, 0, 0, '2020-02-23 22:21:29');
INSERT INTO `ez_tag`
VALUES (5, 'NB', 5, 0, 0, '2020-03-15 17:23:48');
INSERT INTO `ez_tag`
VALUES (6, '贵', 5, 0, 0, '2020-03-15 17:23:48');
INSERT INTO `ez_tag`
VALUES (7, '高科技', 1, 0, 0, '2020-03-24 21:29:26');

-- ----------------------------
-- Table structure for ez_user
-- ----------------------------
DROP TABLE IF EXISTS `ez_user`;
CREATE TABLE `ez_user`
(
    `id`              int(10) UNSIGNED                                             NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `username`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `avatar`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '头像地址,是一个数字，标记一个PNG图片',
    `phone`           varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
    `email`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `real_name`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '实名',
    `nick_name`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '昵称',
    `user_type`       int(11)                                                      NULL     DEFAULT NULL COMMENT '用户类型：普通用户【1】，企业用户【2】，VIP用户【3】',
    `status`          int(11)                                                      NULL     DEFAULT NULL COMMENT '账户状态：正常【1】，冻结【2】，过期【3】',
    `user_profile_id` int(11)                                                      NOT NULL COMMENT '扩展信息',
    `last_login_time` datetime(0)                                                  NULL     DEFAULT NULL COMMENT '上次登陆时间',
    `last_login_ip`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '上次登录IP',
    `x`               tinyint(3) UNSIGNED                                          NOT NULL DEFAULT 0 COMMENT '是否删除',
    `record_version`  int(10) UNSIGNED                                             NOT NULL COMMENT '记录版本',
    `create_time`     datetime(0)                                                  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_user
-- ----------------------------
INSERT INTO `ez_user`
VALUES (1, 'ezlinker', '5F4DCC3B5AA765D61D8327DEB882CF99', '1', '18059150204', 'ezlinker@ez.com', 'EZLinker官方',
        'EZLINKER-Official', 1, 1, 0, '2020-04-07 09:31:08', '192.168.56.1', 0, 211, '2019-11-11 22:23:02');
INSERT INTO `ez_user`
VALUES (2, 'wwhai', '5F4DCC3B5AA765D61D8327DEB882CF99', '2', '18059150205', 'wwhai@qq.com', '程序猿老王', 'NeighborOldWang',
        1, 1, 0, NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user`
VALUES (3, 'liuchun', '5F4DCC3B5AA765D61D8327DEB882CF99', '3', '18059150206', 'liuchun@qq.com', '刘总', 'CEO.Liu', 1, 1,
        0, NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user`
VALUES (4, 'liuzy', '5F4DCC3B5AA765D61D8327DEB882CF99', '4', '18059150207', 'lzy@qq.com', '小刘', 'CTO.Liu', 1, 1, 0,
        NULL, NULL, 0, 2, '2019-11-11 22:23:02');
INSERT INTO `ez_user`
VALUES (6, 'username', '25d55ad283aa400af464c76d713c07ad', '1', '15122225555', '123@qq.com', NULL, NULL, NULL, 2, 7,
        NULL, NULL, 0, 0, '2019-12-19 21:44:53');
INSERT INTO `ez_user`
VALUES (7, 'LiuZeYu', '25d55ad283aa400af464c76d713c07ad', '1', '18659199666', 'LiuZeYu@qq.com', NULL, NULL, NULL, 2, 8,
        NULL, NULL, 0, 0, '2019-12-19 22:26:22');

-- ----------------------------
-- Table structure for ez_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `ez_user_profile`;
CREATE TABLE `ez_user_profile`
(
    `id`              int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `region`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `province`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `city`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `area`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `address`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `domain`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `personal_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `certification`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `qq`              varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `wechat`          varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `x`               tinyint(3) UNSIGNED                                           NULL     DEFAULT 0,
    `record_version`  int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `create_time`     datetime(0)                                                   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户的详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ez_user_profile
-- ----------------------------
INSERT INTO `ez_user_profile` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:28:56');
INSERT INTO `ez_user_profile` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:29:57');
INSERT INTO `ez_user_profile` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:31:01');
INSERT INTO `ez_user_profile` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:34:38');
INSERT INTO `ez_user_profile` VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:42:23');
INSERT INTO `ez_user_profile` VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:43:52');
INSERT INTO `ez_user_profile` VALUES (7, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 21:44:53');
INSERT INTO `ez_user_profile` VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, '2019-12-19 22:26:22');

-- ----------------------------
-- Table structure for ez_visual_style
-- ----------------------------
DROP TABLE IF EXISTS `ez_visual_style`;
CREATE TABLE `ez_visual_style`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '可视化视觉名称',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图标',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '可视化视觉的描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '可视化视觉展示效果'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_visual_style_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_visual_style_config`;
CREATE TABLE `ez_visual_style_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '可视化视觉名称',
    `label`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'UI文本',
    `icon`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图标',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '可视化视觉的描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '可视化视觉展示效果'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ez_wx_app_config
-- ----------------------------
DROP TABLE IF EXISTS `ez_wx_app_config`;
CREATE TABLE `ez_wx_app_config`
(
    `id`             int(10) UNSIGNED                                              NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `name`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT 'EZLINKER授权密钥名称',
    `token`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT 'EZLINKER授权密钥',
    `description`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `record_version` int(11)                                                       NOT NULL DEFAULT 0 COMMENT '记录版本',
    `x`              tinyint(3) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '删除',
    `create_time`    datetime(0)                                                   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '微信小程序密钥配置表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `BLOB_DATA`     blob                                                          NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `ENTRY_ID`          varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `TRIGGER_NAME`      varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_GROUP`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `INSTANCE_NAME`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `FIRED_TIME`        bigint(20)                                                    NOT NULL,
    `SCHED_TIME`        bigint(20)                                                    NOT NULL,
    `PRIORITY`          int(11)                                                       NOT NULL,
    `STATE`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `JOB_NAME`          varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `JOB_GROUP`         varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `IS_NONCONCURRENT`  varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
    INDEX `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
    INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
    INDEX `IDX_QRTZ_FT_J_G` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_FT_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`
(
    `SCHED_NAME`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `INSTANCE_NAME`     varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `LAST_CHECKIN_TIME` bigint(20)                                                    NOT NULL,
    `CHECKIN_INTERVAL`  bigint(20)                                                    NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`
(
    `SCHED_NAME`      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_NAME`    varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_GROUP`   varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `REPEAT_COUNT`    bigint(20)                                                    NOT NULL,
    `REPEAT_INTERVAL` bigint(20)                                                    NOT NULL,
    `TIMES_TRIGGERED` bigint(20)                                                    NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`
(
    `SCHED_NAME`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `STR_PROP_1`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `STR_PROP_2`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `STR_PROP_3`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `INT_PROP_1`    int(11)                                                       NULL DEFAULT NULL,
    `INT_PROP_2`    int(11)                                                       NULL DEFAULT NULL,
    `LONG_PROP_1`   bigint(20)                                                    NULL DEFAULT NULL,
    `LONG_PROP_2`   bigint(20)                                                    NULL DEFAULT NULL,
    `DEC_PROP_1`    decimal(13, 4)                                                NULL DEFAULT NULL,
    `DEC_PROP_2`    decimal(13, 4)                                                NULL DEFAULT NULL,
    `BOOL_PROP_1`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL,
    `BOOL_PROP_2`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`
(
    `SCHED_NAME`     varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_NAME`   varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `TRIGGER_GROUP`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `JOB_NAME`       varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `JOB_GROUP`      varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `DESCRIPTION`    varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint(20)                                                    NULL DEFAULT NULL,
    `PREV_FIRE_TIME` bigint(20)                                                    NULL DEFAULT NULL,
    `PRIORITY`       int(11)                                                       NULL DEFAULT NULL,
    `TRIGGER_STATE`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `TRIGGER_TYPE`   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL,
    `START_TIME`     bigint(20)                                                    NOT NULL,
    `END_TIME`       bigint(20)                                                    NULL DEFAULT NULL,
    `CALENDAR_NAME`  varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `MISFIRE_INSTR`  smallint(6)                                                   NULL DEFAULT NULL,
    `JOB_DATA`       blob                                                          NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_J` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_JG` (`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_C` (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
    INDEX `IDX_QRTZ_T_G` (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
    INDEX `IDX_QRTZ_T_STATE` (`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
    INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`,
                                           `TRIGGER_STATE`) USING BTREE,
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for ez_role_permission_view
-- ----------------------------
DROP VIEW IF EXISTS `ez_role_permission_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `easylinker`@`%` SQL SECURITY INVOKER VIEW `ez_role_permission_view` AS
select `d`.`id`          AS `id`,
       `c`.`role_id`     AS `role_id`,
       `d`.`label`       AS `label`,
       `d`.`name`        AS `name`,
       `d`.`resource`    AS `resource`,
       `d`.`type`        AS `type`,
       `d`.`methods`     AS `methods`,
       `d`.`parent`      AS `parent`,
       `d`.`description` AS `description`
from (((select `a`.`id`             AS `id`,
               `a`.`role_id`        AS `role_id`,
               `a`.`permission_id`  AS `permission_id`,
               `a`.`record_version` AS `record_version`,
               `a`.`x`              AS `x`,
               `a`.`create_time`    AS `create_time`
        from (`ezlinker`.`ez_role_permission` `a`
                 left join `ezlinker`.`ez_role` `b` on ((`a`.`role_id` = `b`.`id`))))) `c`
         left join `ezlinker`.`ez_permission` `d` on ((`d`.`id` = `c`.`permission_id`)));

-- ----------------------------
-- View structure for ez_user_info_view
-- ----------------------------
DROP VIEW IF EXISTS `ez_user_info_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `easylinker`@`%` SQL SECURITY INVOKER VIEW `ez_user_info_view` AS
select distinct `a`.`id`              AS `id`,
                `a`.`username`        AS `username`,
                `a`.`password`        AS `password`,
                `a`.`avatar`          AS `avatar`,
                `a`.`phone`           AS `phone`,
                `a`.`email`           AS `email`,
                `a`.`real_name`       AS `real_name`,
                `a`.`nick_name`       AS `nick_name`,
                `a`.`user_type`       AS `user_type`,
                `a`.`status`          AS `status`,
                `a`.`user_profile_id` AS `user_profile_id`,
                `a`.`last_login_time` AS `last_login_time`,
                `a`.`last_login_ip`   AS `last_login_ip`,
                `b`.`region`          AS `region`,
                `b`.`province`        AS `province`,
                `b`.`area`            AS `area`,
                `b`.`city`            AS `city`,
                `b`.`address`         AS `address`,
                `b`.`domain`          AS `domain`,
                `b`.`certification`   AS `certification`,
                `b`.`personal_remark` AS `personal_remark`,
                `b`.`qq`              AS `qq`,
                `b`.`wechat`          AS `wechat`,
                `a`.`x`               AS `x`,
                `a`.`record_version`  AS `record_version`,
                `a`.`create_time`     AS `create_time`
from (`ez_user` `a`
         left join `ez_user_profile` `b` on ((`a`.`user_profile_id` = `b`.`id`)))
order by `a`.`id` desc;

-- ----------------------------
-- View structure for ez_user_permission_view
-- ----------------------------
DROP VIEW IF EXISTS `ez_user_permission_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `easylinker`@`%` SQL SECURITY DEFINER VIEW `ez_user_permission_view` AS
select `f`.`user_id`       AS `user_id`,
       `e`.`role_id`       AS `role_id`,
       `e`.`permission_id` AS `permission_id`,
       `e`.`allow`         AS `allow`,
       `e`.`resource`      AS `resource`,
       `e`.`methods`       AS `methods`
from (((select `c`.`role_id`       AS `role_id`,
               `c`.`permission_id` AS `permission_id`,
               `c`.`allow`         AS `allow`,
               `d`.`resource`      AS `resource`,
               `d`.`methods`       AS `methods`
        from (((select `a`.`permission_id` AS `permission_id`, `a`.`allow` AS `allow`, `a`.`role_id` AS `role_id`
                from (`ezlinker`.`ez_role_permission` `a`
                         left join `ezlinker`.`ez_role` `b` on ((`a`.`role_id` = `b`.`id`))))) `c`
                 left join `ezlinker`.`ez_permission` `d` on ((`d`.`id` = `c`.`permission_id`))))) `e`
         left join `ezlinker`.`ez_user_role` `f` on ((`f`.`role_id` = `e`.`role_id`)));

-- ----------------------------
-- View structure for ez_user_role_permission_view
-- ----------------------------
DROP VIEW IF EXISTS `ez_user_role_permission_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `easylinker`@`%` SQL SECURITY DEFINER VIEW `ez_user_role_permission_view` AS
select `f`.`user_id`       AS `user_id`,
       `e`.`role_id`       AS `role_id`,
       `e`.`permission_id` AS `permission_id`,
       `e`.`allow`         AS `allow`,
       `e`.`resource`      AS `resource`,
       `e`.`methods`       AS `methods`
from (((select `c`.`role_id`       AS `role_id`,
               `c`.`permission_id` AS `permission_id`,
               `c`.`allow`         AS `allow`,
               `d`.`resource`      AS `resource`,
               `d`.`methods`       AS `methods`
        from (((select `a`.`permission_id` AS `permission_id`, `a`.`allow` AS `allow`, `a`.`role_id` AS `role_id`
                from (`ezlinker`.`ez_role_permission` `a`
                         left join `ezlinker`.`ez_role` `b` on ((`a`.`role_id` = `b`.`id`))))) `c`
                 left join `ezlinker`.`ez_permission` `d` on ((`d`.`id` = `c`.`permission_id`))))) `e`
         left join `ezlinker`.`ez_user_role` `f` on ((`f`.`role_id` = `e`.`role_id`)));

-- ----------------------------
-- View structure for ez_user_role_view
-- ----------------------------
DROP VIEW IF EXISTS `ez_user_role_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `easylinker`@`%` SQL SECURITY INVOKER VIEW `ez_user_role_view` AS
select `d`.`id`          AS `id`,
       `c`.`user_id`     AS `user_id`,
       `d`.`label`       AS `label`,
       `d`.`name`        AS `name`,
       `d`.`parent`      AS `parent`,
       `d`.`description` AS `description`
from (((select `a`.`id`             AS `id`,
               `a`.`user_id`        AS `user_id`,
               `a`.`role_id`        AS `role_id`,
               `a`.`record_version` AS `record_version`,
               `a`.`x`              AS `x`,
               `a`.`create_time`    AS `create_time`
        from (`ezlinker`.`ez_user_role` `a`
                 left join `ezlinker`.`ez_user` `b` on ((`a`.`user_id` = `b`.`id`))))) `c`
         left join `ezlinker`.`ez_role` `d` on ((`d`.`id` = `c`.`role_id`)));

SET FOREIGN_KEY_CHECKS = 1;
