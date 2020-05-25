DROP SCHEMA IF EXISTS `project_management`;

CREATE SCHEMA `project_management`;

use `project_management`;

SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(128) NOT NULL,
  `status` enum('Not Started','In Progress','Completed') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `planned_end_date` datetime NOT NULL,
  `last_update_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(256) DEFAULT 'No comment',
  `task_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

   UNIQUE KEY (`activity_name`),

  KEY `FK_TASK_ID_idx` (`task_id`),

  CONSTRAINT `FK_TASK`
  FOREIGN KEY (`task_id`)
  REFERENCES `task` (`id`)

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO
`activity` (`activity_name`, `status`, `start_date`, `planned_end_date`, `last_update_date`, `comment`, `task_id`)
VALUES
('setup project', 'Completed', '2020-05-10 17:42:00', '2020-05-10 18:00:00',
'2020-05-10 17:55:00', DEFAULT, 1),
('travis ci integration', 'Completed', '2020-05-10 18:10:00', '2020-05-10 18:30:00',
'2020-05-10 18:27:00', DEFAULT, 1),
('setup config', 'Completed', '2020-05-11 16:27:00', '2020-05-11 17:30:00',
'2020-05-11 17:45:00', 'WebMvc, Dispatcher Servlet context, Data source', 1),
('creating activity entity', 'Completed', '2020-05-12 17:42:00', '2020-05-12 18:00:00',
'2020-05-12 18:16:00', DEFAULT, 2),
('creating dao repository interface', 'Completed', '2020-05-12 18:16:00', '2020-05-12 18:45:00',
'2020-05-12 18:32:00', DEFAULT, 3),
('creating activity dao interface', 'Completed', '2020-05-12 18:32:00', '2020-05-12 18:40:00',
'2020-05-12 18:45:00', DEFAULT, 3);


DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) NOT NULL,
  `priority` enum('High','Medium','Low') NOT NULL,
  `status` enum('Not Started','In Progress','Completed') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(256) DEFAULT 'No comment',
  `project_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),

  UNIQUE KEY (`task_name`),

  KEY `FK_PROJECT_ID_idx` (`project_id`),

  CONSTRAINT `FK_PROJECT`
  FOREIGN KEY (`project_id`)
  REFERENCES `project` (`id`)

  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO
`task` (`task_name`, `priority`, `status`, `start_date`, `last_update_date`, `comment`, `project_id`)
VALUES
('setup project', 'High', 'Completed', '2020-05-10 17:42:00',
'2020-05-11 17:45:00', 'setup project, setup config', 1),
('entity layer', 'High', 'In Progress', '2020-05-12 17:42:00',
'2020-05-12 18:16:00', 'creating entities for project', 1),
('dao layer', 'High', 'In Progress', '2020-05-12 18:16:00',
'2020-05-12 18:45:00', 'creating interfaces and classes for dao layer', 1);


DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(128) NOT NULL,
  `status` enum('Not Started','In Progress','Completed') NOT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `planned_end_date` datetime NOT NULL,
  `last_update_date` datetime NULL ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(256) DEFAULT 'No description',

  PRIMARY KEY (`id`),

  UNIQUE KEY (`project_name`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO
`project` (`project_name`, `status`, `start_date`, `planned_end_date`, `last_update_date`, `description`)
VALUES
('rest-api', 'In Progress', '2020-05-10 17:42:00', '2020-06-10 17:42:00',
'2020-05-11 17:45:00', 'rest-api for project management tool');


SET FOREIGN_KEY_CHECKS = 1;


