/*
 Navicat Premium Data Transfer

 Source Server         : admin-recent
 Source Server Type    : MySQL
 Source Server Version : 50544
 Source Host           : localhost
 Source Database       : auction

 Target Server Type    : MySQL
 Target Server Version : 50544
 File Encoding         : utf-8

*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auction_item`
-- ----------------------------
DROP TABLE IF EXISTS `auction_item`;
CREATE TABLE `auction_item` (
  `ID` char(36) NOT NULL,
  `AUTHOR_ID` char(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `ITEM_CATEGORY_ID` char(36) NOT NULL,
  `ITEM_STATUS_ID` char(36) NOT NULL,
  `CREATED_BY` char(36) NOT NULL,
  `CREATED_ON` bigint(20) NOT NULL,
  `SOLD_TO` char(36) DEFAULT NULL,
  `SOLD_ON` bigint(20) DEFAULT NULL,
  `BID_CLOSURE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `AUTHOR_ID_NAME_UK` (`AUTHOR_ID`,`NAME`),
  KEY `ITEM_CATEGORY_ID` (`ITEM_CATEGORY_ID`),
  KEY `ITEM_STATUS_ID` (`ITEM_STATUS_ID`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`ITEM_CATEGORY_ID`) REFERENCES `auction_item_category` (`ID`),
  CONSTRAINT `fk_status_id` FOREIGN KEY (`ITEM_STATUS_ID`) REFERENCES `auction_item_status` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `auction_item_category`
-- ----------------------------
DROP TABLE IF EXISTS `auction_item_category`;
CREATE TABLE `auction_item_category` (
  `ID` char(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `auction_item_status`
-- ----------------------------
DROP TABLE IF EXISTS `auction_item_status`;
CREATE TABLE `auction_item_status` (
  `ID` char(36) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `author`
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `ID` char(36) NOT NULL,
  `CONTACT_ID` char(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `CONTACT_ID` (`CONTACT_ID`),
  CONSTRAINT `fk_contact_id` FOREIGN KEY (`CONTACT_ID`) REFERENCES `contact` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `bidder`
-- ----------------------------
DROP TABLE IF EXISTS `bidder`;
CREATE TABLE `bidder` (
  `ID` char(36) NOT NULL,
  `CONTACT_ID` char(36) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `CONTACT_ID` (`CONTACT_ID`),
  KEY `CONTACT_ID_2` (`CONTACT_ID`),
  CONSTRAINT `fk_contact_id2` FOREIGN KEY (`CONTACT_ID`) REFERENCES `contact` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `bids`
-- ----------------------------
DROP TABLE IF EXISTS `bids`;
CREATE TABLE `bids` (
  `ID` char(36) NOT NULL,
  `BIDDER_ID` char(36) NOT NULL,
  `AUCTION_ITEM_ID` char(36) NOT NULL,
  `BID_DATE` bigint(20) NOT NULL,
  `BID_AMOUNT` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `BIDDER_ID` (`BIDDER_ID`),
  KEY `AUCTION_ITEM_ID` (`AUCTION_ITEM_ID`),
  CONSTRAINT `fk_item_id` FOREIGN KEY (`AUCTION_ITEM_ID`) REFERENCES `auction_item` (`ID`),
  CONSTRAINT `fk_bidder_id` FOREIGN KEY (`BIDDER_ID`) REFERENCES `bidder` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `contact`
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `ID` char(36) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL DEFAULT '',
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) NOT NULL DEFAULT '',
  `EMAIL` varchar(255) NOT NULL DEFAULT '',
  `ALT_EMAIL` varchar(255) DEFAULT NULL,
  `PHONE` varchar(30) NOT NULL DEFAULT '',
  `ALT_PHONE` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
