CREATE DATABASE IF NOT EXISTS service DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE service;
SET FOREIGN_KEY_CHECKS=0;
CREATE TABLE IF NOT EXISTS `user_permission` (
  `permission_id` int(11) NOT NULL,
  `permission_name` varchar(45) DEFAULT NULL,
  `permission_path` varchar(200) DEFAULT NULL COMMENT '页面地址，防止用户直接输入地址进入',
  `permission_desc` varchar(60) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_permission
-- ----------------------------
INSERT INTO `user_permission` VALUES ('100001', 'user', '', '用户管理', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('100002', 'device', '', '设备管理', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('100003', 'ad', '', '目录管理', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200000', 'recording', '', '录播', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200001', 'viewclass', '', '巡课', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200002', 'hhrecplan', '', '录像计划', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200003', 'liveplan', '', '直播计划', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200004', 'resource', '', '资源管理', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200005', 'hhrec_syslog', '', '日志管理', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200006', 'syssetting', '', '系统设置', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400001', 'foreground', '/', '前台', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400002', 'video', '/modules/space/action/view_channel.php', '视频', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400003', 'course', '/modules/space/action/coursemy_list.php', '课程', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400004', 'album', '/modules/space/action/my_album.php', '专辑', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400005', 'albumCreate', '/modules/space/action/create_album.php', '创建专辑', '400004') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400006', 'collect', '/modules/space/action/user_videos.php', '收藏', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400007', 'notes', '/modules/space/action/my_notes.php', '笔记', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400008', 'errQuestion', '/modules/space/action/err_question.php', '错题本', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400009', 'activity', '/modules/space/action/myactivity.php', '评课', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400010', 'courseCenter', '/modules/space/action/course_issuemanager.php', '课程中心', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400011', 'staffroom', '/modules/space/action/staffroom_list.php', '教研活动', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400012', 'videoManager', '/modules/space/action/video_manager.php', '视频管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400013', 'courseManager', '/modules/space/action/coursemanager_list.php', '课程管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400014', 'activityManager', '/modules/space/action/activity_manager.php', '优课评比', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400015', 'liveManager', '/modules/space/action/zhibo_list.php', '直播管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400016', 'masterManager', '/modules/space/action/mastermanager.php', '名师管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400017', 'schoolManager', '/modules/space/action/school_manager.php', '学校管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400018', 'infoManager', '/modules/infos/action/info_manager.php', '公告管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400019', 'reports', '/modules/space/action/reports.php', '平台统计', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400020', 'meetingManager', '/modules/space/action/meetingmanager.php', '互动管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400021', 'videoUpload', '/video_upload', '上传视频', '400002') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400022', 'staffroomCreate', '/modules/space/action/staffroom_editor.php', '创建教研室', '400011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400023', 'staffroomDelete', '/modules/space/action/staffroom_list.php?option=delete', '批量删除', '400011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400100', 'background', '/admin_cp/index.php', '后台', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400101', 'sysManager', '/admin_cp/navigate.php', '系统管理', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400102', 'navigate', '/admin_cp/navigate.php', '导航菜单', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400103', 'sysSetting', '/admin_cp/portal.php', '系统设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400104', 'emailSetting', '/admin_cp/email_settings.php', '邮箱设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400105', 'authority', '/admin_cp/authority.php', '授权设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400106', 'homeBannerManager', '/admin_cp/home_banner_manager.php', '首页轮播', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400107', 'homeBannerUpload', '/admin_cp/upload_home_banners.php', '上传轮播图', '400106') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400108', 'videoSetting', '/admin_cp/video_setting.php', '视频设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400115', 'contentManager', '/admin_cp/category.php', '内容管理', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400116', 'videoCategory', '/admin_cp/category.php', '视频分类', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400117', 'albumCategory', '/admin_cp/collection_category.php', '专辑分类', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400118', 'ykCategory', '/admin_cp/yk_category.php', '优课分类', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400119', 'chapterManager', '/admin_cp/chapters_manager.php', '教材章节管理', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400120', 'techKnowledgeManager', '/admin_cp/tech_knowledge_manager.php', '知识点管理', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400121', 'commentManager', '/admin_cp/comments.php', '评论管理', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400122', 'passManager', '/admin_cp/parent_server_manager.php', '上报管理', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400123', 'passSetting', '/admin_cp/parent_server_manager.php', '上报配置', '400122') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400124', 'passQueue', '/admin_cp/org_video_queue_manager.php', '上报队列', '400122') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400125', 'videoPush', '/admin_cp/video_push_manager.php', '视频上报', '400122') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400126', 'receiveSetting', '/admin_cp/receiption_setting.php', '接收设置', '400122') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400127', 'nginxSetting', '/admin_cp/nginx_setting.php', '流媒体服务配置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400128', 'scoreTemplate', '/admin_cp/score_template.php', '评审模板管理', '400115') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400129', 'schoolList', '/admin_cp/school_manager.php', '学校设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400130', 'classSetting', '/admin_cp/class_setting.php', '课表设置', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500001', 'homepage', '', '首页', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500002', 'timetable', '', '在线课堂', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500003', 'classroom_management', '', '教学监管', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500005', 'system_settings', '', '教学排行榜', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500006', 'add_mcu', '', '通知', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500007', 'set_to_admin', '', '系统设置', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500009', 'myschedule', '', '我的课表', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('600014', 'class_patrol', '', '巡课', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('600015', 'vk', '', '微课', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('600016', 'cloud', '', '云课堂', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('600017', 'live_video', '', '直播', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('600018', 'device_control', '', '设备控制', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10001', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10002', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10003', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10004', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10005', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10006', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10007', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10008', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10009', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10010', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10015', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10016', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10017', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10018', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10019', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('10020', 'viewmodule', '', '查看模块', '0') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500010', '', '', '月历课表', '500002') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500011', '', '', '当天课程列表', '500002') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500012', 'lectureroom', '', '教师课程表', '500011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500013', 'lecture', '', '教室课程表', '500011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500014', 'lecture', '', '观看直播', '500011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500015', 'form', '', '预约', '500011') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500016', '', '', '基础设置', '500007') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500017', '', '', '用户管理', '500007') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500018', '', '', '地点管理', '500007') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('500019', '', '', '设备管理', '500007') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400024', 'document', '', '文档', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400025', 'documentManager', '', '文档管理', '400001') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400131', 'agencyManager', '', '机构管理', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400132', 'roleManager', '', '角色权限', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400133', 'userManager', '', '用户管理', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400134', 'userReview', '', '用户审核', '400100') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('400135', 'systemMonitor', '', '系统监控', '400101') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200029', 'userManager', '', '用户管理', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200030', 'deviceManager', '', '设备管理', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200031', 'placeManager', '', '地点管理', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);
INSERT INTO `user_permission` VALUES ('200032', 'onli', '', '数据统计', '200000') ON DUPLICATE KEY UPDATE permission_id= VALUES(permission_id);


