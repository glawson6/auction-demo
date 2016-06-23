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

INSERT INTO auction_item_status (ID,NAME,DESCRIPTION) VALUES ('1b7efa13-2c07-41f6-b8ed-85e5274e6a10','SOLD','idicates an auction item as sold');
INSERT INTO auction_item_status (ID,NAME,DESCRIPTION) VALUES ('b6a103c2-d144-4048-9aa3-44b1ffdc754e','AVAILABLE','idicates an auction is available for purchase');
INSERT INTO auction_item_status (ID,NAME,DESCRIPTION) VALUES ('6ce72df6-de0d-400e-8673-76265fc164c3','WITHDRAWN','idicates an auction item has been withdrawn from the auction');
INSERT INTO auction_item_status (ID,NAME,DESCRIPTION) VALUES ('83bc6c2c-b5ba-4eda-855a-33a0db49b052','ENTERED','idicates an auction item is in the system');

INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('d9909dd8-3da6-4b7d-9f8d-a4d156aee462','ELECTRONICS','Anything from Electronics');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('b58a31db-7cf7-40d8-9dda-2e247da29bad','CAR','Car and Auto items');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('a84f6287-f9e6-4b18-b6b9-f62999114a69','COMPUTERS','Anything dealing with Computers');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('00732b64-f7a7-4486-a891-e400b5f5e8b2','OTHER','Anything not specified');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('e67abb70-4f30-40ea-b4c5-41b9c16037e5','WOMENS CLOTHING','Anything for Womens Clothing');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('89c4349e-b303-480f-826c-d67acb619353','MENS CLOTHING','Anything for Mens Clothing');
INSERT INTO auction_item_category (ID,NAME,DESCRIPTION) VALUES ('5163ab81-8dad-4cd4-8a62-be7d5f692b04','KITCHEN','Anything in the kitchen');
