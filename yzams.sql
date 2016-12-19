/*
Navicat MySQL Data Transfer

Source Server         : yz_26_2(yzams)
Source Server Version : 50544
Source Host           : 172.16.100.26:3306
Source Database       : yzams

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-12-19 11:44:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `attendance`
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `attendance_id` varchar(64) NOT NULL DEFAULT '',
  `attendance_date` datetime DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `delay_type` varchar(16) DEFAULT NULL,
  `leave_early` tinyint(1) DEFAULT NULL,
  `out_type` varchar(16) DEFAULT NULL,
  `absent_days` double DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `creator_id` varchar(64) DEFAULT NULL,
  `creator_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES ('1232cecb-7ec5-49a4-8077-9d2dc3f9aa7f', '2016-08-22 00:00:00', 'b8449be3-e7d5-4e00-bd79-123fc788cc21', '吕祎菲', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:44:29', null, null, '2016-08-22 09:45:46');
INSERT INTO `attendance` VALUES ('1e7f68dd-fdb3-4839-a7cd-8d1c331d0169', '2016-08-22 00:00:00', 'c4aca1fa-7b9d-4bfe-ae2b-a710a19453a6', '张春男', 'NORMAL', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:42:22', null, null, null);
INSERT INTO `attendance` VALUES ('42aa387e-b053-4169-8af0-ff443dcd1098', '2016-08-22 00:00:00', '2d5375c9-f488-4c0b-8798-66463a437705', '燕戏涛', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:16:39', null, null, null);
INSERT INTO `attendance` VALUES ('42d69a3b-9b3c-44f0-90af-e1c2b7c393e2', '2016-08-23 00:00:00', '6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '郭东东', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:27:28', null, null, null);
INSERT INTO `attendance` VALUES ('430e2639-3e95-4b38-ae43-907412c9fb17', '2016-08-23 00:00:00', '28c86382-167c-44ac-a905-b80eed821c87', '刘佳', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:27:16', null, null, null);
INSERT INTO `attendance` VALUES ('4a5d9af5-2bcb-4173-bc69-b96d8a8af0b6', '2016-08-23 00:00:00', '939782bc-d024-45c7-98c3-fa98d1fcfd5a', '张娥', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:27:32', null, null, null);
INSERT INTO `attendance` VALUES ('52ed0a7a-de97-41d5-bbf8-e5ca75b4186f', '2016-08-23 00:00:00', 'fab289d4-f549-4583-a0b0-33a9d8ea03c4', '陈婷婷', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:27:47', null, null, null);
INSERT INTO `attendance` VALUES ('5b68825e-0b6d-4157-9051-bee1a09b1b06', '2016-08-23 00:00:00', '52a087a9-8824-41cd-9248-fc7896816614', '李津', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:38:09', null, null, null);
INSERT INTO `attendance` VALUES ('6fc90e0a-da9f-4e45-b142-e8785a062b94', '2016-08-23 00:00:00', '9a476763-96a9-44d8-a4c2-9f4a682aa24b', '李佳', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:38:26', null, null, null);
INSERT INTO `attendance` VALUES ('82bfd1b6-381c-4568-8fe5-be8dae6ec9ef', '2016-08-22 00:00:00', '6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '郭东东', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:45:47', null, null, null);
INSERT INTO `attendance` VALUES ('874d13ae-9d4b-4eb1-98d6-13da94d978a1', '2016-08-22 00:00:00', '2df2b000-8217-450f-aa4c-a3a989e86aa9', '周宁', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:16:42', null, null, null);
INSERT INTO `attendance` VALUES ('ce980f35-aa5a-458d-8820-f081ce3b3d4f', '2016-08-23 00:00:00', '1a17fd1c-2bc1-41ff-ad9c-693ae6cbc489', '郑里', 'LIGHT', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-23 09:27:08', null, null, null);
INSERT INTO `attendance` VALUES ('debd7945-c057-454e-bf67-eb419e029160', '2016-08-22 00:00:00', 'fab289d4-f549-4583-a0b0-33a9d8ea03c4', '陈婷婷', 'NORMAL', '0', null, '0', '0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-22 09:44:23', null, null, null);

-- ----------------------------
-- Table structure for `biz_trip`
-- ----------------------------
DROP TABLE IF EXISTS `biz_trip`;
CREATE TABLE `biz_trip` (
  `biz_trip_id` varchar(64) NOT NULL DEFAULT '',
  `applicant_id` varchar(64) DEFAULT NULL,
  `applicant_name` varchar(64) DEFAULT NULL,
  `location` varchar(256) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `morning` tinyint(1) DEFAULT NULL,
  `days` decimal(10,1) DEFAULT NULL,
  `staff_ids` varchar(1024) DEFAULT NULL,
  `audit_state` varchar(16) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  `auditor_id` varchar(64) DEFAULT NULL,
  `auditor_name` varchar(64) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `creator_id` varchar(64) DEFAULT NULL,
  `creator_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`biz_trip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of biz_trip
-- ----------------------------
INSERT INTO `biz_trip` VALUES ('348ed964-6d5f-4ac9-89f5-9866c24002b2', 'b0216c52-f3e3-42fb-b59f-4414860e2c39', '李晓亮', '江西省 南昌市', '2016-08-17 00:00:00', '1', '2.0', 'b0216c52-f3e3-42fb-b59f-4414860e2c39', 'WAIT_FOR_CEO', '开会', null, null, null, '0', null, null, '2016-08-22 12:01:38', null, null, null, '2016-08-18 23:59:59');

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `user_id` varchar(64) NOT NULL,
  `entry_time` date DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `employee_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('04f03d40-afa5-44b8-8b15-781e9e18f81e', '2014-03-19', '刘易哲', '172');
INSERT INTO `employee` VALUES ('0cb0e7e8-f156-4fe8-8219-99de55c1e143', null, '人事行政', '');
INSERT INTO `employee` VALUES ('137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '2010-05-17', '邱东岳', '126');
INSERT INTO `employee` VALUES ('1567f29b-e44f-4849-864e-c108ece088c6', null, '人事行政主管', '');
INSERT INTO `employee` VALUES ('173a1ae0-df22-407d-8909-f7396dc661d8', null, '部门主管', '');
INSERT INTO `employee` VALUES ('1a17fd1c-2bc1-41ff-ad9c-693ae6cbc489', '2011-05-19', '郑里', '140');
INSERT INTO `employee` VALUES ('1cf7d8d9-159b-4df7-8c6f-ff34b2ba33b1', '2015-12-04', '罗永昌', '186');
INSERT INTO `employee` VALUES ('28c86382-167c-44ac-a905-b80eed821c87', '2016-03-30', '刘佳', '201');
INSERT INTO `employee` VALUES ('2d5375c9-f488-4c0b-8798-66463a437705', '2016-03-15', '燕戏涛', '196');
INSERT INTO `employee` VALUES ('2df2b000-8217-450f-aa4c-a3a989e86aa9', '2016-05-09', '周宁', '205');
INSERT INTO `employee` VALUES ('3057fa6f-e5d7-46f4-b33e-aaba4bc3d617', '2011-10-14', '余静', '145');
INSERT INTO `employee` VALUES ('36da438a-440a-4d23-a9b3-b92e9da496b7', '2016-03-28', '刘禹', '199');
INSERT INTO `employee` VALUES ('3e4e88b7-b314-4660-a34a-ac95cf8068f0', '2014-03-10', '詹丹', '170');
INSERT INTO `employee` VALUES ('402cc2d3-b3a0-4508-8419-e18051dd79f0', '2016-04-25', '黄兴旺', '203');
INSERT INTO `employee` VALUES ('45d8b359-06c3-4d0f-844d-8df444623e22', '2015-12-04', '李涛', '187');
INSERT INTO `employee` VALUES ('476d1e28-fd96-41fb-8c2a-e40275e97d4b', '2015-11-01', '连莲', '191');
INSERT INTO `employee` VALUES ('4b06055c-7a1f-4295-b1cd-49fd8fa45088', '2009-06-26', '陈佳楠', '116');
INSERT INTO `employee` VALUES ('5235b043-6d53-4005-83ab-028c9d77fcaa', '2010-06-09', '吴京华', '130');
INSERT INTO `employee` VALUES ('52a087a9-8824-41cd-9248-fc7896816614', '2013-06-17', '李津', '165');
INSERT INTO `employee` VALUES ('534ee8b1-fd8c-4087-93e7-932a81c1b128', '2012-11-08', '蔡灵霞', '160');
INSERT INTO `employee` VALUES ('5b76518f-2c10-4cc8-bf1c-55262a9cb3c1', '2016-05-12', '叶鹏举', '208');
INSERT INTO `employee` VALUES ('5e7fdddb-cf88-46e6-862d-1ffb94e76cea', '2015-11-01', '廉蕊', '190');
INSERT INTO `employee` VALUES ('642e3685-a9d1-4ffb-b216-edcfd6c8ee3b', '2015-05-28', '朱梦超', '180');
INSERT INTO `employee` VALUES ('66097385-d888-45e8-80ce-ab7503e3ac05', '2010-08-10', '解冰', '133');
INSERT INTO `employee` VALUES ('6dfd2afd-82d3-4f1e-9dd1-25c57feb77af', '2016-05-30', '曹会英', '209');
INSERT INTO `employee` VALUES ('6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '2010-11-30', '郭东东', '136');
INSERT INTO `employee` VALUES ('7457be91-b7f9-47d0-8720-bbed3d2a88cb', '2016-07-15', '上官新建', '211');
INSERT INTO `employee` VALUES ('8dbb8937-454c-4afd-aabb-97254287ce15', '2015-12-01', '周帅', '192');
INSERT INTO `employee` VALUES ('929009b2-2ee5-4a31-8392-aadee28443fb', '2016-03-17', '吴诗', '197');
INSERT INTO `employee` VALUES ('939782bc-d024-45c7-98c3-fa98d1fcfd5a', '2012-08-20', '张娥', '158');
INSERT INTO `employee` VALUES ('9a476763-96a9-44d8-a4c2-9f4a682aa24b', '2016-03-30', '李佳', '200');
INSERT INTO `employee` VALUES ('a1d225d5-2704-416d-88ca-def0f50e0b44', '2016-02-29', '姚晓伟', '193');
INSERT INTO `employee` VALUES ('a2cd3780-0169-4c2c-b324-8501e7b57608', '2008-02-04', '黄凯', '101');
INSERT INTO `employee` VALUES ('b0216c52-f3e3-42fb-b59f-4414860e2c39', '2015-04-24', '李晓亮', '177');
INSERT INTO `employee` VALUES ('b04df9f4-7bf3-4004-8212-2041652e581f', '2016-05-09', '史杰', '206');
INSERT INTO `employee` VALUES ('b25674f1-c2d2-49af-bc7d-1c8165525c91', '2008-06-01', '赵胜松', '107');
INSERT INTO `employee` VALUES ('b8449be3-e7d5-4e00-bd79-123fc788cc21', '2010-08-02', '吕祎菲', '132');
INSERT INTO `employee` VALUES ('b955ad4d-ba33-453c-b457-7a25f7c8a57d', '2016-07-14', '张琪', '210');
INSERT INTO `employee` VALUES ('be158a57-0b29-4911-bc31-cc5cb0cbc887', '2009-07-13', '高禄芳', '118');
INSERT INTO `employee` VALUES ('be63974f-f41e-469a-9361-f5273393d93c', null, '彭锐锐', '214');
INSERT INTO `employee` VALUES ('bfe221f2-9a71-45c0-8e29-359c14e3f699', '2010-05-31', '李勤', '128');
INSERT INTO `employee` VALUES ('c4aca1fa-7b9d-4bfe-ae2b-a710a19453a6', '2008-01-13', '张春男', '102');
INSERT INTO `employee` VALUES ('c6e7ce29-0651-4322-aba2-3fa64e8614f7', null, '沈洋', '213');
INSERT INTO `employee` VALUES ('c748ab56-6ac5-48d2-9493-798cf09120f2', '2016-05-10', '陈亚东', '209');
INSERT INTO `employee` VALUES ('ca8a2767-c9d2-4772-9f7b-61d0d1fde0d9', null, '崔佳', '123');
INSERT INTO `employee` VALUES ('cdad0f46-61ac-490a-affd-c82b12900baa', '2016-05-03', '赵洪坤', '204');
INSERT INTO `employee` VALUES ('e2fe79d9-baf1-4da4-a761-b663efff009d', '2014-03-11', '闫丽梅', '171');
INSERT INTO `employee` VALUES ('f32f8975-74b0-4561-a7d7-b03024ba4985', '2016-08-01', '胡琴', '212');
INSERT INTO `employee` VALUES ('fab289d4-f549-4583-a0b0-33a9d8ea03c4', '2016-04-18', '陈婷婷', '202');
INSERT INTO `employee` VALUES ('fbc67ba5-2ed8-4c90-9c25-b787c2482737', '2015-11-01', '冯凯', '189');

-- ----------------------------
-- Table structure for `holiday`
-- ----------------------------
DROP TABLE IF EXISTS `holiday`;
CREATE TABLE `holiday` (
  `holiday_id` varchar(64) NOT NULL DEFAULT '',
  `holiday_date` datetime DEFAULT NULL,
  `holiday_type` varchar(100) DEFAULT NULL,
  `holiday_name` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`holiday_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of holiday
-- ----------------------------
INSERT INTO `holiday` VALUES ('0028da5a-3647-4a82-978d-eda0b3df20af', '2016-10-06 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('07215ffb-45b2-4b1d-bb2a-70a4c753c806', '2016-02-12 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('0f2e8021-70c4-4991-8592-8a2c66bd1298', '2016-10-05 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('15b29c69-e5c6-4b1a-b251-0be28e5b5acb', '2016-10-03 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('1f0ff7f4-b1c9-4fc5-b2b0-089ad9d2f249', '2016-05-01 00:00:00', 'HOLIDAY', '劳动节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('266a07e9-fe31-4e5e-bdad-2c8ca1e06fc4', '2016-06-11 00:00:00', 'HOLIDAY', '端午节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('2762fad6-fb48-4d2c-807c-4bf0acaf7979', '2016-02-10 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('2ef9034d-b310-44ef-aa23-4661f40b1559', '2016-02-11 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('3d22474a-7da2-4e12-bc91-7223245d414c', '2016-05-03 00:00:00', 'WORKDAY', '劳动节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('4a6a1990-e65f-4f5e-b5f6-062072a9129f', '2016-04-03 00:00:00', 'HOLIDAY', '清明节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('4ccd43aa-66f4-440e-ad83-e77d4ea4a3b5', '2016-06-09 00:00:00', 'HOLIDAY', '端午节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('55d559d1-aa3e-49b2-a74f-bc30a2d066b5', '2016-02-06 00:00:00', 'WORKDAY', '春节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('5c1932a8-4051-44c1-b77b-716bff5fb918', '2016-04-30 00:00:00', 'HOLIDAY', '劳动节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('66aa5ddb-3554-4000-9ef8-39401973f672', '2016-09-15 00:00:00', 'HOLIDAY', '中秋节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('67565959-f5ec-43e7-8e96-77faf961e97d', '2016-10-07 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('67c9c4ab-8bd8-42ba-a767-0714e873bca7', '2016-09-16 00:00:00', 'HOLIDAY', '中秋节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('6d7f3afa-2af8-4b79-9f58-491f96b15079', '2016-04-02 00:00:00', 'HOLIDAY', '清明节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('6fde0728-666d-4a8f-8764-2f3b3f37a001', '2016-10-08 00:00:00', 'WORKDAY', '国庆节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('770cab69-07aa-4fad-a88d-e39b614348b9', '2016-06-10 00:00:00', 'HOLIDAY', '端午节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('7d932f3e-1c7e-4cba-9a3c-3a04dda82278', '2016-02-07 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('7f594812-e9e4-415b-8ab5-9385678a5c2a', '2016-10-02 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('84ace814-8d49-452b-bd6d-003fe93370e3', '2016-09-17 00:00:00', 'HOLIDAY', '中秋节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('8b1e443a-1ea9-465a-9060-b1d3375099b6', '2016-10-01 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('8df0f93c-7f7a-4a3c-b39b-2d8f4d43f283', '2016-05-02 00:00:00', 'HOLIDAY', '劳动节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('98f15299-c838-48c5-84e3-d30ad0a3bf7c', '2016-10-09 00:00:00', 'WORKDAY', '国庆节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('a161b385-9316-4224-9c6a-a4b1bb13b71e', '2016-04-04 00:00:00', 'HOLIDAY', '清明节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('aa6c80a1-638f-4ad7-9115-53558d80c9e4', '2016-06-12 00:00:00', 'WORKDAY', '端午节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('b70eb0db-5cfb-49b6-a756-2604b57862f0', '2016-02-09 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('bd78828e-641c-4d19-963f-2dce82d0de22', '2016-10-04 00:00:00', 'HOLIDAY', '国庆节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('ea465438-8f4f-4b07-a6a8-d4c70579e142', '2016-02-14 00:00:00', 'WORKDAY', '春节补班', '0', null, null, null);
INSERT INTO `holiday` VALUES ('eeec8b1c-0b7c-4c80-a2ab-16f05f620876', '2016-02-08 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('ff343494-9020-465f-b2f1-7b62b457651b', '2016-02-13 00:00:00', 'HOLIDAY', '春节', '0', null, null, null);
INSERT INTO `holiday` VALUES ('ffba5a30-4072-458d-ae3d-84f7205d5747', '2016-09-18 00:00:00', 'WORKDAY', '中秋节补班', '0', null, null, null);

-- ----------------------------
-- Table structure for `paid_vacation`
-- ----------------------------
DROP TABLE IF EXISTS `paid_vacation`;
CREATE TABLE `paid_vacation` (
  `pv_id` varchar(64) NOT NULL DEFAULT '',
  `pv_year` int(11) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `official_days` decimal(10,1) DEFAULT NULL,
  `inner_days` decimal(10,1) DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_year_days` double(8,1) DEFAULT NULL,
  PRIMARY KEY (`pv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paid_vacation
-- ----------------------------
INSERT INTO `paid_vacation` VALUES ('23786acd-bf1f-4ac6-b06f-7894954746d7', '2016', '173a1ae0-df22-407d-8909-f7396dc661d8', '0.0', '5.0', 'sys', 'sys', '2016-09-20 17:59:49', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343211', '2016', '1a17fd1c-2bc1-41ff-ad9c-693ae6cbc489', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:20:25', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343212', '2016', '939782bc-d024-45c7-98c3-fa98d1fcfd5a', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:11:00', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343213', '2016', '534ee8b1-fd8c-4087-93e7-932a81c1b128', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:21:03', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343214', '2016', '52a087a9-8824-41cd-9248-fc7896816614', '1.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:32:57', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343215', '2016', 'e2fe79d9-baf1-4da4-a761-b663efff009d', '5.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:34:01', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343218', '2016', 'b0216c52-f3e3-42fb-b59f-4414860e2c39', '0.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:34:45', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343219', '2016', '642e3685-a9d1-4ffb-b216-edcfd6c8ee3b', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:28:27', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234322', '2016', 'a2cd3780-0169-4c2c-b324-8501e7b57608', '10.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:28:22', '0.0');
INSERT INTO `paid_vacation` VALUES ('43242343220', '2016', 'c4aca1fa-7b9d-4bfe-ae2b-a710a19453a6', '9.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:28:41', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234324', '2016', 'be158a57-0b29-4911-bc31-cc5cb0cbc887', '0.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:29:27', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234325', '2016', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '3.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:29:57', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234326', '2016', 'bfe221f2-9a71-45c0-8e29-359c14e3f699', '3.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:30:15', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234327', '2016', '5235b043-6d53-4005-83ab-028c9d77fcaa', '0.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:30:35', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234328', '2016', 'b8449be3-e7d5-4e00-bd79-123fc788cc21', '3.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:30:59', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234353', '2016', '4b06055c-7a1f-4295-b1cd-49fd8fa45088', '1.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:29:10', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234354', '2016', '66097385-d888-45e8-80ce-ab7503e3ac05', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:19:03', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234355', '2016', '6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '10.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:31:35', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234356', '2016', '3e4e88b7-b314-4660-a34a-ac95cf8068f0', '3.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:33:36', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234357', '2016', '04f03d40-afa5-44b8-8b15-781e9e18f81e', '1.5', '0.0', 'sys', '管理员', '2016-08-22 11:32:41', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234358', '2016', '1cf7d8d9-159b-4df7-8c6f-ff34b2ba33b1', '3.5', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:29:19', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234359', '2016', '45d8b359-06c3-4d0f-844d-8df444623e22', '3.0', '3.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:29:44', '3.0');
INSERT INTO `paid_vacation` VALUES ('4324234360', '2016', 'a1d225d5-2704-416d-88ca-def0f50e0b44', '2.5', '0.0', '929009b2-2ee5-4a31-8392-aadee28443fb', '吴诗', '2016-08-17 11:06:06', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234361', '2016', '2d5375c9-f488-4c0b-8798-66463a437705', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:30:27', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234362', '2016', '929009b2-2ee5-4a31-8392-aadee28443fb', '3.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:30:54', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234363', '2016', '36da438a-440a-4d23-a9b3-b92e9da496b7', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:31:11', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234364', '2016', '9a476763-96a9-44d8-a4c2-9f4a682aa24b', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:31:33', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234365', '2016', '28c86382-167c-44ac-a905-b80eed821c87', '4.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:37:45', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234366', '2016', 'fab289d4-f549-4583-a0b0-33a9d8ea03c4', '3.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:38:05', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234367', '2016', '402cc2d3-b3a0-4508-8419-e18051dd79f0', '5.0', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:38:22', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234368', '2016', 'cdad0f46-61ac-490a-affd-c82b12900baa', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:33:01', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234369', '2016', '2df2b000-8217-450f-aa4c-a3a989e86aa9', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:36:08', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234370', '2016', 'b04df9f4-7bf3-4004-8212-2041652e581f', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:37:56', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234371', '2016', 'c748ab56-6ac5-48d2-9493-798cf09120f2', '3.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:38:31', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234372', '2016', '5b76518f-2c10-4cc8-bf1c-55262a9cb3c1', '2.5', '0.0', 'cdad0f46-61ac-490a-affd-c82b12900baa', '赵洪坤', '2016-08-10 12:39:31', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234373', '2016', '6dfd2afd-82d3-4f1e-9dd1-25c57feb77af', '5.0', '2.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:39:35', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234374', '2016', '476d1e28-fd96-41fb-8c2a-e40275e97d4b', '4.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:40:03', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234375', '2016', '5e7fdddb-cf88-46e6-862d-1ffb94e76cea', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:40:30', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234376', '2016', 'fbc67ba5-2ed8-4c90-9c25-b787c2482737', '3.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:40:52', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234377', '2016', '8dbb8937-454c-4afd-aabb-97254287ce15', '4.5', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:41:30', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234378', '2016', 'b955ad4d-ba33-453c-b457-7a25f7c8a57d', '4.5', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:41:58', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234379', '2016', '7457be91-b7f9-47d0-8720-bbed3d2a88cb', '5.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 09:42:22', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234380', '2016', 'b25674f1-c2d2-49af-bc7d-1c8165525c91', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 14:34:36', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234381', '2016', '3057fa6f-e5d7-46f4-b33e-aaba4bc3d617', '0.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-02 14:34:19', '0.0');
INSERT INTO `paid_vacation` VALUES ('4324234382', '2016', 'ca8a2767-c9d2-4772-9f7b-61d0d1fde0d9', '7.0', '0.0', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-01 15:32:41', '0.0');
INSERT INTO `paid_vacation` VALUES ('45d10264-ffb5-430a-9c6e-e4c6707073b9', '2016', 'be63974f-f41e-469a-9361-f5273393d93c', '0.0', '5.0', 'sys', 'sys', '2016-09-23 18:33:14', '0.0');
INSERT INTO `paid_vacation` VALUES ('4b28d458-dbaf-4f35-abcd-3023d60f9f6f', '2016', '1567f29b-e44f-4849-864e-c108ece088c6', '0.0', '5.0', 'sys', 'sys', '2016-09-20 17:59:49', '0.0');
INSERT INTO `paid_vacation` VALUES ('b2dd0520-126d-4d27-b3dd-3cd4045cf760', '2016', '0cb0e7e8-f156-4fe8-8219-99de55c1e143', '0.0', '5.0', 'sys', 'sys', '2016-09-20 17:59:49', '0.0');
INSERT INTO `paid_vacation` VALUES ('b3ad0570-8984-4cfb-a954-a8710ecccd19', '2016', 'f32f8975-74b0-4561-a7d7-b03024ba4985', '5.0', '0.0', null, null, null, '0.0');
INSERT INTO `paid_vacation` VALUES ('bcbe4e36-6259-46a2-b71e-96ed6d795024', '2016', 'c6e7ce29-0651-4322-aba2-3fa64e8614f7', '0.0', '5.0', 'sys', 'sys', '2016-09-23 18:33:14', '0.0');

-- ----------------------------
-- Table structure for `rest`
-- ----------------------------
DROP TABLE IF EXISTS `rest`;
CREATE TABLE `rest` (
  `rest_id` varchar(64) NOT NULL DEFAULT '',
  `user_id` varchar(64) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `morning` tinyint(1) DEFAULT NULL,
  `days` decimal(10,1) DEFAULT NULL,
  `staff_ids` varchar(1024) DEFAULT NULL,
  `audit_state` varchar(16) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  `auditor_id` varchar(64) DEFAULT NULL,
  `auditor_name` varchar(64) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `creator_id` varchar(64) DEFAULT NULL,
  `creator_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`rest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rest
-- ----------------------------

-- ----------------------------
-- Table structure for `systemparam`
-- ----------------------------
DROP TABLE IF EXISTS `systemparam`;
CREATE TABLE `systemparam` (
  `param_key` varchar(32) NOT NULL DEFAULT '',
  `param_value` varchar(64) DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemparam
-- ----------------------------
INSERT INTO `systemparam` VALUES ('LIGHT_DELAY_MINUTES', '5', 'sys', '管理员', '2016-06-24 10:27:48');
INSERT INTO `systemparam` VALUES ('NORMAL_DELAY_MINUTES', '15', 'sys', '管理员', '2016-06-24 10:27:48');
INSERT INTO `systemparam` VALUES ('PAID_VACATION_INNER', '5', 'sys', '管理员', '2016-06-24 10:26:13');
INSERT INTO `systemparam` VALUES ('PAID_VACATION_ONE_YEAR', '5', 'sys', '管理员', '2016-07-13 16:45:41');
INSERT INTO `systemparam` VALUES ('PAID_VACATION_TEN_YEAR', '10', 'sys', '管理员', '2016-07-13 16:45:41');
INSERT INTO `systemparam` VALUES ('PAID_VACATION_TWENTY_YEAR', '20', 'sys', '管理员', '2016-06-24 10:26:13');
INSERT INTO `systemparam` VALUES ('SERIOUS_DELAY_MINUTES', '30', 'sys', '管理员', '2016-06-24 10:27:48');
INSERT INTO `systemparam` VALUES ('WORK_END_TIME_AM', '12:30', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-03 14:30:27');
INSERT INTO `systemparam` VALUES ('WORK_END_TIME_PM', '18:00', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '邱东岳', '2016-08-03 14:31:22');
INSERT INTO `systemparam` VALUES ('WORK_START_TIME_AM', '09:00', 'sys', '管理员', '2016-08-18 20:25:56');
INSERT INTO `systemparam` VALUES ('WORK_START_TIME_PM', '13:30', 'sys', '管理员', '2016-06-24 10:27:48');

-- ----------------------------
-- Table structure for `team`
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `team_id` varchar(64) NOT NULL DEFAULT '',
  `team_name` varchar(64) DEFAULT NULL,
  `is_mgmt` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('1383eb85-67c0-48e5-bdb8-afbfa6c45539', '东岳团队', '0');
INSERT INTO `team` VALUES ('4bc3cd5d-48ea-445b-a1f0-313d8c119b82', '运维团队', '0');
INSERT INTO `team` VALUES ('71a36952-9e62-44db-9984-d65d5bea9153', '佳楠团队', '0');
INSERT INTO `team` VALUES ('7c05ce6d-4bac-4ec7-adf7-048c397c8f57', '管理团队', '1');
INSERT INTO `team` VALUES ('9069ca24-384c-4c11-9c96-b6366b830cea', '云集客团队', '0');
INSERT INTO `team` VALUES ('9b159634-08ab-4945-a60a-1a507fc38ed3', '春男团队', '0');
INSERT INTO `team` VALUES ('9c2e2faa-2dd2-4091-9921-3b4d44b70e1a', '市场团队', '0');
INSERT INTO `team` VALUES ('ac2316a8-33de-4b64-b23c-7ba995744db0', 'UI团队', '0');
INSERT INTO `team` VALUES ('c5e73b0a-3ddf-4200-b867-b49f35af0411', '人事团队', '0');
INSERT INTO `team` VALUES ('e955b50a-4467-4b35-8ff6-b5870214b1e4', '运营团队', '0');

-- ----------------------------
-- Table structure for `team_member`
-- ----------------------------
DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member` (
  `member_id` varchar(64) NOT NULL DEFAULT '',
  `team_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `entry_time` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of team_member
-- ----------------------------
INSERT INTO `team_member` VALUES ('003263c4-00f1-4e44-a892-449ca2fe4ef6', '9069ca24-384c-4c11-9c96-b6366b830cea', '476d1e28-fd96-41fb-8c2a-e40275e97d4b', '2015-11-01 00:00:00');
INSERT INTO `team_member` VALUES ('003e29f1-0f2c-486e-a192-d3b331c0add1', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '2010-05-17 00:00:00');
INSERT INTO `team_member` VALUES ('0357533e-5062-41b8-945d-09ea972353c2', '71a36952-9e62-44db-9984-d65d5bea9153', '4b06055c-7a1f-4295-b1cd-49fd8fa45088', '2009-06-26 00:00:00');
INSERT INTO `team_member` VALUES ('048d8794-7587-4b03-b5af-35eb15ac359d', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', '66097385-d888-45e8-80ce-ab7503e3ac05', '2010-08-10 00:00:00');
INSERT INTO `team_member` VALUES ('07b023ee-ae0e-4801-90b6-546843a79995', 'e955b50a-4467-4b35-8ff6-b5870214b1e4', '9a476763-96a9-44d8-a4c2-9f4a682aa24b', '2016-03-30 00:00:00');
INSERT INTO `team_member` VALUES ('0ab59db2-3227-42c1-a4da-28e6b781ca49', '9b159634-08ab-4945-a60a-1a507fc38ed3', '1a17fd1c-2bc1-41ff-ad9c-693ae6cbc489', '2011-05-19 00:00:00');
INSERT INTO `team_member` VALUES ('0d0fabdc-0360-49f9-bff1-cbbc566f8ff3', '9069ca24-384c-4c11-9c96-b6366b830cea', 'a2cd3780-0169-4c2c-b324-8501e7b57608', '2008-02-04 00:00:00');
INSERT INTO `team_member` VALUES ('10592708-ccdb-493c-8f70-e0918395a1d7', 'ac2316a8-33de-4b64-b23c-7ba995744db0', 'b8449be3-e7d5-4e00-bd79-123fc788cc21', '2010-08-02 00:00:00');
INSERT INTO `team_member` VALUES ('15e71b00-c120-4c6f-8c58-6859271376f0', '71a36952-9e62-44db-9984-d65d5bea9153', 'b04df9f4-7bf3-4004-8212-2041652e581f', '2016-05-09 00:00:00');
INSERT INTO `team_member` VALUES ('17178047-8585-4267-8caa-b3caee4eabf8', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', 'be158a57-0b29-4911-bc31-cc5cb0cbc887', '2009-07-13 00:00:00');
INSERT INTO `team_member` VALUES ('18784221-c9d6-45c3-9801-5b1d4897963e', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', '6dfd2afd-82d3-4f1e-9dd1-25c57feb77af', '2016-05-30 00:00:00');
INSERT INTO `team_member` VALUES ('1e55298b-ecfa-407e-9b94-252d6736f1b4', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', '642e3685-a9d1-4ffb-b216-edcfd6c8ee3b', '2015-05-28 00:00:00');
INSERT INTO `team_member` VALUES ('2701b8d1-8eb7-48ce-9c9e-266bd7180307', '9b159634-08ab-4945-a60a-1a507fc38ed3', '1cf7d8d9-159b-4df7-8c6f-ff34b2ba33b1', '2015-12-04 00:00:00');
INSERT INTO `team_member` VALUES ('29044e5b-8297-4c92-87ce-daf12e442524', 'e955b50a-4467-4b35-8ff6-b5870214b1e4', '28c86382-167c-44ac-a905-b80eed821c87', '2016-03-30 00:00:00');
INSERT INTO `team_member` VALUES ('2ae1a53b-99f2-4bdf-b883-66f51a873a47', '9069ca24-384c-4c11-9c96-b6366b830cea', 'fbc67ba5-2ed8-4c90-9c25-b787c2482737', '2015-11-01 00:00:00');
INSERT INTO `team_member` VALUES ('2d4a9ee5-93de-403a-87fe-9d770d7e7882', '9b159634-08ab-4945-a60a-1a507fc38ed3', '7457be91-b7f9-47d0-8720-bbed3d2a88cb', '2016-07-15 00:00:00');
INSERT INTO `team_member` VALUES ('2d73b3d3-a6c6-4c90-9f3d-3232b6089ec7', 'ac2316a8-33de-4b64-b23c-7ba995744db0', 'a1d225d5-2704-416d-88ca-def0f50e0b44', '2016-02-29 00:00:00');
INSERT INTO `team_member` VALUES ('2dc7eb45-b8a7-4399-9315-fe5262c4015e', '9b159634-08ab-4945-a60a-1a507fc38ed3', 'c4aca1fa-7b9d-4bfe-ae2b-a710a19453a6', '2008-01-13 00:00:00');
INSERT INTO `team_member` VALUES ('333655dc-5eb6-42d7-a151-f876fc14e476', 'ac2316a8-33de-4b64-b23c-7ba995744db0', '36da438a-440a-4d23-a9b3-b92e9da496b7', '2016-03-28 00:00:00');
INSERT INTO `team_member` VALUES ('338ee710-4b46-49b5-be28-8493eb00d12d', '71a36952-9e62-44db-9984-d65d5bea9153', '52a087a9-8824-41cd-9248-fc7896816614', '2013-06-17 00:00:00');
INSERT INTO `team_member` VALUES ('358a93bb-8c92-47c2-8991-6ab6ed1fd3f2', '9b159634-08ab-4945-a60a-1a507fc38ed3', 'c748ab56-6ac5-48d2-9493-798cf09120f2', '2016-05-10 00:00:00');
INSERT INTO `team_member` VALUES ('39fff0b0-07cf-49f2-bf2c-01e4080efdeb', '71a36952-9e62-44db-9984-d65d5bea9153', '2df2b000-8217-450f-aa4c-a3a989e86aa9', '2016-05-09 00:00:00');
INSERT INTO `team_member` VALUES ('3d1dd688-7c92-4df6-a4c8-bc54b86e6d2c', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', '6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '2010-11-30 00:00:00');
INSERT INTO `team_member` VALUES ('4284a636-a375-4526-884a-2926c57a6db7', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', 'b8449be3-e7d5-4e00-bd79-123fc788cc21', '2010-08-02 00:00:00');
INSERT INTO `team_member` VALUES ('44e64613-5916-4d65-a73d-55b6aa8430f6', '9b159634-08ab-4945-a60a-1a507fc38ed3', '939782bc-d024-45c7-98c3-fa98d1fcfd5a', '2012-08-20 00:00:00');
INSERT INTO `team_member` VALUES ('4521d64f-483c-49f7-b419-eeb35c598f53', '9b159634-08ab-4945-a60a-1a507fc38ed3', 'e2fe79d9-baf1-4da4-a761-b663efff009d', '2014-03-11 00:00:00');
INSERT INTO `team_member` VALUES ('474695f5-8d9d-44cc-9d0e-618623734669', '9b159634-08ab-4945-a60a-1a507fc38ed3', '04f03d40-afa5-44b8-8b15-781e9e18f81e', '2014-03-19 00:00:00');
INSERT INTO `team_member` VALUES ('4bdb8a95-52b3-4928-945e-2160c052995f', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', '137ed0bb-a1b4-49b1-93e4-dbd115c9b13b', '2010-05-17 00:00:00');
INSERT INTO `team_member` VALUES ('4bf72302-fa44-4c2d-b0d7-d4bc13c98d24', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', '5235b043-6d53-4005-83ab-028c9d77fcaa', '2010-06-09 00:00:00');
INSERT INTO `team_member` VALUES ('574d417f-ef3c-4bb9-8761-697f84e6b97a', '71a36952-9e62-44db-9984-d65d5bea9153', '3e4e88b7-b314-4660-a34a-ac95cf8068f0', '2014-03-10 00:00:00');
INSERT INTO `team_member` VALUES ('57e2db63-745d-4f79-a89a-93ff7c3eccfb', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', '4b06055c-7a1f-4295-b1cd-49fd8fa45088', '2009-06-26 00:00:00');
INSERT INTO `team_member` VALUES ('59a2ea0c-0614-47b0-b8ac-6559a5b2fb0e', '71a36952-9e62-44db-9984-d65d5bea9153', 'b955ad4d-ba33-453c-b457-7a25f7c8a57d', '2016-07-14 00:00:00');
INSERT INTO `team_member` VALUES ('5ce64c37-b33a-4a64-b77d-c88cab31777d', '9b159634-08ab-4945-a60a-1a507fc38ed3', 'f32f8975-74b0-4561-a7d7-b03024ba4985', '2016-08-01 00:00:00');
INSERT INTO `team_member` VALUES ('5fe1b90f-5d66-471c-a9de-34c11c9afd6c', 'c5e73b0a-3ddf-4200-b867-b49f35af0411', 'be158a57-0b29-4911-bc31-cc5cb0cbc887', '2009-07-13 00:00:00');
INSERT INTO `team_member` VALUES ('86446888-dea4-4472-9dce-5ad78eac353b', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', '9a476763-96a9-44d8-a4c2-9f4a682aa24b', '2016-03-30 00:00:00');
INSERT INTO `team_member` VALUES ('8bd3906a-d009-45fc-bd1b-ef5845964991', '9069ca24-384c-4c11-9c96-b6366b830cea', '5e7fdddb-cf88-46e6-862d-1ffb94e76cea', '2015-11-01 00:00:00');
INSERT INTO `team_member` VALUES ('9ee0bc0d-01b3-4c6b-891c-889a088ad044', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', 'b0216c52-f3e3-42fb-b59f-4414860e2c39', '2015-04-24 00:00:00');
INSERT INTO `team_member` VALUES ('a077d95f-1c9d-44f9-a399-67c516eea208', '4bc3cd5d-48ea-445b-a1f0-313d8c119b82', '6e070dc1-aa87-4d1f-a56a-7f1ef8da0218', '2010-11-30 00:00:00');
INSERT INTO `team_member` VALUES ('a735ba00-01e4-46fe-8707-9f7ea9d67c95', '4bc3cd5d-48ea-445b-a1f0-313d8c119b82', '402cc2d3-b3a0-4508-8419-e18051dd79f0', '2016-04-25 00:00:00');
INSERT INTO `team_member` VALUES ('afc083c7-ace1-4218-8302-297358df8893', 'ac2316a8-33de-4b64-b23c-7ba995744db0', 'fab289d4-f549-4583-a0b0-33a9d8ea03c4', '2016-04-18 00:00:00');
INSERT INTO `team_member` VALUES ('b3c50dfe-0e27-412d-ba80-951f9d73ba12', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', 'bfe221f2-9a71-45c0-8e29-359c14e3f699', '2010-05-31 00:00:00');
INSERT INTO `team_member` VALUES ('b930aedd-764a-440a-a401-cf3aad5990a5', '1383eb85-67c0-48e5-bdb8-afbfa6c45539', 'cdad0f46-61ac-490a-affd-c82b12900baa', '2016-05-03 00:00:00');
INSERT INTO `team_member` VALUES ('c8441e47-0cdb-4c5b-9324-8f8866e7233c', '9b159634-08ab-4945-a60a-1a507fc38ed3', '5b76518f-2c10-4cc8-bf1c-55262a9cb3c1', '2016-05-12 00:00:00');
INSERT INTO `team_member` VALUES ('d627c0a5-9c57-4eb5-abf0-719a004673b0', '9b159634-08ab-4945-a60a-1a507fc38ed3', 'b0216c52-f3e3-42fb-b59f-4414860e2c39', '2015-04-24 00:00:00');
INSERT INTO `team_member` VALUES ('d79c0a99-edcf-4fbc-9943-3ec149ba0285', '9b159634-08ab-4945-a60a-1a507fc38ed3', '45d8b359-06c3-4d0f-844d-8df444623e22', '2015-12-04 00:00:00');
INSERT INTO `team_member` VALUES ('e4f74627-79f5-4d5d-a02c-f26ed82b7ffa', '9b159634-08ab-4945-a60a-1a507fc38ed3', '2d5375c9-f488-4c0b-8798-66463a437705', '2016-03-15 00:00:00');
INSERT INTO `team_member` VALUES ('e52ee192-1d63-45f7-8972-2d5a9ce880d5', '9069ca24-384c-4c11-9c96-b6366b830cea', '8dbb8937-454c-4afd-aabb-97254287ce15', '2015-12-01 00:00:00');
INSERT INTO `team_member` VALUES ('e913f553-0685-432f-9327-d8c32f7ecbab', 'c5e73b0a-3ddf-4200-b867-b49f35af0411', '929009b2-2ee5-4a31-8392-aadee28443fb', '2016-03-17 00:00:00');
INSERT INTO `team_member` VALUES ('f3b9c5a2-b384-4bd2-a371-7ce811e3a631', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', 'a2cd3780-0169-4c2c-b324-8501e7b57608', '2008-02-04 00:00:00');
INSERT INTO `team_member` VALUES ('f61501ee-e724-4b4b-aca4-256a1527ae0b', '7c05ce6d-4bac-4ec7-adf7-048c397c8f57', 'c4aca1fa-7b9d-4bfe-ae2b-a710a19453a6', '2008-01-13 00:00:00');
INSERT INTO `team_member` VALUES ('ff0ec524-da36-4864-8b00-bbd755202b64', '9c2e2faa-2dd2-4091-9921-3b4d44b70e1a', '534ee8b1-fd8c-4087-93e7-932a81c1b128', '2012-11-08 00:00:00');

-- ----------------------------
-- Table structure for `vacation`
-- ----------------------------
DROP TABLE IF EXISTS `vacation`;
CREATE TABLE `vacation` (
  `vacation_id` varchar(64) NOT NULL DEFAULT '',
  `vacation_date` datetime DEFAULT NULL,
  `morning` tinyint(1) DEFAULT NULL,
  `applicant_id` varchar(64) DEFAULT NULL,
  `applicant_name` varchar(64) DEFAULT NULL,
  `total_days` decimal(10,1) DEFAULT NULL,
  `memo` varchar(64) DEFAULT NULL,
  `audit_state` varchar(16) DEFAULT NULL,
  `has_sick_type` tinyint(1) DEFAULT NULL,
  `certificate_state` varchar(16) DEFAULT NULL,
  `certificate_pic_id` varchar(64) DEFAULT NULL,
  `certificate_upload_time` datetime DEFAULT NULL,
  `auditor_id1` varchar(64) DEFAULT NULL,
  `auditor_name1` varchar(64) DEFAULT NULL,
  `audit_time1` datetime DEFAULT NULL,
  `auditor_id2` varchar(64) DEFAULT NULL,
  `auditor_name2` varchar(64) DEFAULT NULL,
  `audit_time2` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `creator_id` varchar(64) DEFAULT NULL,
  `creator_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier_id` varchar(64) DEFAULT NULL,
  `modifier_name` varchar(64) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`vacation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vacation
-- ----------------------------

-- ----------------------------
-- Table structure for `vacation_detail`
-- ----------------------------
DROP TABLE IF EXISTS `vacation_detail`;
CREATE TABLE `vacation_detail` (
  `detail_id` varchar(64) NOT NULL DEFAULT '',
  `vacation_id` varchar(64) DEFAULT NULL,
  `vacation_type` varchar(16) DEFAULT NULL,
  `vacation_days` decimal(10,1) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vacation_detail
-- ----------------------------
