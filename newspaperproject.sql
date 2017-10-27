/*
Navicat MySQL Data Transfer

Source Server         : Erudite
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : newspaperproject

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2017-10-27 14:37:08
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `addpedlar`
-- ----------------------------
DROP TABLE IF EXISTS `addpedlar`;
CREATE TABLE `addpedlar` (
  `PName` varchar(40) DEFAULT NULL,
  `Mobile` varchar(10) DEFAULT NULL,
  `ArAlotted` varchar(70) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `IdPfPath` varchar(150) DEFAULT NULL,
  `DOJoining` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of addpedlar
-- ----------------------------
INSERT INTO `addpedlar` VALUES ('Ravi Arora', '9584546455', 'Ajit Road', 'guniana', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Gagan', '9454646464', 'Bharatnagar', 'klnscdc', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Raghav', '9884664612', 'Bhagu Road', 'sxxdkjs', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Vivek', '9454512345', 'Civil lines', 'xsdsdx', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Samar', '8437145781', 'Bibiwala Road', 'kjkcscbd', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Ishan', '7435437894', 'Fauji Chowk', 'Ajit road', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-12');
INSERT INTO `addpedlar` VALUES ('Shubhangi', '9780519781', 'Veer colony', 'hjudsd', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Aadhar-card.jpg', '2017-07-15');
INSERT INTO `addpedlar` VALUES ('Dyuksh', '7981453978', 'Thermal colony', 'mount fuji', 'C:\\Users\\Muskan Mittal\\Pictures\\Saved Pictures\\Newspaper Icon.png', '2017-10-19');

-- ----------------------------
-- Table structure for `areas`
-- ----------------------------
DROP TABLE IF EXISTS `areas`;
CREATE TABLE `areas` (
  `Area` varchar(80) NOT NULL DEFAULT '',
  PRIMARY KEY (`Area`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of areas
-- ----------------------------
INSERT INTO `areas` VALUES ('Ajit Road');
INSERT INTO `areas` VALUES ('Bhagu Road');
INSERT INTO `areas` VALUES ('Bharatnagar');
INSERT INTO `areas` VALUES ('Bibiwala Road');
INSERT INTO `areas` VALUES ('Civil lines');
INSERT INTO `areas` VALUES ('Fauji Chowk');
INSERT INTO `areas` VALUES ('GT Road');
INSERT INTO `areas` VALUES ('Hogwards wizard? School');
INSERT INTO `areas` VALUES ('Kikar bazar');
INSERT INTO `areas` VALUES ('Mall Road');
INSERT INTO `areas` VALUES ('Modal town');
INSERT INTO `areas` VALUES ('NFL colony');
INSERT INTO `areas` VALUES ('Shaktinagar');
INSERT INTO `areas` VALUES ('Thermal colony');
INSERT INTO `areas` VALUES ('Veer colony');

-- ----------------------------
-- Table structure for `bills`
-- ----------------------------
DROP TABLE IF EXISTS `bills`;
CREATE TABLE `bills` (
  `CId` int(11) DEFAULT NULL,
  `Bill` double DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `MM` int(11) DEFAULT NULL,
  `YY` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of bills
-- ----------------------------
INSERT INTO `bills` VALUES ('1', '279', '0', '1', '2017');
INSERT INTO `bills` VALUES ('2', '480.5', '0', '1', '2017');
INSERT INTO `bills` VALUES ('1', '279', '0', '8', '2017');
INSERT INTO `bills` VALUES ('2', '480.5', '0', '8', '2017');
INSERT INTO `bills` VALUES ('3', '713', '1', '8', '2017');
INSERT INTO `bills` VALUES ('4', '923.8000000000001', '1', '8', '2017');
INSERT INTO `bills` VALUES ('5', '1249.3', '0', '8', '2017');
INSERT INTO `bills` VALUES ('1', '252', '0', '2', '2018');
INSERT INTO `bills` VALUES ('2', '434', '0', '2', '2018');
INSERT INTO `bills` VALUES ('3', '644', '0', '2', '2018');
INSERT INTO `bills` VALUES ('4', '834.4', '0', '2', '2018');
INSERT INTO `bills` VALUES ('5', '1128.3999999999999', '0', '2', '2018');
INSERT INTO `bills` VALUES ('1', '252', '0', '2', '2018');
INSERT INTO `bills` VALUES ('2', '434', '0', '2', '2018');
INSERT INTO `bills` VALUES ('3', '644', '0', '2', '2018');
INSERT INTO `bills` VALUES ('4', '834.4', '0', '2', '2018');
INSERT INTO `bills` VALUES ('5', '1128.3999999999999', '0', '2', '2018');
INSERT INTO `bills` VALUES ('1', '252', '0', '2', '2018');
INSERT INTO `bills` VALUES ('2', '434', '0', '2', '2018');
INSERT INTO `bills` VALUES ('3', '644', '0', '2', '2018');
INSERT INTO `bills` VALUES ('4', '834.4', '0', '2', '2018');
INSERT INTO `bills` VALUES ('5', '1128.3999999999999', '0', '2', '2018');
INSERT INTO `bills` VALUES ('1', '270', '0', '4', '2017');
INSERT INTO `bills` VALUES ('2', '465', '0', '4', '2017');
INSERT INTO `bills` VALUES ('3', '690', '1', '4', '2017');
INSERT INTO `bills` VALUES ('4', '894', '0', '4', '2017');
INSERT INTO `bills` VALUES ('5', '1209', '0', '4', '2017');

-- ----------------------------
-- Table structure for `customerinfo`
-- ----------------------------
DROP TABLE IF EXISTS `customerinfo`;
CREATE TABLE `customerinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CName` varchar(255) NOT NULL,
  `Adrs` varchar(255) DEFAULT NULL,
  `Mob` varchar(10) DEFAULT NULL,
  `Ar` varchar(60) DEFAULT NULL,
  `Hawker` varchar(40) DEFAULT NULL,
  `Npaper` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of customerinfo
-- ----------------------------
INSERT INTO `customerinfo` VALUES ('1', 'Muskan ', 'ajit road', '9780519781', 'Ajit Road', 'Ravi Arora', 'Dainik Bhaskar,Hindu,');
INSERT INTO `customerinfo` VALUES ('2', 'Karan', 'bibiwala', '986564461', 'Bibiwala Road', 'Ravi Arora', 'Punjab Kesri,Times of India,');
INSERT INTO `customerinfo` VALUES ('3', 'Sahil', 'bibiwala road', '9256239781', 'Civil lines', 'Vivek', 'Times of India,Tribune,');
INSERT INTO `customerinfo` VALUES ('4', 'Dyuksh', 'cscscs', '9256239781', 'Bibiwala Road', 'Samar', 'Dainik Jagran,Times of India,');
INSERT INTO `customerinfo` VALUES ('5', 'Renu', 'Ajit Road st no 7', '9256239781', 'GT Road', 'Vivek', 'Dainik Bhaskar,Hindu,Punjab Kesri,');
INSERT INTO `customerinfo` VALUES ('6', 'Sanjeev K Mittal', 'Bti Road			', '9855656500', 'Thermal colony', '-Select-', 'Hindu,Punjab Kesri,Punjabi Tribune,Times of India,Tribune,');
INSERT INTO `customerinfo` VALUES ('7', 'Sahil', 'bibiwala road', '9256239781', 'Civil lines', 'Vivek', 'Hermoini Times,');

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `username` varchar(20) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('Muskan Mittal', 'muskanmtl');

-- ----------------------------
-- Table structure for `papertypes`
-- ----------------------------
DROP TABLE IF EXISTS `papertypes`;
CREATE TABLE `papertypes` (
  `NName` varchar(255) NOT NULL,
  `Price` double DEFAULT NULL,
  PRIMARY KEY (`NName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of papertypes
-- ----------------------------
INSERT INTO `papertypes` VALUES ('Dainik Bhaskar', '2.5');
INSERT INTO `papertypes` VALUES ('Dainik Jagran', '1.8');
INSERT INTO `papertypes` VALUES ('Hermoini Times', '100');
INSERT INTO `papertypes` VALUES ('Hindu', '6.5');
INSERT INTO `papertypes` VALUES ('Punjab Kesri', '1.5');
INSERT INTO `papertypes` VALUES ('Punjabi Tribune', '1.6');
INSERT INTO `papertypes` VALUES ('Times of India', '5');
INSERT INTO `papertypes` VALUES ('Tribune', '2.5');
