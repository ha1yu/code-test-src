/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 20/12/2023 11:03:03

 数据库配置账号密码见 application.properties
 数据库名称 : test
 账号密码： root/root
 还原.sql文件方法
 mysql>use test
 mysql>source test.sql # 注意路径
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fraction
-- ----------------------------
DROP TABLE IF EXISTS `fraction`;
CREATE TABLE `fraction`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fraction` int(8) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fraction
-- ----------------------------
INSERT INTO `fraction` VALUES (1, 'Dumb', 'man', 89);
INSERT INTO `fraction` VALUES (2, 'Angelina', 'woman', 87);
INSERT INTO `fraction` VALUES (3, 'Dummy', 'man', 89);
INSERT INTO `fraction` VALUES (4, 'secure', 'woman', 99);
INSERT INTO `fraction` VALUES (5, 'stupid', 'woman', 88);
INSERT INTO `fraction` VALUES (6, 'superman', 'man', 78);
INSERT INTO `fraction` VALUES (7, 'batman', 'man', 90);
INSERT INTO `fraction` VALUES (8, 'admin', 'woman', 92);
INSERT INTO `fraction` VALUES (9, 'admin1', 'woman', 67);
INSERT INTO `fraction` VALUES (10, 'admin2', 'man', 45);
INSERT INTO `fraction` VALUES (11, 'admin3', 'man', 72);
INSERT INTO `fraction` VALUES (12, 'dhakkan', 'woman', 56);
INSERT INTO `fraction` VALUES (13, 'admin4', 'woman', 90);
INSERT INTO `fraction` VALUES (14, 'cc', 'woman', 79);
INSERT INTO `fraction` VALUES (15, 'mechoy', 'man', 57);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '华为', 2000.00);
INSERT INTO `goods` VALUES (2, '苹果', 3000.00);
INSERT INTO `goods` VALUES (3, '小米', 2222.00);
INSERT INTO `goods` VALUES (4, 'vivo', NULL);
INSERT INTO `goods` VALUES (5, '三星', 2300.00);
INSERT INTO `goods` VALUES (6, '海尔', 1800.00);
INSERT INTO `goods` VALUES (7, 'IBM', 5000.00);
INSERT INTO `goods` VALUES (8, '格力', NULL);
INSERT INTO `goods` VALUES (9, 'xxx', 123.00);

-- ----------------------------
-- Table structure for soldier
-- ----------------------------
DROP TABLE IF EXISTS `soldier`;
CREATE TABLE `soldier`  (
  `soldierId` int(11) NOT NULL AUTO_INCREMENT,
  `soldierName` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `soldierWeapon` char(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`soldierId`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of soldier
-- ----------------------------
INSERT INTO `soldier` VALUES (11, 'Mechoy', '啥也不是');
INSERT INTO `soldier` VALUES (12, 'Nopass', '黄金AK');
INSERT INTO `soldier` VALUES (13, 'zzz', 'M4A1');
INSERT INTO `soldier` VALUES (24, 'Mechoy', '卷心菜');
INSERT INTO `soldier` VALUES (15, '王总', '某女秘书-hs');
INSERT INTO `soldier` VALUES (23, '王总', 'M4A1-T');
INSERT INTO `soldier` VALUES (22, '王总', 'M4A1-茉莉');
INSERT INTO `soldier` VALUES (25, 'Mechoy', '弟中弟-S');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'Dumb', 'Dumb', '123@qq.com');
INSERT INTO `users` VALUES (2, 'Angelina', 'I-kill-you', '21312@qq.com');
INSERT INTO `users` VALUES (3, 'Dummy', 'p@ssword', '75463@qq.com');
INSERT INTO `users` VALUES (4, 'secure', 'crappy', '6324241@qq.com');
INSERT INTO `users` VALUES (5, 'stupid', 'stupidity', '577443@qq.com');
INSERT INTO `users` VALUES (6, 'superman', 'genious', '2213125@qq.com');
INSERT INTO `users` VALUES (7, 'batman', 'mob!le', '6456@qq.com');
INSERT INTO `users` VALUES (8, 'admin', 'admin', '1231246@qq.com');
INSERT INTO `users` VALUES (9, 'admin1', 'admin1', '7789897@qq.com');
INSERT INTO `users` VALUES (10, 'admin2', 'admin2', '55768@qq.com');
INSERT INTO `users` VALUES (11, 'admin3', 'admin3', '0097079@qq.com');
INSERT INTO `users` VALUES (12, 'dhakkan', 'dumbo', '245656@qq.com');
INSERT INTO `users` VALUES (13, 'admin4', 'admin4', '098976@qq.com');
INSERT INTO `users` VALUES (14, 'cc', 'pp', '2345465@qq.com');
INSERT INTO `users` VALUES (16, 'hello', '1234', '34567@qq.com');
INSERT INTO `users` VALUES (17, 'mechoy', '5678', '098675@qq.com');
INSERT INTO `users` VALUES (18, 'root', 'root', '4345656@qq.com');
INSERT INTO `users` VALUES (20, 'jack', 'jack123', '9089@qq.com');
INSERT INTO `users` VALUES (27, 'mechoy', 'admin123..', 'mechoy@yeah.net');
INSERT INTO `users` VALUES (21, 'wdqwd', 'qcwcwcqw', 'qwdqwd@qqw.com');
INSERT INTO `users` VALUES (22, 'webgoat', 'admin123', 'hello123@123.com');
INSERT INTO `users` VALUES (23, 'webgoat123', 'admin123', 'hello123@123.com');
INSERT INTO `users` VALUES (24, 'moankeji', 'admin123', 'hello123@123.com');
INSERT INTO `users` VALUES (25, 'admin123', '192023A7BBD73250516F069DF18B500', 'admin123@qq.com');
INSERT INTO `users` VALUES (26, 'nihaoaaa', '1231321', '0');

SET FOREIGN_KEY_CHECKS = 1;
