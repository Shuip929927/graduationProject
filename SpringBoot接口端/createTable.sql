CREATE TABLE `tb_admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_major` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department_id` int NOT NULL,
  `major_name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_major_department_id_idx` (`department_id`),
  CONSTRAINT `FK_major_department_id` FOREIGN KEY (`department_id`) REFERENCES `tb_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `tb_information` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL,
  `title` text,
  `author` varchar(45) DEFAULT NULL,
  `createOn` datetime DEFAULT NULL,
  `content` text,
  `isDelete` int DEFAULT '0',
  `hits` varchar(45) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_user` (
  `account` varchar(10) NOT NULL,
  `password` varchar(45) NOT NULL,
  `gender` int DEFAULT NULL,
  `name` varchar(10) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `departmentId` int NOT NULL,
  `majorId` int NOT NULL,
  `classes` int NOT NULL,
  `power` varchar(45) NOT NULL DEFAULT '1' COMMENT '    /*\\n     *  1 代表没有参加任何学生组织的学生\\n     *  2 代表学生组织成员\\n     *  3 代表学生组织负责人\\n     *  4 代表学生会主席团\\n     *  5 代表老师\\n     *  6 代表管理员\\n     * */',
  PRIMARY KEY (`account`),
  KEY `FK_department_id_idx` (`departmentId`),
  KEY `FK_user_major_id_idx` (`majorId`),
  CONSTRAINT `FK_user_department_id` FOREIGN KEY (`departmentId`) REFERENCES `tb_department` (`id`),
  CONSTRAINT `FK_user_major_id` FOREIGN KEY (`majorId`) REFERENCES `tb_major` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_organization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(45) DEFAULT NULL,
  `person_in_charge` varchar(45) DEFAULT NULL,
  `person_id` varchar(10) DEFAULT NULL,
  `number_of_people` int DEFAULT '0',
  `description` text,
  `isEnroll` varchar(45) DEFAULT '0' COMMENT '    /*\n     * 0 代表纳新 进行中\n     * 1 代表活动结束\n     * */',
  PRIMARY KEY (`id`),
  KEY `organization_id_and_name` (`id`),
  KEY `FK_organization_user_id_idx` (`person_id`),
  CONSTRAINT `FK_organization_user_id` FOREIGN KEY (`person_id`) REFERENCES `tb_user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int DEFAULT NULL,
  `activity_name` text,
  `activity_create_time` datetime DEFAULT NULL,
  `activity_start_time` datetime DEFAULT NULL,
  `activity_description` text,
  `activity_state` int DEFAULT '0' COMMENT '    /*\n    * 0 代表活动 进行中\n    * 1 代表活动结束\n    * 2 代表活动长期有效\n    * */',
  `isDelete` varchar(45) DEFAULT '0' COMMENT '    /*\n     * 0 代表活动存在\n     * 1 代表活动删除\n     * */',
  PRIMARY KEY (`id`),
  KEY `FK_activities_organization_id_idx` (`organization_id`),
  CONSTRAINT `FK_actvities_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `tb_organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_organization_enroll` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  `organization_enroll_state` int DEFAULT '1' COMMENT '    /*\n     *  不存在 代表未报名，\n     *  0 代表 审核不通过 ，\n     *  1 代表已经报名 但未审核 ，\n     *  2 代表通过审核\n     * */',
  PRIMARY KEY (`id`),
  KEY `FK_enroll_organization_id_idx` (`organization_id`),
  KEY `FK_organization_enroll_user_id_idx` (`user_id`),
  CONSTRAINT `FK_enroll_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `tb_organization` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_organization_enroll_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activities_enroll` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) DEFAULT NULL,
  `activity_id` int DEFAULT NULL,
  `activity_enroll_state` int DEFAULT '0' COMMENT '    /*\n     *  不存在 代表未报名，\n     *  0 代表 未报名 ，\n     *  1 代表已经报名 但未审核 ，\n     *  2 代表通过审核\n     * */',
  PRIMARY KEY (`id`),
  KEY `FK_enroll_activity_id_idx` (`activity_id`),
  KEY `FK_activities_enroll_user_id_idx` (`user_id`),
  CONSTRAINT `FK_activities_enroll_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`account`),
  CONSTRAINT `FK_enroll_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `tb_activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



