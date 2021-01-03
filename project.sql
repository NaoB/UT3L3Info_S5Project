-- Adminer 4.7.7 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS `project`;
CREATE DATABASE `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `project`;

DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `moment` datetime NOT NULL,
  `sensor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `value` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sensor` (`sensor`),
  CONSTRAINT `data_ibfk_1` FOREIGN KEY (`sensor`) REFERENCES `sensor` (`name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `sensor`;
CREATE TABLE `sensor` (
  `name` varchar(255) NOT NULL,
  `sensor_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `building` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `floor` int NOT NULL,
  `location` varchar(255) NOT NULL,
  `connected` tinyint NOT NULL DEFAULT '0',
  `value` float DEFAULT NULL,
  `min` float NOT NULL,
  `max` float NOT NULL,
  PRIMARY KEY (`name`),
  KEY `sensor_type` (`sensor_type`),
  KEY `building` (`building`),
  CONSTRAINT `sensor_ibfk_1` FOREIGN KEY (`sensor_type`) REFERENCES `sensor_type` (`name`) ON DELETE CASCADE,
  CONSTRAINT `sensor_ibfk_2` FOREIGN KEY (`building`) REFERENCES `building` (`name`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `sensor_type`;
CREATE TABLE `sensor_type` (
  `name` varchar(255) NOT NULL,
  `unit` varchar(255) NOT NULL,
  `min` float NOT NULL,
  `max` float NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `sensor_type` (`name`, `unit`, `min`, `max`) VALUES
('AIRCOMPRIME',	'm3/h',	0,	5),
('EAU',	'm3',	0,	10),
('ELECTRICITE',	'kWh',	10,	500),
('TEMPERATURE',	'°C',	17,	22);

-- 2021-01-03 16:17:54
