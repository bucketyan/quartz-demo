CREATE TABLE `schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `class_name` varchar(200) DEFAULT NULL COMMENT '运行的job类路径，例：com.bsi.bdc.quartzdemo.job.HelloJob',
  `cron_expression` varchar(50) DEFAULT NULL COMMENT 'job的运行周期 cron表达式',
  `job_name` varchar(100) DEFAULT NULL COMMENT 'job名称',
  `job_group` varchar(100) DEFAULT NULL COMMENT 'job分组',
  `trigger_name` varchar(100) DEFAULT NULL COMMENT '调度名称',
  `trigger_group` varchar(100) DEFAULT NULL COMMENT '调度分组',
  `pause` BOOLEAN DEFAULT FALSE COMMENT '是否暂停，默认false，即tinyint 0',
  `enable` BOOLEAN DEFAULT TRUE COMMENT '是否允许，默认true，即tinyint 1',
  `description` varchar(500) DEFAULT NULL COMMENT 'job描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `i_schedule_job_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
