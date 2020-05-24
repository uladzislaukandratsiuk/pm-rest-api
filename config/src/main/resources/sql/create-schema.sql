DROP SCHEMA IF EXISTS `project_management`;

CREATE SCHEMA `project_management`;

use `project_management`;

SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(128) NOT NULL,
  `status` enum('Active','Pending','Cancelled','Suspend') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `planned_end_date` datetime NOT NULL,
  `actual_end_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(256) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_TASK_ID_idx` (`task_id`),

  CONSTRAINT `FK_TASK`
  FOREIGN KEY (`task_id`)
  REFERENCES `task` (`id`)

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) NOT NULL,
  `priority` enum('High','Medium','Low') NOT NULL,
  `status` enum('Active','Pending','Cancelled','Suspend') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `planned_end_date` datetime NOT NULL,
  `actual_end_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(256) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_PROJECT_ID_idx` (`project_id`),

  CONSTRAINT `FK_PROJECT`
  FOREIGN KEY (`project_id`)
  REFERENCES `project` (`id`)

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(128) NOT NULL,
  `status` enum('Active','Pending','Cancelled','Suspend') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `planned_end_date` datetime NOT NULL,
  `actual_end_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(256) DEFAULT NULL,

  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;