CREATE TABLE IF NOT EXISTS `user_sys` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_name` varchar(45) DEFAULT NULL,
  `sys_desc` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- INSERT INTO `user_sys` VALUES ('2', 'hdevicecontroller', '设备集中管理控制系统') ON DUPLICATE KEY UPDATE sys_id=VALUES(sys_id);
-- INSERT INTO `user_sys` VALUES ('7', 'hresmanager', '教育资源应用平台') ON DUPLICATE KEY UPDATE sys_id=VALUES(sys_id);
INSERT INTO `user_sys` VALUES ('10', 'hinteractiveteaching', '远程互动教学系统') ON DUPLICATE KEY UPDATE sys_id=VALUES(sys_id);
-- INSERT INTO `user_sys` VALUES ('11', 'hcampusapp', '录播系统app') ON DUPLICATE KEY UPDATE sys_id=VALUES(sys_id);

CREATE TABLE IF NOT EXISTS `user_sys2permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_name` varchar(45) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_sys2permission
-- ----------------------------
INSERT INTO `user_sys2permission` VALUES ('13', 'hlocationmanager', '300001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('14', 'hlocationmanager', '300002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('15', 'hlocationmanager', '300003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('16', 'hlocationmanager', '300004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('25', 'hdss', '800006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('26', 'hdss', '800007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('27', 'hdss', '800008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('28', 'hdss', '800009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('29', 'hdss', '800010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('33', 'hdss', '800015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('34', 'hdss', '800016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('35', 'hdss', '800017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('37', 'hdss', '800019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('41', 'hresmanager', '400001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('42', 'hresmanager', '400002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('43', 'hresmanager', '400003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('44', 'hresmanager', '400004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('45', 'hresmanager', '400005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('46', 'hresmanager', '400006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('47', 'hresmanager', '400007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('48', 'hresmanager', '400008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('49', 'hresmanager', '400009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('50', 'hresmanager', '400010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('51', 'hresmanager', '400011') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('52', 'hresmanager', '400012') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('53', 'hresmanager', '400013') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('54', 'hresmanager', '400014') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('55', 'hresmanager', '400015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('56', 'hresmanager', '400016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('57', 'hresmanager', '400017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('58', 'hresmanager', '400018') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('59', 'hresmanager', '400019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('60', 'hresmanager', '400020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('61', 'hresmanager', '400021') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('62', 'hresmanager', '400022') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('63', 'hresmanager', '400023') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('64', 'hresmanager', '400100') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('65', 'hresmanager', '400101') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('66', 'hresmanager', '400102') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('67', 'hresmanager', '400103') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('68', 'hresmanager', '400104') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('69', 'hresmanager', '400105') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('70', 'hresmanager', '400106') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('71', 'hresmanager', '400107') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('72', 'hresmanager', '400108') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('79', 'hresmanager', '400115') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('80', 'hresmanager', '400116') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('82', 'hresmanager', '400117') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('83', 'hresmanager', '400118') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('84', 'hresmanager', '400119') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('85', 'hresmanager', '400120') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('86', 'hresmanager', '400128') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('87', 'hresmanager', '400121') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('88', 'hresmanager', '400122') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('89', 'hresmanager', '400123') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('90', 'hresmanager', '400124') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('91', 'hresmanager', '400125') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('93', 'hresmanager', '400126') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('94', 'hresmanager', '400127') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('95', 'hmeeting', '900001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('96', 'hmeeting', '900002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('97', 'hmeeting', '900003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('99', 'hmeeting', '900005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('101', 'hinteractiveteaching', '500001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('102', 'hinteractiveteaching', '500002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('103', 'hinteractiveteaching', '500003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('105', 'hinteractiveteaching', '500005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('106', 'hinteractiveteaching', '500006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('107', 'hinteractiveteaching', '500007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('109', 'hinteractiveteaching', '500009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('110', 'hinteractiveteaching', '500010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('111', 'hinteractiveteaching', '500011') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('112', 'hinteractiveteaching', '500012') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('113', 'hinteractiveteaching', '500013') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('114', 'hinteractiveteaching', '500014') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('115', 'hinteractiveteaching', '500015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('116', 'hinteractiveteaching', '500016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('117', 'hinteractiveteaching', '500017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('118', 'hinteractiveteaching', '500018') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('119', 'hinteractiveteaching', '500019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('120', 'hinteractiveteaching', '500020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('121', 'hinteractiveteaching', '500021') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('122', 'hinteractiveteaching', '500022') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('123', 'hinteractiveteaching', '500023') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2010', 'hinteractiveteaching', '10010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('124', 'hcampusapp', '600001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('125', 'hcampusapp', '600002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('126', 'hcampusapp', '600003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('127', 'hcampusapp', '600004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('128', 'hcampusapp', '600005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('129', 'hcampusapp', '600006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('130', 'hcampusapp', '600007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('131', 'hcampusapp', '600008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('132', 'hcampusapp', '600009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('133', 'hcampusapp', '600010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('134', 'hcampusapp', '600011') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('135', 'hcampusapp', '600012') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('136', 'hcampusapp', '600013') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('137', 'hcampusapp', '600014') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('138', 'hcampusapp', '600015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('139', 'hcampusapp', '600016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('140', 'hcampusapp', '600017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('141', 'hcampusapp', '600018') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('142', 'hcampusapp', '600019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('143', 'hcampusapp', '600020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('144', 'hcampusapp', '600021') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('145', 'hcampusapp', '600022') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('146', 'hcampusapp', '600023') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('147', 'hcampusapp', '600024') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('148', 'hcampusapp', '600025') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('149', 'hcampusapp', '600026') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('150', 'hcampusapp', '600027') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('151', 'hcampusapp', '600028') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('152', 'hcampusapp', '600029') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('153', 'hcampusapp', '600030') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('154', 'hcampusapp', '600031') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('155', 'hcampusapp', '600033') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('156', 'hcampusapp', '600034') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('157', 'hcampusapp', '600035') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('158', 'hresmanager', '400129') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('159', 'hresmanager', '400130') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('160', 'hcampusapp', '600038') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('161', 'hcampusapp', '600039') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('162', 'hcampusapp', '600040') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('163', 'hcampusapp', '600041') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('164', 'hcampusapp', '600042') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('165', 'hdss', '800020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('166', 'hdss', '800021') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('167', 'hdss', '800022') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('168', 'hdss', '800023') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('169', 'hdss', '800024') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('170', 'hdss', '800025') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('171', 'hdevicecontroller', '200000') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('172', 'hdevicecontroller', '200001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('173', 'hdevicecontroller', '200002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('174', 'hdevicecontroller', '200003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('175', 'hdevicecontroller', '200004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('176', 'hdevicecontroller', '200005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('177', 'hdevicecontroller', '200006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('178', 'hdevicecontroller', '200007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('179', 'hdevicecontroller', '200008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('180', 'hdevicecontroller', '200009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('181', 'hdevicecontroller', '200010') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('182', 'hdevicecontroller', '200011') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('183', 'hdevicecontroller', '200012') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('184', 'hdevicecontroller', '200013') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('185', 'hdevicecontroller', '200014') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('186', 'hdevicecontroller', '200015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('187', 'hdevicecontroller', '200016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('188', 'hdevicecontroller', '200017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('189', 'hdevicecontroller', '200018') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('190', 'hdevicecontroller', '200019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('191', 'hdevicecontroller', '200020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('192', 'hdevicecontroller', '200021') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('193', 'hdevicecontroller', '200022') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('194', 'hdevicecontroller', '200023') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('195', 'hdevicecontroller', '200024') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('196', 'hdevicecontroller', '200025') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('197', 'hdevicecontroller', '200027') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('198', 'hdevicecontroller', '200028') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1001', 'hhtpm', '120100') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1002', 'hhtpm', '120101') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1003', 'hhtpm', '120102') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1004', 'hhtpm', '120103') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1005', 'hhtpm', '120104') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1006', 'hhtpm', '120105') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1007', 'hhtpm', '120106') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1008', 'hhtpm', '120107') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1009', 'hhtpm', '120108') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1010', 'hhtpm', '120109') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1011', 'hhtpm', '120110') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1012', 'hhtpm', '120111') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1013', 'hhtpm', '120112') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1014', 'hhtpm', '120113') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1015', 'hhtpm', '120200') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1016', 'hhtpm', '120201') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1017', 'hhtpm', '120202') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1018', 'hhtpm', '120203') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1019', 'hhtpm', '120204') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1020', 'hhtpm', '120205') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1021', 'hhtpm', '120206') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1022', 'hhtpm', '120300') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1023', 'hhtpm', '120301') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1024', 'hhtpm', '120302') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1025', 'hhtpm', '120303') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1026', 'hhtsm', '130101') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1027', 'hhtsm', '130102') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1028', 'hhtsm', '130103') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1029', 'hhtsm', '130104') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1030', 'hhtsm', '130105') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1031', 'hhtsm', '130106') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1032', 'hhtsm', '130107') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1033', 'hhtsm', '130108') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1034', 'hhtsm', '130109') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1035', 'hhtsm', '130110') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1036', 'hhtsm', '130111') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1037', 'hhtsm', '130100') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1038', 'hhtsm', '130200') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1039', 'hhtsm', '130201') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1040', 'hhtsm', '130202') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1041', 'hhtsm', '130203') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1042', 'hhtsm', '130204') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1043', 'hhtsm', '130205') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1044', 'hhtsm', '130206') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1045', 'hhtsm', '130207') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1046', 'hhtsm', '130208') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1047', 'hhtsm', '130209') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1048', 'hhtocs', '140100') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1049', 'hhtocs', '140101') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1050', 'hhtocs', '140102') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1051', 'hhtocs', '140103') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1052', 'hhtocs', '140104') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1053', 'hhtocs', '140105') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1054', 'hhtocs', '140106') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1055', 'hhtocs', '140107') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1056', 'hhtocs', '140108') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1057', 'hhtocs', '140109') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1058', 'hhtocs', '140110') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1059', 'hhtocs', '140111') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1060', 'hhtocs', '140200') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1061', 'hhtocs', '140201') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1062', 'hhtocs', '140202') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1063', 'hhtocs', '140203') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1064', 'hhtocs', '140204') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1065', 'hhtocs', '140205') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1066', 'hhtocs', '140206') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1067', 'hhtocs', '140207') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1068', 'hhtocs', '140208') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1069', 'hhtocs', '140209') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1070', 'hhtocs', '140300') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1071', 'hhtocs', '140301') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1072', 'hhtocs', '140302') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1073', 'hhtocs', '140303') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1074', 'hhtocs', '140304') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1075', 'hhtocs', '140305') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1076', 'hhtocs', '140306') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1077', 'hhtocs', '140307') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1078', 'hhtocs', '140308') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1079', 'hhtocs', '140309') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1080', 'hhtocs', '140310') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1081', 'hhtocs', '140311') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1082', 'hhtocs', '140312') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1083', 'hhtocs', '140313') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1084', 'hhtocs', '140314') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1085', 'hhtocs', '140315') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1086', 'hhtocs', '140316') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1087', 'hhtocs', '140317') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1088', 'hhtocs', '140318') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1089', 'hhtocs', '140319') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1090', 'hhtocs', '140320') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1091', 'hhtocs', '140400') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1092', 'hhtocs', '140401') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1093', 'hhtocs', '140402') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1094', 'hhtocs', '140403') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1095', 'hhtocs', '140404') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1096', 'hhtocs', '140405') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1097', 'hhtocs', '140406') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1098', 'hhtocs', '140407') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1099', 'hhtocs', '140408') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1100', 'hhtocs', '140409') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1101', 'hhtocs', '140410') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1102', 'hhtocs', '140411') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1103', 'hhtocs', '140500') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1104', 'hhtocs', '140501') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1105', 'hhtocs', '140502') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1106', 'hhtocs', '140600') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1107', 'hhtocs', '140601') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1108', 'hhtocs', '140602') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1109', 'hhtocs', '140603') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1110', 'hhtocs', '140604') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1111', 'hhtocs', '140605') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1112', 'hhtocs', '140606') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1113', 'hhtocs', '140607') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1114', 'hhtocs', '140608') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1115', 'hhtocs', '140609') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1116', 'hhtocs', '140610') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1117', 'hhtocs', '140611') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1118', 'hhtocs', '140612') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1119', 'hhtocs', '140613') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1120', 'hhtocs', '140614') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1121', 'hhtocs', '140700') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1122', 'hhtocs', '140701') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1123', 'hhtocs', '140702') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1124', 'hhtocs', '140703') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1125', 'hhtocs', '140800') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1126', 'hhtocs', '140801') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1127', 'hhtocs', '140802') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1128', 'hhtocs', '140803') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1129', 'hhtocs', '140804') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1130', 'hhtocs', '140805') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1131', 'hhtocs', '140806') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1132', 'hhtocs', '140807') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1133', 'hhtocs', '140808') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1134', 'hhtpm', '124000') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1135', 'hhtpm', '124001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1136', 'hhtpm', '124002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1137', 'hhtpm', '124003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1138', 'hhtpm', '124004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1139', 'hhtpm', '124005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1140', 'hhtpm', '124006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1141', 'hhtpm', '124007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1142', 'hhtpm', '124008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1143', 'hhtsm', '130210') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1144', 'hhtsm', '130300') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1145', 'hhtsm', '130301') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1146', 'hhtsm', '130302') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1147', 'hhtsm', '130303') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('1148', 'hhtsm', '130304') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2001', 'hdss', '10001') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2002', 'hdevicecontroller', '10002') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2003', 'hschedule', '10003') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2004', 'hlocationmanager', '10004') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2005', 'husermanager', '10005') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2006', 'hsecurity', '10006') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2007', 'hresmanager', '10007') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2008', 'hdevice', '10008') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2009', 'hmeeting', '10009') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2015', 'hlicence', '10015') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2016', 'hcourarrange', '10016') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2017', 'hcourchoose', '10017') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2018', 'harea', '10018') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2019', 'hresservice', '10019') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2020', 'hmenumanager', '10020') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2021', 'hhtsm', '130112') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2022', 'hresmanager', '400024') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2023', 'hresmanager', '400025') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2024', 'hresmanager', '400131') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2025', 'hresmanager', '400132') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2026', 'hresmanager', '400133') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2027', 'hresmanager', '400134') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_sys2permission` VALUES ('2028', 'hresmanager', '400135') ON DUPLICATE KEY UPDATE id=VALUES(id);

