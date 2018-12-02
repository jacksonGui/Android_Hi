/*
MySQL Data Transfer
Source Host: localhost
Source Database: android_service
Target Host: localhost
Target Database: android_service
Date: 2018/12/2 13:55:45
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for moments
-- ----------------------------
DROP TABLE IF EXISTS `moments`;
CREATE TABLE `moments` (
  `id` int(10) NOT NULL auto_increment,
  `uid` int(10) default NULL,
  `content` text,
  `time` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
  `imgurl` varchar(255) default 'imgs/asd.jpg',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `add_request` VALUES ('1', '2', '3', '1', '2018-11-21');
INSERT INTO `add_request` VALUES ('2', '5', '3', '0', '2018-11-05');
INSERT INTO `add_request` VALUES ('3', '4', '1', '1', '2018-11-21 16:57:03');
INSERT INTO `add_request` VALUES ('4', '4', '9', '0', '2018-11-21 16:58:15');
INSERT INTO `add_request` VALUES ('5', '1', '1', '1', '2018-11-24 22:44:53');
INSERT INTO `add_request` VALUES ('6', '1', '7', '0', '2018-11-27 09:59:03');
INSERT INTO `add_request` VALUES ('7', '1', '1', '1', '2018-11-27 14:42:31');
INSERT INTO `friends` VALUES ('1', '1', '2', '网络16');
INSERT INTO `friends` VALUES ('2', '1', '3', '网络162');
INSERT INTO `friends` VALUES ('3', '1', '4', '网络16');
INSERT INTO `friends` VALUES ('4', '2', '1', '网络162');
INSERT INTO `friends` VALUES ('5', '3', '2', '我的好友');
INSERT INTO `friends` VALUES ('6', '2', '3', '我的好友');
INSERT INTO `friends` VALUES ('8', '4', '1', '我的好友');
INSERT INTO `friends` VALUES ('13', '1', '1', '我的好友');
INSERT INTO `moments` VALUES ('1', '2', '少时诵诗书所所所所所所所所所所所所', '11-25 06:42');
INSERT INTO `moments` VALUES ('2', '3', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '11-24 06:42');
INSERT INTO `moments` VALUES ('3', '1', '少时诵诗书所所所所所所所所所所所所所所所所所所所所所', '11-23 06:42');
INSERT INTO `moments` VALUES ('4', '1', 'asssssssssssssssss', '11-27 06:42');
INSERT INTO `user` VALUES ('1', 'zzg', '123456', '0', '123@qq.com', '123456789', 'imgs/IMG_20181126_170116.jpg');
INSERT INTO `user` VALUES ('2', 'nc', '123456', '0', '123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('3', 'szk', '123456', '0', '123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('4', 'b', '123456', '0', '123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('5', 'a', 'a', '0', 'a123@qq.com', 'a123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('6', 'Zhang ', '123', '0', '123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('7', 'as', 'a', '0', 'a123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('8', 'asd', 'sd', '0', 'sd123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('9', 'asda', 'asd', '0', 'asd123@qq.com', '123456789', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('10', '安卓', '123456', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('11', 'a', 'abb', '0', 'a', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('12', 'sdsd', 'sdsd', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('13', 'sdsds', 'sdsd', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('14', '十一月', '123', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('15', '张', '1', '0', '1', '1', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('16', '张', '1', '0', '1', '1', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('17', '??', 'q', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('18', '??socket', 'a', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('19', '启动socketaa', 'a', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('20', 'xxxx', 'x', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('21', 'ff', 'f', '0', '', '', 'imgs/a.jpg');
INSERT INTO `user` VALUES ('22', 'qq', 'q', '0', '', '', 'imgs/a.jpg');
