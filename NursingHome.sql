/*
 Navicat MySQL Data Transfer

 Source Server         : Conn1
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : NursingHome

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 03/07/2019 16:56:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `administrator_id` varchar(45) NOT NULL,
  `administrator_name` varchar(45) NOT NULL,
  `administrator_date` date DEFAULT NULL,
  `administrator_position` varchar(45) NOT NULL,
  `administrator_salary` double DEFAULT NULL,
  PRIMARY KEY (`administrator_id`),
  CONSTRAINT `admin_stafffk` FOREIGN KEY (`administrator_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bed
-- ----------------------------
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed` (
  `bed_id` varchar(45) NOT NULL,
  `bed_roomid` varchar(45) NOT NULL,
  `bed_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`bed_id`,`bed_roomid`),
  KEY `bed_roomid_idx` (`bed_roomid`),
  CONSTRAINT `bed_roomid` FOREIGN KEY (`bed_roomid`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` varchar(45) NOT NULL,
  `customer_name` varchar(45) NOT NULL,
  `customer_date` date DEFAULT NULL,
  `customer_entertime` date NOT NULL,
  `customer_roomid` varchar(45) DEFAULT NULL,
  `customer_bedid` varchar(45) NOT NULL,
  `customer_phone` varchar(45) DEFAULT NULL,
  `customer_careworker` varchar(45) NOT NULL,
  `customer_rank` int(11) NOT NULL,
  `customer_relationname` varchar(45) NOT NULL,
  `customer_relation` varchar(45) NOT NULL,
  `customer_relationphone` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`),
  KEY `customer_roomid_idx1` (`customer_roomid`),
  KEY `customer_rank_idx` (`customer_rank`),
  KEY `customer_bedidfk_idx` (`customer_bedid`),
  KEY `customer_workerfk_idx` (`customer_careworker`),
  CONSTRAINT `customer_bedidfk` FOREIGN KEY (`customer_bedid`) REFERENCES `bed` (`bed_id`),
  CONSTRAINT `customer_idfk` FOREIGN KEY (`customer_id`) REFERENCES `historical_customer` (`customer_id`),
  CONSTRAINT `customer_roomidfk` FOREIGN KEY (`customer_roomid`) REFERENCES `room` (`room_id`),
  CONSTRAINT `customer_workerfk` FOREIGN KEY (`customer_careworker`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `doctor_id` varchar(45) NOT NULL,
  `doctor_name` varchar(45) NOT NULL,
  `doctor_date` date DEFAULT NULL,
  `doctor_salary` double NOT NULL DEFAULT '6000',
  `doctor_major` varchar(45) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  UNIQUE KEY `doctor_id_UNIQUE` (`doctor_id`),
  CONSTRAINT `doctorid_stafffk` FOREIGN KEY (`doctor_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doorboy
-- ----------------------------
DROP TABLE IF EXISTS `doorboy`;
CREATE TABLE `doorboy` (
  `doorboy_id` varchar(45) NOT NULL,
  `doorboy_name` varchar(45) NOT NULL,
  `doorboy_date` date DEFAULT NULL,
  `doorboy_salary` double NOT NULL DEFAULT '3000',
  `doorboy_workplace` varchar(45) NOT NULL,
  PRIMARY KEY (`doorboy_id`),
  UNIQUE KEY `doorboy_id_UNIQUE` (`doorboy_id`),
  CONSTRAINT `doorboyid_stafffk` FOREIGN KEY (`doorboy_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for historical_customer
-- ----------------------------
DROP TABLE IF EXISTS `historical_customer`;
CREATE TABLE `historical_customer` (
  `customer_id` varchar(45) NOT NULL,
  `customer_name` varchar(45) NOT NULL,
  `customer_status` tinyint(4) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for historical_staff
-- ----------------------------
DROP TABLE IF EXISTS `historical_staff`;
CREATE TABLE `historical_staff` (
  `staff_id` varchar(45) NOT NULL,
  `staff_status` tinyint(4) NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `manager_id` varchar(45) NOT NULL,
  `manager_priv` int(11) NOT NULL,
  `manager_password` varchar(45) NOT NULL,
  PRIMARY KEY (`manager_id`),
  UNIQUE KEY `manager_id_UNIQUE` (`manager_id`),
  CONSTRAINT `manager_historical_staff_idfk` FOREIGN KEY (`manager_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `record_id` varchar(45) NOT NULL,
  `customer_id` varchar(45) NOT NULL,
  `customer_name` varchar(45) NOT NULL,
  `doctor_id` varchar(45) NOT NULL,
  `record_time` date NOT NULL,
  `record_context` varchar(45) NOT NULL,
  PRIMARY KEY (`record_id`),
  KEY `record_customerfk_idx` (`customer_id`),
  KEY `record_doctorfk_idx` (`doctor_id`),
  CONSTRAINT `record_customerfk` FOREIGN KEY (`customer_id`) REFERENCES `historical_customer` (`customer_id`),
  CONSTRAINT `record_doctorfk` FOREIGN KEY (`doctor_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `room_id` varchar(45) NOT NULL,
  `room_rank` varchar(45) NOT NULL,
  `room_totalbed` int(11) NOT NULL,
  `room_usedbed` int(11) NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker` (
  `worker_id` varchar(45) NOT NULL,
  `worker_name` varchar(45) NOT NULL,
  `worker_date` date NOT NULL,
  `worker_salary` double NOT NULL DEFAULT '3000',
  `worker_rank` varchar(45) NOT NULL,
  `worker_customerrank` int(11) NOT NULL,
  `worker_customernumber` int(11) NOT NULL,
  `worker_vispos` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`worker_id`),
  UNIQUE KEY `worker_id_UNIQUE` (`worker_id`),
  CONSTRAINT `workerid_stafffk` FOREIGN KEY (`worker_id`) REFERENCES `historical_staff` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