CREATE TABLE IF NOT EXISTS `user_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  `role_init` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '管理员', '1');
INSERT INTO `user_role` VALUES ('2', '领导', '1');
INSERT INTO `user_role` VALUES ('3', '教师', '1');
--INSERT INTO `user_role` VALUES ('4', '学生', '1');
-- INSERT INTO `user_role` VALUES ('5', '巡课', '1');

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` varchar(50) DEFAULT NULL COMMENT '内部流水',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `user_salt` varchar(50) DEFAULT NULL COMMENT '加密串',
  `user_type` tinyint(2) DEFAULT NULL COMMENT '用户类型： 0 超级管理员参考user_type 表',
  `user_realname` varchar(255) DEFAULT NULL COMMENT ' 真实姓名',
  `user_path` varchar(255) DEFAULT NULL COMMENT '头像存放路径',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '头像图片名称',
  `user_gender` tinyint(4) DEFAULT NULL COMMENT '性别 1-男 2-女 0-未知',
  `user_birthday` varchar(50) DEFAULT NULL COMMENT '出生日期',
  `user_mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `user_email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `user_address` varchar(255) DEFAULT NULL COMMENT '家庭住址',
  `user_num` varchar(60) DEFAULT NULL COMMENT '用户号码，如学籍号等',
  `user_regdate` varchar(50) DEFAULT NULL COMMENT '注册时间',
  `user_status` tinyint(4) DEFAULT NULL COMMENT '用户状态 0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用',
  `user_card` varchar(255) DEFAULT NULL COMMENT '用户卡号',
  `user_course` varchar(255) DEFAULT NULL COMMENT '用户学科',
  `user_info` varchar(300) DEFAULT NULL COMMENT '用户简介',
  `user_syn_cloud` int(11) DEFAULT '0' COMMENT '同步云平台状态 0 未导入，1导入',
  `user_isAdmin` int(11) DEFAULT NULL COMMENT '标识该用户是否是校级管理员，0 否，1 是',
  `user_itoken` varchar(255) DEFAULT NULL COMMENT 'i学的token，用于判断用户是否已注册',
  `user_hres` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `index_user_code` (`user_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1','AM2017000001','admin', 'f941d1d51b5bf705647fb739a640ad0b', '279842', '0', '管理员', null, null, "1", null, null, null, null,null, null, '1',null,null,null,0,1,null,null) ON DUPLICATE KEY UPDATE user_id='1';
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(11) NOT NULL,
  `type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
INSERT INTO `user_type` VALUES ('0', '超级管理员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('1', '市管理员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('2', '区管理员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('3', '校管理员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('4', '教委领导') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('5', '学科老师') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('6', '教研员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('7', '学校领导') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('8', '教导主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('9', '德育主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('10', '总务主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('11', '文艺体卫主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('12', '办公室主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('13', '保卫科长') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('14', '教研组长') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('15', '年级组长') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('16', '班主任') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('17', '老师') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('18', '学生') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('19', '家长') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('20', '安全保卫人员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('21', '宿舍管理人员') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('22', '电教老师') ON DUPLICATE KEY UPDATE id=VALUES(id);
INSERT INTO `user_type` VALUES ('23', '信息技术老师') ON DUPLICATE KEY UPDATE id=VALUES(id);

CREATE TABLE IF NOT EXISTS `user_user2role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_role2type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `agency` (
  `agency_id` int(11) NOT NULL AUTO_INCREMENT,
  `agency_name` varchar(50) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  `agency_level` int(11) DEFAULT NULL COMMENT '1 市 2 县 3 学校',
  PRIMARY KEY (`agency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `agency` VALUES ('1', '教育视频资源应用平台', '0', '1') ON DUPLICATE KEY UPDATE agency_id=VALUES(agency_id);

 CREATE TABLE IF NOT EXISTS `user2agency` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `agency_id` int(11) DEFAULT NULL,
   `user_id` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 INSERT INTO `user2agency` VALUES ('1', '1', '1') ON DUPLICATE KEY UPDATE id=VALUES(id);

CREATE TABLE IF NOT EXISTS `user_parent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_code` varchar(50) NOT NULL COMMENT '系统内部流水号，服务接口传送的家长编号',
  `namepy` varchar(50) DEFAULT NULL COMMENT '姓名拼音',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '家长姓名',
  `parent_path` varchar(255) DEFAULT NULL COMMENT '头像存放路径',
  `membership` tinyint(4) DEFAULT NULL COMMENT '成员关系（1父亲，2母亲,3其他）',
  `parent_mobile` varchar(50) DEFAULT NULL COMMENT '家长手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '家长邮箱',
  `is_guardian` tinyint(4) DEFAULT NULL COMMENT '是否是监护人（0否；1是）',
  `is_together` tinyint(4) DEFAULT NULL COMMENT '是否生活在一起（0否；1是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家长表';

CREATE TABLE IF NOT EXISTS `user_pc_relations` (
  `pc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '亲子关系，记录id',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '家长id',
  `student_code` varchar(50) DEFAULT NULL COMMENT ' 学生id',
  PRIMARY KEY (`pc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家长与学生关系表';

CREATE TABLE IF NOT EXISTS `user_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `student_code` varchar(50) NOT NULL COMMENT '系统内部流水',
  `student_num` varchar(50) DEFAULT NULL COMMENT '学籍号',
  `namepy` varchar(50) DEFAULT NULL COMMENT '姓名拼音',
  `realname` varchar(50) DEFAULT NULL COMMENT '学生姓名',
  `student_path` varchar(255) DEFAULT NULL COMMENT '头像存放路径',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别 1-男 2-女 0-未知',
  `readtype` tinyint(20) DEFAULT NULL COMMENT '就读方式（1走读；2住宿；3借宿；4其他）',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `status` tinyint(4) DEFAULT NULL COMMENT '在校状态(1在校；2离校;3已毕业)',
  `enter_year` varchar(50) DEFAULT NULL COMMENT '入学年度（2016）',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `enter_way` tinyint(4) DEFAULT NULL COMMENT '入学方式（1普通入学；2体育特招；3外校转入；4其他）',
  `address` varchar(255) DEFAULT NULL COMMENT '户口所在地',
  `birthday` varchar(50) DEFAULT NULL COMMENT '出生年月(2010-08-09)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

CREATE TABLE IF NOT EXISTS `user_teacher` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `teacher_code` varchar(50) NOT NULL COMMENT '系统内部流水号，服务接口传送的教师编号',
  `teacher_name` varchar(255) DEFAULT NULL COMMENT '教师姓名',
  `employeeno` varchar(50) NOT NULL COMMENT '教职工工号',
  `namepy` varchar(50) DEFAULT NULL COMMENT '姓名拼音',
  `teacher_path` varchar(255) DEFAULT NULL COMMENT '头像存放路径',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别 1-男 2-女 0-未知',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq号码',
  `idcode` varchar(50) DEFAULT NULL COMMENT '身份证号码',
  `birthday` varchar(50) DEFAULT NULL COMMENT '出生年月(2010-08-09)',
  `short_num` varchar(50) DEFAULT NULL COMMENT '短号',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `political` varchar(50) DEFAULT NULL COMMENT '政治面貌',
  `address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `work_date` varchar(50) DEFAULT NULL COMMENT '参工时间',
  `teach_date` varchar(50) DEFAULT NULL COMMENT '从教时间',
  `is_job` tinyint(4) DEFAULT NULL COMMENT '是否在职 （0否,1是）',
  `professional_title` tinyint(4) DEFAULT NULL COMMENT '职称（职业资格 1：三级教师，2：二级教师、3：一级教师:4：高级教师，5：正高级教师）',
  `stage_id` bigint(20) DEFAULT NULL COMMENT '教师所授课学段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师职工表';

CREATE TABLE IF NOT EXISTS `user_teacher2subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '教师流水',
  `subject_id` int(11) DEFAULT NULL COMMENT '科目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师与科目的关系表';

CREATE TABLE IF NOT EXISTS `user_teacher2type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '教师流水',
  `type_id` int(11) DEFAULT NULL COMMENT '教师职务id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师与职务的关系表';

CREATE TABLE IF NOT EXISTS `user_famous`(
  `teacher_id` varchar(32) NOT NULL COMMENT '教师id',
  `is_famous` tinyint(4) DEFAULT '0' COMMENT '是否是名师（0否, 1是）',
  `is_professor` tinyint(4) DEFAULT '0' COMMENT '是否是专家（0否, 1是）',
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师名师表';

CREATE TABLE IF NOT EXISTS `user_focus_fans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `focus_userid` bigint(20) DEFAULT NULL COMMENT '关注用户的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注粉丝表';

CREATE TABLE IF NOT EXISTS `user_sys_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `pm_name` varchar(255) DEFAULT NULL COMMENT '模块英文标识',
  `pm_desc` varchar(255) DEFAULT NULL COMMENT '模块中文名称',
  `pm_x` bigint(20) DEFAULT NULL COMMENT '模块的x坐标位置',
  `pm_y` varchar(50) DEFAULT NULL COMMENT '模块纵坐标的位置',
  `pm_length` varchar(50) DEFAULT NULL COMMENT '模块图片的长',
  `pm_width` varchar(50) DEFAULT NULL COMMENT '模块图片的宽',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户系统位置表';

CREATE TABLE IF NOT EXISTS `user_sys_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `m_name` varchar(255) DEFAULT NULL COMMENT '模块英文标识',
  `m_desc` varchar(255) NOT NULL COMMENT '模块中文名称',
  `m_status` varchar(50) DEFAULT NULL COMMENT '各模块安装状态，0表示未安装，1表示已安装',
  `m_time` varchar(200) DEFAULT NULL COMMENT '安装时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统安装状态表';

-- ----------------------------
-- Table structure for user_role2permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_role2permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role2permission
-- ----------------------------
INSERT INTO `user_role2permission` VALUES ('1', '1', '500001');
INSERT INTO `user_role2permission` VALUES ('2', '1', '500002');
INSERT INTO `user_role2permission` VALUES ('3', '1', '500003');
INSERT INTO `user_role2permission` VALUES ('4', '1', '500004');
INSERT INTO `user_role2permission` VALUES ('5', '1', '500005');
INSERT INTO `user_role2permission` VALUES ('6', '1', '500006');
INSERT INTO `user_role2permission` VALUES ('7', '1', '500007');
INSERT INTO `user_role2permission` VALUES ('8', '1', '500008');
INSERT INTO `user_role2permission` VALUES ('9', '1', '500009');
INSERT INTO `user_role2permission` VALUES ('10', '1', '500010');
INSERT INTO `user_role2permission` VALUES ('11', '1', '500011');
INSERT INTO `user_role2permission` VALUES ('12', '1', '500012');
INSERT INTO `user_role2permission` VALUES ('13', '1', '500013');
INSERT INTO `user_role2permission` VALUES ('14', '1', '500014');
INSERT INTO `user_role2permission` VALUES ('15', '1', '500015');
INSERT INTO `user_role2permission` VALUES ('16', '1', '500016');
INSERT INTO `user_role2permission` VALUES ('17', '1', '500017');
INSERT INTO `user_role2permission` VALUES ('18', '1', '500018');
INSERT INTO `user_role2permission` VALUES ('19', '1', '500019');
INSERT INTO `user_role2permission` VALUES ('20', '1', '10010');
INSERT INTO `user_role2permission` VALUES ('21', '2', '500001');
INSERT INTO `user_role2permission` VALUES ('22', '2', '500002');
INSERT INTO `user_role2permission` VALUES ('23', '2', '500003');
INSERT INTO `user_role2permission` VALUES ('24', '2', '500005');
INSERT INTO `user_role2permission` VALUES ('25', '2', '500006');
INSERT INTO `user_role2permission` VALUES ('26', '2', '500010');
INSERT INTO `user_role2permission` VALUES ('27', '2', '500011');
INSERT INTO `user_role2permission` VALUES ('28', '2', '500012');
INSERT INTO `user_role2permission` VALUES ('29', '2', '500013');
INSERT INTO `user_role2permission` VALUES ('30', '2', '500014');
INSERT INTO `user_role2permission` VALUES ('32', '2', '10010');
INSERT INTO `user_role2permission` VALUES ('33', '3', '500001');
INSERT INTO `user_role2permission` VALUES ('34', '3', '500002');
INSERT INTO `user_role2permission` VALUES ('36', '3', '500005');
INSERT INTO `user_role2permission` VALUES ('37', '3', '500006');
INSERT INTO `user_role2permission` VALUES ('38', '3', '500009');
INSERT INTO `user_role2permission` VALUES ('39', '3', '500010');
INSERT INTO `user_role2permission` VALUES ('40', '3', '500011');
INSERT INTO `user_role2permission` VALUES ('41', '3', '500012');
INSERT INTO `user_role2permission` VALUES ('42', '3', '500013');
INSERT INTO `user_role2permission` VALUES ('43', '3', '500014');
INSERT INTO `user_role2permission` VALUES ('44', '3', '500015');
INSERT INTO `user_role2permission` VALUES ('45', '3', '10010');

CREATE TABLE IF NOT EXISTS `classification_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL COMMENT '显示顺序',
  `is_show` int(11) DEFAULT NULL COMMENT '是否显示   0 不显示  1 显示',
  `season_id` int(11) DEFAULT NULL COMMENT '所属学段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `classification_subject` VALUES ('65', '语文', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('66', '数学', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('67', '英语', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('68', '物理', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('69', '化学', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('70', '生物', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('71', '政治', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('72', '历史', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('73', '地理', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('74', '艺术', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('75', '体育', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('76', '信息', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('77', '通用', '', '0', '1', '4');
INSERT INTO `classification_subject` VALUES ('78', '体育与健康', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('79', '信息技术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('80', '公共卫生教育', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('81', '劳动技术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('82', '化学', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('83', '历史', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('84', '地理', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('85', '心理健康教育', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('86', '思想政治', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('87', '数学', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('88', '物理', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('89', '生物', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('90', '社会', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('91', '科学', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('92', '美术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('93', '艺术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('94', '艺术欣赏美术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('95', '艺术欣赏音乐', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('96', '英语', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('97', '语文', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('98', '通用技术', '', '0', '1', '5');
INSERT INTO `classification_subject` VALUES ('99', '音乐', '', '0', '1', '5');

