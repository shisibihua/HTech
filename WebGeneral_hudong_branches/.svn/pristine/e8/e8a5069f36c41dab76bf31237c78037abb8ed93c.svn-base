CREATE DATABASE IF NOT EXISTS  meeting DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE meeting;
SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `tech_activity` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  `activity_style_id` int(11) DEFAULT NULL COMMENT '活动模式',
  `period_id` int(11) DEFAULT NULL COMMENT '学段id',
  `period_name` varchar(50) DEFAULT NULL COMMENT '学段名称',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级id',
  `grade_name` varchar(50) DEFAULT NULL COMMENT '年级名称',
  `subject_id` int(11) DEFAULT NULL COMMENT '学科id',
  `subject_name` varchar(50) DEFAULT NULL COMMENT '学科名称',
  `host_id` int(11) DEFAULT NULL COMMENT '主讲人id',
  `host_name` varchar(50) DEFAULT NULL COMMENT '主讲人名称',
  `room_id` int(11) DEFAULT NULL COMMENT '主讲教室',
  `room_name` varchar(50) DEFAULT NULL COMMENT '主讲教室名称',
  `room_students` int(11) DEFAULT NULL COMMENT '教室容纳人数',
  `class_id` int(11) DEFAULT NULL COMMENT '主讲教室听课班级',
  `class_name` varchar(50) DEFAULT NULL COMMENT '主讲教室听课班级名称',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `uuid` varchar(50) DEFAULT NULL COMMENT '辅助教师与课表活动对应id',
  `area_id` varchar(50) DEFAULT NULL COMMENT '地点名称对应id',
  `intro` text  DEFAULT NULL COMMENT '活动简介',
  `send_email` int(5) DEFAULT NULL COMMENT '是否发送邮件  0:不发送；1:发送',
  `send_message` int(5) DEFAULT NULL COMMENT '是否发送短信 0:不发送；1:发送 ',
  `notice_time` varchar(100) DEFAULT NULL COMMENT '通知时间',
  `notice_type` varchar(20) DEFAULT NULL COMMENT '通知類型',
  `mcu_id` varchar(50) DEFAULT NULL COMMENT 'mcu',
  `mcu_ip` varchar(50) DEFAULT NULL COMMENT 'mcuip',
  `mcu_type` varchar(20) DEFAULT NULL COMMENT 'mcu类型',
  `cloud_live` int(5) DEFAULT NULL COMMENT '是否云直播',
  `status` int(5) DEFAULT NULL COMMENT '课程状态',
  `invalid_remark` text  DEFAULT NULL COMMENT '课程无效备注',
  `user_id` int(11) DEFAULT NULL COMMENT '操作用户',
  `is_del` int(5) DEFAULT NULL COMMENT '是否删除:1:删除；0:未删',
  `room_addr` varchar(50) DEFAULT NULL COMMENT '保存省、市、区、学校id，用“-”分割',
  `update_time` TIMESTAMP NOT NULL COMMENT '最后修改时间',
  `spare_a` varchar(50) DEFAULT NULL COMMENT '对应课节id',
  `spare_b` varchar(50) DEFAULT NULL COMMENT '备用字段',
  `spare_c` varchar(50) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`activity_id`),
  UNIQUE KEY `activity_id` (`activity_id`),
  index `uuid`(`uuid`),
  index `room_addr`(`room_addr`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `tech_assteach_r_activity` (
  `assteach_r_activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `assist_teacher_id` int(11) DEFAULT NULL COMMENT '辅助教师',
  `assist_teacher_name` varchar(50) DEFAULT NULL COMMENT '辅助教师名称',
  `accept_room_id` int(11) DEFAULT NULL COMMENT '接收教室',
  `accept_room_name` varchar(50) DEFAULT NULL COMMENT '接收教室名称',
  `accept_class_id` int(11) DEFAULT NULL COMMENT '接收教室听课班级',
  `accept_class_name` varchar(50) DEFAULT NULL COMMENT '接收教室听课班级名称',
  `accept_room_addr` varchar(50) DEFAULT NULL COMMENT '保存省、市、区、学校id，用“-”分割',
  `area_id` varchar(50) DEFAULT NULL COMMENT '地点对应id',
  `room_students` int(11) DEFAULT NULL COMMENT '教室容纳人数',
  `uuid` varchar(50) DEFAULT NULL COMMENT '与教学活动对应uuid',
  PRIMARY KEY (`assteach_r_activity_id`),
  UNIQUE KEY `assteach_r_activity_id` (`assteach_r_activity_id`),
  index `uuid`(`uuid`),
  index `accept_room_addr`(`accept_room_addr`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_area` (
  `area_id` varchar(50) NOT NULL,
  `province` varchar(50) DEFAULT NULL COMMENT '省名称',
  `city` varchar(50) DEFAULT NULL COMMENT '市名称',
  `county` varchar(50) DEFAULT NULL COMMENT '县、区名称',
  `school` varchar(50) DEFAULT NULL COMMENT '学校名称',
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_id` (`area_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `tech_live_stream` (
  `live_stream_id` int(11) NOT NULL AUTO_INCREMENT,
  `stream_addr` varchar(500) DEFAULT NULL COMMENT '直播地址',
  `activity_id` int(11) DEFAULT NULL COMMENT '教学活动',
  `spare_a` varchar(50) DEFAULT NULL COMMENT '备用字段',
  `spare_b` varchar(50) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`live_stream_id`),
  UNIQUE KEY `live_stream_id` (`live_stream_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_subject` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '学科名称',
  `grade_id` int(11) DEFAULT NULL COMMENT '年级',
  `period_id` int(11) DEFAULT NULL COMMENT '学段',
  `spare` varchar(50) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`subject_id`),
  UNIQUE KEY `subject_id` (`subject_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP  table  if  EXISTS  tech_activity_style;
CREATE TABLE IF NOT EXISTS `tech_activity_style` (
  `activity_style_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '活动类型名称',
  `spare` varchar(50) DEFAULT NULL COMMENT '教学活动',
  PRIMARY KEY (`activity_style_id`),
  UNIQUE KEY `activity_style_id` (`activity_style_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
--  插入基本数据
INSERT INTO `tech_activity_style`(`name`) VALUES ('教学模式');

CREATE TABLE IF NOT EXISTS `tech_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '评论用户id',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `update_time` datetime NOT NULL COMMENT '评论时间',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动id',
  `replay_id` int(11) DEFAULT NULL COMMENT '评论id，发布的评论默认为0',
  `visible` int(5) DEFAULT NULL COMMENT '评论是否可见，0不可见，1可见，默认为1',
  `spare_a` varchar(50) DEFAULT NULL COMMENT '备注字段a',
  `spare_b` varchar(50) DEFAULT NULL COMMENT '备注字段b',
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_id` (`comment_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_comment_check` (
  `check_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论id',
  `check_time` datetime NOT NULL COMMENT '审核时间',
  `status` int(5) DEFAULT NULL COMMENT '评论审核状态，0未审核，已审核',
  `spare` varchar(50) DEFAULT NULL COMMENT '备注字段',
  PRIMARY KEY (`check_id`),
  UNIQUE KEY `check_id` (`check_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_oper_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `time` TIMESTAMP NOT NULL COMMENT '日志时间',
  `type` int(5) DEFAULT NULL COMMENT '日志类型,0:操作日志，1:系统日志',
  `content` varchar(500) DEFAULT NULL COMMENT '日志内容',
  `modulename` varchar(50) DEFAULT NULL COMMENT '模块功能',
  `spare_a` varchar(50) DEFAULT NULL COMMENT '备注字段a',
  `spare_b` varchar(50) DEFAULT NULL COMMENT '备注字段b',
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_id` (`log_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `content` varchar(500) DEFAULT NULL COMMENT '发布内容',
  `name` varchar(50) DEFAULT NULL COMMENT '通知名称',
  `pub_time` datetime NOT NULL COMMENT '发布通知时间',
  `pub_user_id` int(11) DEFAULT NULL COMMENT '发布人id',
  `status` int(5) DEFAULT NULL COMMENT '通知状态，0未读，1已读',
  `type` int(11) DEFAULT NULL COMMENT '通知类型',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动id',
  `spare_a` varchar(50) DEFAULT NULL COMMENT '备注字段a',
  `spare_b` varchar(50) DEFAULT NULL COMMENT '备注字段b',
  PRIMARY KEY (`notice_id`),
  UNIQUE KEY `notice_id` (`notice_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `tech_schedule` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '作息策略id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `isEnable` int(5) DEFAULT NULL COMMENT '是否启用，0：启用；1：禁用',
  `spare` varchar(50) DEFAULT NULL COMMENT '备注字段',
  PRIMARY KEY (`schedule_id`),
  UNIQUE KEY `schedule_id` (`schedule_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cur_classtime
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cur_classtime` (
  `classtime_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `schedule_id` int(11) DEFAULT NULL  COMMENT '策略id',
  `week` int(11) DEFAULT NULL COMMENT '星期',
  `section` int(11) DEFAULT NULL COMMENT '节次',
  `start_time` varchar(20) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) DEFAULT NULL COMMENT '结束时间',
  `type` int(11) DEFAULT NULL COMMENT '时间类型 1早自习,2上午，3下午，4晚自习',
  `section_name` varchar(50) DEFAULT NULL COMMENT '节次名称',
  PRIMARY KEY (`classtime_id`),
  UNIQUE KEY `classtime_id` (`classtime_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT='作息';

-- ----------------------------
-- Table structure for cur_ploycriteria
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cur_criteria` (
  `criteria_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL COMMENT '修改时间',
  `user_id` int(11) NOT NULL,
  `morning` int(1) DEFAULT NULL COMMENT '早自习节数',
  `am` int(1) DEFAULT NULL COMMENT '上午节数',
  `pm` int(1) DEFAULT NULL COMMENT '下午节数',
  `night` int(1) DEFAULT NULL COMMENT '晚自习节数',
  `start_time` varchar(20) DEFAULT NULL COMMENT '策略开始月份',
  `end_time` varchar(20) DEFAULT NULL COMMENT '策略结束月份',
  PRIMARY KEY (`criteria_id`),
  UNIQUE KEY `criteria_id` (`criteria_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cur_term
-- ----------------------------
CREATE TABLE IF NOT EXISTS `cur_term` (
  `term_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `schoolyear_id` int(11) DEFAULT NULL COMMENT '学年id',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_time` TIMESTAMP NOT NULL COMMENT '更新时间',
  `startdate` varchar(225) DEFAULT NULL COMMENT '开始时间',
  `enddate` varchar(225) DEFAULT NULL COMMENT '结束时间',
  `type` int(11) DEFAULT NULL COMMENT '学期',
    PRIMARY KEY (`term_id`),
   UNIQUE KEY `term_id` (`term_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cur_term
-- ----------------------------

DROP  table  if  EXISTS  tech_period;
CREATE TABLE IF NOT EXISTS `tech_period` (
  `period_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`period_id`),
   UNIQUE KEY `period_id` (`period_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

insert into tech_period (period_id,name) VALUES (1,"幼教"),(2,"小学"),(3,"初中"),(4,"高中"),(5,"通用");

-- ----------------------------
-- Table structure for tech_schoolyear
-- ----------------------------
CREATE TABLE IF NOT EXISTS `tech_schoolyear` (
  `schoolyear_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '学年名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `startdate` varchar(225) DEFAULT NULL COMMENT '开始时间',
  `enddate` varchar(225) DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`schoolyear_id`),
   UNIQUE KEY `schoolyear_id` (`schoolyear_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;