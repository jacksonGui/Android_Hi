/*
MySQL Data Transfer
Source Host: localhost
Source Database: android_service
Target Host: localhost
Target Database: android_service
Date: 2018/11/21 17:01:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for add_request
-- ----------------------------
DROP TABLE IF EXISTS `add_request`;
CREATE TABLE `add_request` (
  `id` int(32) NOT NULL auto_increment,
  `uid` int(32) default NULL,
  `fid` int(32) default NULL,
  `isok` int(2) default NULL,
  `time` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `id` int(10) NOT NULL auto_increment,
  `uid` int(10) default NULL,
  `fid` int(10) default NULL,
  `fgroup` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL auto_increment,
  `username` varchar(32) default NULL,
  `password` varchar(32) default NULL,
  `isonline` int(2) default '0',
  `email` varchar(32) default NULL,
  `phone` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `add_request` VALUES ('1', '2', '3', '1', '2018-11-21');
INSERT INTO `add_request` VALUES ('2', '5', '3', '0', '2018-11-05');
INSERT INTO `add_request` VALUES ('3', '4', '1', '1', '2018-11-21 16:57:03');
INSERT INTO `add_request` VALUES ('4', '4', '9', '0', '2018-11-21 16:58:15');
INSERT INTO `friends` VALUES ('1', '1', '2', '网络162');
INSERT INTO `friends` VALUES ('2', '1', '3', '网络162');
INSERT INTO `friends` VALUES ('3', '1', '4', '网络');
INSERT INTO `friends` VALUES ('4', '2', '1', '网络162');
INSERT INTO `friends` VALUES ('5', '3', '2', '我的好友');
INSERT INTO `friends` VALUES ('6', '2', '3', '我的好友');
INSERT INTO `friends` VALUES ('7', '1', '4', '我的好友');
INSERT INTO `friends` VALUES ('8', '4', '1', '我的好友');
INSERT INTO `user` VALUES ('1', 'zzg', '123456', '0', '123@qq.com', '123456789');
INSERT INTO `user` VALUES ('2', 'nc', '123456', '0', '123@qq.com', '123456789');
INSERT INTO `user` VALUES ('3', 'szk', '123456', '0', '123@qq.com', '123456789');
INSERT INTO `user` VALUES ('4', 'b', '123456', '0', '123@qq.com', '123456789');
INSERT INTO `user` VALUES ('5', 'a', 'a', '0', 'a123@qq.com', 'a123456789');
INSERT INTO `user` VALUES ('6', 'Zhang ', '123', '0', '123@qq.com', '123456789');
INSERT INTO `user` VALUES ('7', 'as', 'a', '0', 'a123@qq.com', '123456789');
INSERT INTO `user` VALUES ('8', 'asd', 'sd', '0', 'sd123@qq.com', '123456789');
INSERT INTO `user` VALUES ('9', 'asda', 'asd', '0', 'asd123@qq.com', '123456789');
