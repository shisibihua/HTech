CREATE DATABASE IF NOT EXISTS service DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci##
  USE service##
SET FOREIGN_KEY_CHECKS=0##

-- ----------------------------
-- Table structure for cmdcode_update
-- ----------------------------
CREATE TABLE IF NOT EXISTS`device_cmdcode_update` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字段id',
  `dspec_id` int(11) DEFAULT NULL COMMENT '设备型号表中的设备id',
  `cmdcode_update` varchar(50) DEFAULT NULL COMMENT '命令参数更新标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT = '设备命令更新表'##

-- ----------------------------
-- Records of cmdcode_update
-- ----------------------------
INSERT INTO `device_cmdcode_update` VALUES ('1', '6', 'adb38c7d-882f-11e5-a218-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('2', '4', 'f3038de3-c7f7-11e4-aeb1-549f3511f8e41') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('3', '7', 'd750b261-8464-11e5-8e28-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('4', '8', 'd75c4ae7-8464-11e5-8e28-549f3511f8e4') ON DUPLICATE KEY UPDATE  id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('6', '10', 'd57d66d9-835e-11e5-8e28-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('7', '9', 'adcdbcbb-882f-11e5-a218-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('8', '12', '31f95c94-7960-11e5-9dd7-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('9', '13', '861c526b-382f-11e5-a486-28d2440ba0c2') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('12', '16', '64a2faad-32a1-11e6-906d-6002921dd86f') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('13', '18', '692af017-32a1-11e6-906d-6002921dd86f') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('14', '19', '7baaf038-378c-11e6-a02b-6002921dd86f') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('15', '19', '31f95c94-7960-11e5-9dd7-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_cmdcode_update` VALUES ('16', '37', '31f95c94-7960-11e5-9dd7-549f3511f8e4') ON DUPLICATE KEY UPDATE id=VALUES(id)##

CREATE TABLE  IF NOT EXISTS`device_type` (
  `dtype_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备类型id',
  `dtype_name` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型英文大写名称',
  `dtype_desc` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型英文小写名称',
  `dtype_name_cn` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型中文描述',
  PRIMARY KEY (`dtype_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT = '设备类型表'##


  INSERT INTO `device_type` VALUES ('1', 'NVR', 'nvr', '录播设备')  ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('2', 'IPC', 'ipc', '镜头') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('3', 'UNKNOWN', 'UNKNOWN', '未知') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('4', 'HHTC', 'hhtc', '大屏设备') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('5', 'HTPR', 'htpr', '投影仪设备') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('6', 'HHTDC', 'hhtdc', '数字班牌') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('7', 'HHTWB', 'hhtwb', '白板一体机') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('8', 'HHTPA', 'hhtpa', '定位天线') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('9', 'HHTSE', 'hhtse', '安防设备') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('10', 'HHTAU', 'hhtau', '音频设备') ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('11', 'HHTMCU', 'hhtmcu', '视频会议设备')ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('12', 'HHTCTE', 'hhtcte', '会议终端设备')ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##
INSERT INTO `device_type` VALUES ('13', 'HHTCC', 'hhtcc', '中控设备')ON DUPLICATE KEY UPDATE dtype_id=VALUES(dtype_id)##


CREATE TABLE IF NOT EXISTS `device_specification` (
  `dspec_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `dspec_name` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型名称',
  `dtype_id` int(11) NOT NULL COMMENT '设备类型id',
  `connect_param` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备型号',
  `record_type` int(11) NOT NULL COMMENT '0 代表简易录播，1代表精品录播',
  `typeInt` int(11) DEFAULT NULL COMMENT '设备的代表字段简易0，精品1，艾路克2...',
  PRIMARY KEY (`dspec_id`),
  KEY `dtype_id` (`dtype_id`),
  CONSTRAINT `device_type` FOREIGN KEY (`dtype_id`) REFERENCES `device_type` (`dtype_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT = '设备型号表'##

-- ----------------------------
-- Records of device_specification
-- ----------------------------
  INSERT INTO `device_specification` VALUES ('2', '精品录播主机', '1', '12345678', '1', '1')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('3', '爱录客录播主机', '1', 'HL-ZJ0500', '1', '2')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('4', '未知', '3', 'unknown', '0', '-1')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('5', '简易录播', '1', 'HL-ZF0400', '0', '0')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('6', '鸿合教育大屏901', '4', '901.1', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('7', '鸿合v69(7025EL)', '4', 'V69.5', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('8', '鸿合2015901大屏', '4', '901.6', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('9', '鸿合v69.8', '4', 'V69.8', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('10', '鸿合918', '4', '918', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('11', 'TBOX录播主机', '1', 'TBOX-1234', '0', '0')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('12', '投影机', '5', 'HCP-A7', '3', '5')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('13', '鸿合大屏828', '4', '828', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('14', '鸿合大屏638', '4', '638', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('15', '鸿合大屏V56', '4', 'V56', '2', '3')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('16', '简易录播四机位', '1', 'HL-ZF0500', '0', '0')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('17', '虚拟录播主机', '1', 'HHTVD-IPCAM', '1', '6')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('18', '数字班牌', '6', 'DC-001', '4', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('19', '白板一体机', '7', 'HV-MC96', '5', '7')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('20', '定位天线', '8', 'PA-001', '6', '9')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('21', '安防设备', '9', 'SE-001', '7', '10')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('22', '音频设备', '10', 'AU-001', '8', '11')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('23', '展板901.1', '6', 'ZB901.1', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('24', '展板v69', '6', 'ZBV69.5', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('25', '展板2015901', '6', 'ZB901.6', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('26', '展板v69.8', '6', 'ZBV69.8', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('27', '展板918', '6', 'ZB918', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('28', '展板828', '6', 'ZB828', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('29', '展板638', '6', 'ZB638', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('30', '展板v56', '6', 'ZBV56', '2', '8')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('31', 'WBOX录播主机', '1', 'honghe-wbox', '0', '20')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('32', '鸿合互动MCU', '11', 'njxd-mcu', '9', '15')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('33', 'CiscoMCU', '11', 'Cisco-mcu', '9', '16')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('34', '会议终端设备', '12', 'Terminal', '10', '13')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('35', '中控设备', '13', 'Control', '11', '14')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('36', '鸿合大屏638.9', '4', '638.9', '2','3') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('37', 'HID白板一体机', '7', 'HID', '5','7') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('38', '展板638.9', '6', 'ZB638.9', '2','8') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('39', '威海德MCU', '11', 'vhd-mcu', '9','22') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('40', '录播ops', '1', 'honghe-ops', '0','23') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_specification` VALUES ('41', '捷视飞通MCU', '11', 'ifreecomm-mcu', '9','24') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##

-- ----------------------------
-- Table structure for command
-- ----------------------------

CREATE TABLE  IF NOT EXISTS`device_command` (
  `cmd_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备命令id',
  `cmd_name` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备命令名称',
  `cmd_group` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备命令分组名称',
  `cmd_image` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '',
  `cmd_default` bit(1) DEFAULT b'0',
  `cmd_flag` varchar(2) COLLATE utf8_general_ci DEFAULT '10' COMMENT '命令标识：10-host##01-device##11-both##',
  `cmd_hex` varchar(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应的通用命令id（与device_command表中的code_type相关联）',
  PRIMARY KEY (`cmd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT = '设备命令表'##

-- ----------------------------
-- Records of command
-- ----------------------------
  INSERT INTO `device_command` VALUES ('1', '开机', '电源管理', null, '\0', '10', '0x001001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('2', '关机', '电源管理', null, '\0', '10', '0x001002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('3', '复位', '电源管理', null, '\0', '10', '0x001003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('4', '预置点设置', 'PTZ控制', null, '\0', '10', '0x002001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('5', '预置点定位', 'PTZ控制', null, '\0', '10', '0x002002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('6', '水平转动', 'PTZ控制', null, '\0', '10', '0x002003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('7', '垂直转动', 'PTZ控制', null, '\0', '10', '0x002004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('8', '巡航', 'PTZ控制', null, '\0', '10', '0x002005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('9', '辅助灯光控制', 'PTZ控制', null, '\0', '10', '0x002006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('10', '镜头聚焦', '镜头控制', null, '\0', '10', '0x003001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('11', '镜头变倍', '镜头控制', null, '\0', '10', '0x003002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('12', '光圈设置', '镜头控制', null, '\0', '10', '0x003003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('13', '创建直播', '直播管理', null, '\0', '10', '0x004001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('14', '删除直播', '直播管理', null, '\0', '10', '0x004002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('15', '修改直播', '直播管理', null, '\0', '10', '0x004003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('16', '开始直播', '直播管理', null, '\0', '10', '0x004004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('17', '暂停直播', '直播管理', null, '\0', '10', '0x004005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('18', '停止直播', '直播管理', null, '\0', '10', '0x004006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('19', '创建录播', '录播管理', null, '\0', '10', '0x005001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('20', '删除录播', '录播管理', null, '\0', '10', '0x005002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('21', '修改录播', '录播管理', null, '\0', '10', '0x005003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('22', '开始录播', '录播管理', null, '\0', '10', '0x005004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('23', '暂停录播', '录播管理', null, '\0', '10', '0x005005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('24', '停止录播', '录播管理', null, '\0', '10', '0x005006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('25', '声音+', '音量调节', '', '\0', '10', '0x014001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('26', '声音-', '音量调节', '', '\0', '10', '0x014002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('27', '模拟电视ATV', '信号源切换', '', '\0', '10', '0x012001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('28', '数字电视DTV', '信号源切换', '', '\0', '10', '0x012002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('29', '视频1', '信号源切换', '', '\0', '10', '0x012003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('30', '视频2', '信号源切换', '', '\0', '10', '0x012004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('31', 'S-端子', '信号源切换', '', '\0', '10', '0x012005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('32', '分量', '信号源切换', '', '\0', '10', '0x012006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('33', 'HDMI1', '信号源切换', '', '\0', '10', '0x012007') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('34', 'HDMI2', '信号源切换', '', '\0', '10', '0x012008') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('35', '电脑1', '信号源切换', '', '\0', '10', '0x012009') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('36', '电脑2', '信号源切换', '', '\0', '10', '0x012010') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('37', '内置电脑', '信号源切换', '', '\0', '10', '0x012011') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('38', '电脑VGA', '信号源切换', '', '\0', '10', '0x012012') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('39', '室内', '音响模式调节', '', '\0', '10', '0x013001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('40', '室外', '音响模式调节', '', '\0', '10', '0x013002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('41', '混合', '音响模式调节', '', '\0', '10', '0x013003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('43', '启用', '触摸功能', '', '\0', '10', '0x015001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('44', '禁用', '触摸功能', '', '\0', '10', '0x015002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('45', '启用', '远程节能', '', '\0', '10', '0x016001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('46', '禁用', '远程节能', '', '\0', '10', '0x016002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('47', '重启', '电源管理', '', '\0', '10', '0x001003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('48', '其他', '电源管理', '', '\0', '10', '0x011004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('49', '临场2', '音响模式调节', null, '\0', '10', '0x013010') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('50', '临场1', '音响模式调节', null, '\0', '10', '0x013009') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('51', '用户', '音响模式调节', null, '\0', '10', '0x013008') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('52', '新闻', '音响模式调节', null, '\0', '10', '0x013007') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('53', '运动', '音响模式调节', null, '\0', '10', '0x013006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('54', '音乐', '音响模式调节', null, '\0', '10', '0x013005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('55', '标准', '音响模式调节', null, '\0', '10', '0x013004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('56', '标准', '远程节能', null, '\0', '10', '0x016003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('57', '节能', '远程节能', null, '\0', '10', '0x016004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('58', '自动', '远程节能', null, '\0', '10', '0x016005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('59', 'android模式', '触摸功能', null, '\0', '10', '0x015004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('60', '电脑模式', '触摸功能', null, '\0', '10', '0x015003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('64', '主页', '信号源切换', null, '\0', '10', '0x012016') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('65', 'HDMI3', '信号源切换', null, '\0', '10', '0x012017') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('66', '影院', '音响模式调节', null, '\0', '10', '0x013011') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('67', '电脑3', '信号源切换', null, '\0', '10', '0x012018') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('68', '多媒体', '信号源切换', null, '\0', '10', '0x012019') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('70', '视屏', '信号源切换', null, '\0', '10', '0x012021') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('71', '电影', '音响模式调节', null, '\0', '10', '0x013012') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('72', '解锁', '触摸功能', null, '\0', '10', '0x015006') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('73', '锁定', '触摸功能', null, '\0', '10', '0x015005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('74', '会议', '音响模式调节', null, '\0', '10', '0x013014') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('75', '教室', '音响模式调节', null, '\0', '10', '0x013013') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('76', '开机', '投影仪开关', null, '\0', '10', '0x017001') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('77', '关机', '投影仪开关', null, '\0', '10', '0x017002') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('78', '正常', '远程节能', null, '\0', '10', '0x017003') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('79', '正常', '省电模式', null, '\0', '10', '0x017004') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##
INSERT INTO `device_command` VALUES ('80', '省电', '省电模式', null, '\0', '10', '0x017005') ON DUPLICATE KEY UPDATE cmd_id=VALUES(cmd_id)##

-- ----------------------------
-- Table structure for device_command_code
-- ----------------------------

CREATE TABLE  IF NOT EXISTS`device_command_code` (
  `code_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '命令编码id',
  `code_name` varchar(26) COLLATE utf8_general_ci NOT NULL COMMENT '命令编码',
  `code_type` varchar(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应的通用命令id',
  `code_result` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '命令返回码',
  `code_remark` varchar(100) COLLATE utf8_general_ci DEFAULT NULL COMMENT '命令编码备注',
  `code_instruction` varchar(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT '命令编码说明',
  `dspec_id` int(11) DEFAULT '6' COMMENT '设备类型id',
  `code_code` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '通信接口解释标准码',
  `code_flag` varchar(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '命令返回数值',
  `code_group` varchar(20) COLLATE utf8_general_ci DEFAULT NULL COMMENT '命令所属组',
  PRIMARY KEY (`code_id`),
  KEY `desp_code` (`dspec_id`),
  CONSTRAINT `device_command_code_ibfk_1` FOREIGN KEY (`dspec_id`) REFERENCES `device_specification` (`dspec_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT = '设备命令编码表'##

-- ----------------------------
-- Records of device_command_code
-- ----------------------------
  INSERT INTO `device_command_code` VALUES ('1', '7F0899A2B3C402ff0100CF', '0x001001', '7E0999A2B3C402ff010001CF', '（00 ～2F）无返回参数,TV收到串口命令后将返回码写入串口，返回给上位机', '电源管理：01：开机', '6', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('2', '7F0899A2B3C402ff0101CF', '0x001002', '7F0999A2B3C402ff010101CF', '（00 ～2F）无返回参数,TV收到串口命令后将返回码写入串口，返回给上位机', '电源管理：关机', '6', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('3', '7F0899A2B3C402ff0102CF', null, '7F0999A2B3C402ff010201CF', '静音', null, '6', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('4', '7F0899A2B3C402ff0105CF', null, '7F0999A2B3C402ff010501CF', '音响模式（设置）XX=00：标准', null, '6', 'AudioMode', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('5', '7F0899A2B3C402ff0103CF', null, '7F0999A2B3C402ff010301CF', '触控状态', null, '6', 'Touch', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('6', '7F0899A2B3C402ff0104CF', null, '7F0999A2B3C402ff010401CF', 'WIFI', null, '6', '', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('7', '7F0899A2B3C402ff0106CF', null, '7F0999A2B3C402ff010601CF', '信号源', null, '6', '', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('8', '7F0899A2B3C402ff0109CF', null, '7F0999A2B3C402ff010901CF', '显示状态', null, '6', '', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('9', '7F0899A2B3C402ff010ACF', '0x012007', '7F0999A2B3C402ff010A01CF', '17:高清1(HDMI1)', '信号源切换：XX=17:高清1(HDMI1)', '6', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('10', '7F0899A2B3C402ff010BCF', '0x012008', '7F0999A2B3C402ff010B01CF', '18:高清2(HDMI2)', '信号源切换：XX=18:高清2(HDMI2)', '6', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('11', '7F0899A2B3C402ff010CCF', '0x012011', '7F0999A2B3C402ff010C01CF', '19:OPS电脑(内置电脑)', '信号源切换：XX=19:OPS电脑(内置电脑)', '6', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('12', '7F0899A2B3C402ff010DCF', '0x012009', '7F0999A2B3C402ff010D01CF', '00:VGA 电脑1', '信号源切换：XX=00:VGA电脑1', '6', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('13', '7F0899A2B3C402ff010ECF', '0x012010', '7F0999A2B3C402ff010E01CF', '11: 电脑2 ', '信号源切换：XX=11:电脑2 ', '6', 'VGA2', '11', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('14', '7F0899A2B3C402ff0111CF', '0x012003', '7F0999A2B3C402ff011101CF', '02：视频1', '信号源切换：XX=02:视频1', '6', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('15', '7F0899A2B3C402ff0112CF', '0x012004', '7F0999A2B3C402ff011201CF', '03：视频2', '信号源切换：XX=03:视频2', '6', 'VIDEO2', '03', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('16', '7F0899A2B3C402ff0110CF', '0x012006', '7F0999A2B3C402ff011001CF', '10:分量', '信号源切换：XX=10:分量', '6', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('17', '7F0899A2B3C402ff0118CF', '0x014001', '7F0999A2B3C402ff011801CF', '声音+', null, '6', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('18', '7F0899A2B3C402ff0117CF', '0x014002', '7F0999A2B3C402ff011701CF', '声音-', null, '6', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('19', '7F0899A2B3C402ff011FCF', null, '7F0999A2B3C402ff011F01CF', '截屏', null, '6', 'ScreenShot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('20', '7F0899A2B3C402ff0131CF', null, '7F0999A2B3C402ff0131XXCF', '设备型号', null, '6', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('21', '7F0899A2B3C402ff0132CF', null, '7F0999A2B3C402ff0132XXCF', 'DSPLAYSTATE显示状态', 'XX指的当前通道号：XX=01：模拟电视，=1C：数字电视，=02：视频1，=03：视频2，=0B：S-端子，=10：分量，=17：高清1，（HDMI1）=18：高清2(HDMI2)，=19(内置电脑ops)=00：VGA(电脑1)=11(电脑2)', '6', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('22', '7F0899A2B3C402ff0133CF', null, '7F0999A2B3C402ff0133XXCF', 'VOLUME声音（如静音则返回0）', 'XX表示当前音量值（XX为十六进制数，范围为：00～64）。如：XX=20：当前音量值为32（十进制），=00：当前为静音。', '6', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('23', '7F0899A2B3C402ff0134CF', null, '7F0999A2B3C402ff0134XXCF', '音响模式', 'XX指的音响模式：XX=00：标准，=01：音乐，=02：运动，=03：新闻，=04：用户，=05：临场1，=06：临场2', '6', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('24', '7F0899A2B3C402ff0135CF', null, '7F0999A2B3C402ff0135XXCF', 'ECOMODE节能模式（31～37）有返回参数用返回码中的第11位保存需要返回的参数，返回给上位机', 'XX指的节能模式：XX=00：标准，=01：节能，=02：自动', '6', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('25', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', 'TOUCHSTATE触控状态', 'XX指的触摸模式：XX=00：电脑模式，=01：android模式(电脑模式下)', '6', 'TouchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('26', '7F0899A2B3C402ff0137CF', null, '7F0999A2B3C402ff0137XXCF', '开关机状态', 'XX指的开关机状态：XX=01：开机状态', '6', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('27', '7F0899A2B3C402ff0105CF', '0x013004', '7F0999A2B3C402ff010501CF', '音响模式-标准', '音响模式：XX=00：标准', '6', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('28', '7F0899A2B3C402ff0105CF', '0x013005', '7F0999A2B3C402ff010501CF', '音响模式-音乐', '音响模式：XX=01：音乐', '6', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('29', '7F0899A2B3C402ff0105CF', '0x013006', '7F0999A2B3C402ff010501CF', '音响模式-运动', '音响模式：XX=02：运动', '6', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('30', '7F0899A2B3C402ff0105CF', '0x013007', '7F0999A2B3C402ff010501CF', '音响模式-新闻', '音响模式：XX=03：新闻', '6', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('31', '7F0899A2B3C402ff0105CF', '0x013008', '7F0999A2B3C402ff010501CF', '音响模式-用户', '音响模式：XX=04：用户', '6', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('32', '7F0899A2B3C402ff0105CF', '0x013009', '7F0999A2B3C402ff010501CF', '音响模式-临场1', '音响模式：XX=05：临场1', '6', 'Spot1', '05', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('33', '7F0899A2B3C402ff0105CF', '0x013010', '7F0999A2B3C402ff010501CF', '音响模式-临场2', '音响模式：XX=06：临场2', '6', 'Spot2', '06', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('34', '7F0899A2B3C402ff0116CF', '0x016003', '7F0999A2B3C402ff011601CF', '远程节能-标准', '节能模式：XX=00：标准', '6', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('35', '7F0899A2B3C402ff0116CF', '0x016004', '7F0999A2B3C402ff011601CF', '远程节能-节能', '节能模式：XX=01：节能', '6', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('36', '7F0899A2B3C402ff0116CF', '0x016005', '7F0999A2B3C402ff011601CF', '远程节能-自动', '节能模式：XX=02：自动', '6', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('37', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', '触控状态-电脑模式', '触摸功能：XX=00：电脑模式', '6', 'computer', '00', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('38', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', '触控状态-android模式', '触摸功能：XX=01：android模式', '6', 'android', '01', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('40', '7F0899A2B3C402ff0108CF', '0x012001', '7F0999A2B3C402ff010B01CF', '01:模拟电视', '信号源切换：XX=01:模拟电视', '6', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('41', '7F0899A2B3C402ff010FCF', '0x012002', '7F0999A2B3C402ff010B01CF', '1C:数字电视', '信号源切换：XX=1C:数字电视', '6', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('48', '7F0899A2B3C402ff010BCF', '0x012005', '7F0999A2B3C402ff010B01CF', '信号源切换：XX=0B:S端子', '信号源切换：XX=0B:S端子', '6', 'STerminal', '0B', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('73', '7F0899A2B3C402ff011CCF', '0x012016', '7F0999A2B3C402ff011C01CF', '主页', '信号源切换：XX = 22：主页', '6', 'Home', '22', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('80', '7F0899A2B3C402ff0100CF', '0x001001', '7E0999A2B3C402ff010001CF', '开机', null, '7', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('81', '7F0899A2B3C402ff0101CF', '0x001002', '7E0999A2B3C402ff010101CF', '关机', null, '7', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('82', '7F0899A2B3C402ff0102CF', null, '7E0999A2B3C402ff010201CF', '静音', null, '7', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('83', '7F0899A2B3C402ff0103CF', '0x012001', '7E0999A2B3C402ff010301CF', 'TV', '模拟电视XX=00', '7', 'ATV', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('84', '7F0899A2B3C402ff0104CF', '0x012012', '7E0999A2B3C402ff010401CF', '电脑VGA', '电脑VGA XX= 06：电脑', '7', 'VGA', '06', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('86', '7F0899A2B3C402ff0106CF', '0x012021', '7E0999A2B3C402ff010601CF', 'AV', '视频XX= 08：视频', '7', 'VIDEO', '08', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('87', '7F0899A2B3C402ff0107CF', '0x012007', '7E0999A2B3C402ff010701CF', 'HDMI1', ' XX = 03', '7', 'HDMI1', '03', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('88', '7F0899A2B3C402ff0122CF', '0x012008', '7E0999A2B3C402ff012201CF', 'HDMI2', 'XX= 04：HDMI2', '7', 'HDMI2', '04', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('89', '7F0899A2B3C402ff0123CF', '0x012017', '7E0999A2B3C402ff012301CF', 'HDMI3', 'XX= 05：HDMI3', '7', 'HDMI3', '05', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('90', '7F0899A2B3C402ff0124CF', '0x012011', '7E0999A2B3C402ff012401CF', 'OPS电脑(内置电脑)', '02：OPS电脑', '7', 'OPS', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('92', '7F0899A2B3C402ff0109CF', '0x012019', '7E0999A2B3C402ff010901CF', 'Multi-media', 'XX=09多媒体', '7', 'Media', '09', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('95', '7F0899A2B3C402ff010CCF', '0x014002', '7E0999A2B3C402ff010C01CF', 'V-', '声音-', '7', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('96', '7F0899A2B3C402ff010DCF', '0x014001', '7E0999A2B3C402ff010D01CF', 'V+', '声音+', '7', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('107', '7F0899A2B3C402ff0125CF', '0x012002', '7E0999A2B3C402ff012501CF', 'DTV', '数字电视xx=01', '7', 'DTV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('108', '7F0899A2B3C402ff0136CF', null, '7E0999A2B3C402ff0136XXCF', '开关机状态', 'XX指的开关机状态：XX=00关机状态\r\nXX = 01：开机状态', '7', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('112', '7F0899A2B3C402ff0134CF', '0x013004', '7E0999A2B3C402ff0134XXCF', '音响模式', '标准', '7', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('113', '7F0899A2B3C402ff0134CF', '0x013005', '7E0999A2B3C402ff0134XXCF', '音响模式', '音乐', '7', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('114', '7F0899A2B3C402ff0134CF', '0x013011', '7E0999A2B3C402ff0134XXCF', '音响模式', '电影', '7', 'Movie', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('115', '7F0899A2B3C402ff0134CF', '0x013006', '7E0999A2B3C402ff0134XXCF', '音响模式', '运动', '7', 'Sport', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('116', '7F0899A2B3C402ff0134CF', '0x013008', '7E0999A2B3C402ff0134XXCF', '音响模式', '用户', '7', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('117', '7F0899A2B3C402ff0135CF', null, '7E0999A2B3C402ff0135XXCF', 'ECOMODE  节能模式', 'XX指的节能模式：\r\nXX = 00：标准，\r\n      = 01：节能，\r\n      = 02：自动  ', '7', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('118', '7F0899A2B3C402ff0134CF', null, '7E0999A2B3C402ff0134XXCF', '音响模式', 'XX=00:标准,XX=01:音乐,XX=02:电影,XX=03:运动XX=04:用户.', '7', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('119', '7F0899A2B3C402ff0133CF', null, '7E0999A2B3C402ff0133XXCF', 'VOLUME声音（如静音则返回0）', 'XX 表示当前音量值（XX为十六进制数，范围为：00～64）。如：\r\nXX = 20：当前音量值为32（十进制），\r\n      = 00：当前为静音。', '7', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('120', '7F0899A2B3C402ff0132CF', null, '7E0999A2B3C402ff0132XXCF', 'DSPLAYSTATE显示状态', 'XX = 00：模拟电视， XX= 01：数字电视，\r\nXX= 02：OPS电脑，   XX = 03：HDMI1\r\nXX= 04：HDMI2，     XX= 05：HDMI3，\r\nXX= 06：电脑，     XX= 07：分量，\r\nXX= 08：视频,     XX =09:多媒体', '7', 'DisplayState', '', null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('131', '7F0899A2B3C402ff0131CF', null, '7E0999A2B3C402ff0131XXCF', '设备型号', 'XX指的主IC的型号：\r\nXX = 01：801 ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)## XX= 02：901 ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##XX =03:V69', '7', 'Mosel', '03', null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('132', '7F0899A2B3C402ff0126CF', null, '7E0999A2B3C402ff012601CF', '节能', '节能设置命令', '7', 'Energy', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('133', '7F0899A2B3C402ff0135CF', '0x016003', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '标准', '7', 'Normal', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('134', '7F0899A2B3C402ff0135CF', '0x016004', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '节能', '7', 'PowerSaving', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('135', '7F0899A2B3C402ff0135CF', '0x016005', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '自动', '7', 'Auto', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('136', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '8', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('137', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '8', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('138', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '8', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('139', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '8', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('140', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '8', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('141', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '8', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('142', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '8', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('143', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '8', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('144', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '8', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('145', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '8', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('146', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '8', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('147', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '8', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('148', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '8', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('149', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '8', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('150', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('151', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('152', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '8', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('153', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '8', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('154', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '8', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('155', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '8', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('156', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '8', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('157', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '8', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('158', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '8', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('161', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('162', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '8', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('163', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '8', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('164', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '8', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('165', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('166', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('167', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '8', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('168', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '8', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('169', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '8', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('170', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '8', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('171', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '8', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('172', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '8', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('173', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('174', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '8', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('175', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '10', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('176', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视X = 01：模拟电视', '10', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('177', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI1 xx=17：高清1', '10', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('178', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2 xx=18', '10', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('179', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI3 xx=20', '10', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('180', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑） xx = 19：OPS', '10', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('181', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1   00：VGA1', '10', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('182', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视= 1C：数字电视', '10', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('183', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量  = 10：分量', '10', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('184', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 = 02：视频1', '10', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('185', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '10', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('186', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '10', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('187', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('188', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('189', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '10', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('190', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '10', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('191', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '10', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('192', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '10', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('193', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '10', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('194', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '10', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('195', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '10', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('196', '7F0899A2B3C402FF0305CF', '0x013009', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：05##临场1', '10', 'Spot1', '05', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('197', '7F0899A2B3C402FF0306CF', '0x013010', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：06##临场2', '10', 'Spot2', '06', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('198', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('199', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '10', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('200', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '10', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('201', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '10', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('202', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('203', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('204', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '10', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('205', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '10', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('206', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '10', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('207', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '10', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('208', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '10', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('209', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '10', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('210', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('211', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '10', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('212', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '9', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('213', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视 01：模拟电视', '9', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('214', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI1  = 17：高清1', '9', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('215', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2   = 18：高清2', '9', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('216', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI3  =20：高清3', '9', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('217', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）    = 19：OPS', '9', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('218', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1  = 00：VGA1', '9', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('219', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视  = 1C：数字电视', '9', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('220', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量  = 10：分量', '9', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('221', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 = 02：视频1', '9', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('222', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '9', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('223', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '9', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('224', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('225', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('226', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '9', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('227', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '9', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('228', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '9', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('229', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '9', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('230', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '9', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('231', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '9', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('232', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '9', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('235', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('236', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '9', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('237', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '9', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('238', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '9', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('239', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('240', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('241', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '9', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('242', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '9', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('243', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '9', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('244', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '9', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('245', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '9', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('246', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '9', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('247', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('248', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '9', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('251', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '9', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('252', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '8', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('253', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '6', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('254', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '6', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('255', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '6', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('256', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '6', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('257', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '6', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('258', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '6', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('259', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '6', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('260', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '6', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('261', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '6', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('262', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '6', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('263', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '8', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('264', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '8', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('265', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '8', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('266', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '8', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('267', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '8', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('268', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '8', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('269', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '8', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('270', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '8', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('271', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '8', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('272', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '8', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('273', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '9', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('274', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '9', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('275', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '9', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('276', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '9', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('277', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '9', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('278', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '9', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('279', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '9', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('280', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '9', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('281', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '9', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('282', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '9', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('283', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '10', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('284', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '10', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('285', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '10', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('286', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '10', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('287', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '10', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('288', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '10', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('289', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '10', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('290', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '10', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('291', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '10', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('292', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '10', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('293', '7F0899A2B3C402FF0136CF', '', '7F0999A2B3C402FF0136XXCF', '触控状态-电脑模式', '触摸功能：XX=00：电脑模式', '8', 'computer', '00', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('294', '7F0899A2B3C402FF0136CF', '', '7F0999A2B3C402FF0136XXCF', '触控状态-android模式', '触摸功能：XX=01：android模式', '8', 'android', '01', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('295', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '6', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('296', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '7', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('297', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '6', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('298', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '8', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('299', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '9', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('300', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '10', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('301', 'BEEF0306002AD3010000600000', '0x017002', '06', '关机', ' ', '12', 'TurnOff', '1d0000', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('302', 'BEEF030600BAD2010000600100', '0x017001', '06', '开机', '', '12', 'TurnOn', '1d0100', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('303', 'BEEF03060019D3020000600000', '', '1DXX00', '获得开关机状态', '1D0000:TurnOff##1D0100:TurnOn:1D0200:CoolDown', '12', 'TurnState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('304', 'BEEF030600FED2010000200000', '0x012009', '06', 'Computer1', '', '12', 'Computer1', '1d0000', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('305', 'BEEF0306003ED0010000200400', '0x012010', '06', 'Computer2', '', '12', 'Computer2', '1d0400', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('306', 'BEEF030600AED1010000200500', '0x012006', '06', 'Component', '', '12', 'Component', '1d0500', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('307', 'BEEF0306009ED3010000200200', '0x012005', '06', 'S-Video', '', '12', 'S-Video', '1d0200', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('308', 'BEEF0306006ED3010000200100', '0x012003', '06', 'Video', '', '12', 'Video', '1d0100', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('309', 'BEEF030600CDD2020000200000', '', '1DXX00', '获得信号源状态', '1D0100:Video##', '12', 'ChannelState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('310', '', '', '', '冷却', '', '12', 'CoolDown', '1d0200', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('311', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '6', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('312', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '6', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('313', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '6', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('314', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '7', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('315', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '7', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('316', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '7', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('317', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '8', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('318', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '8', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('319', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '8', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('320', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '9', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('321', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '9', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('322', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '9', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('323', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '10', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('324', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '10', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('325', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '10', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('331', 'BEEF0306002AD3010000600000', '0x001002', '06', '关机', null, '12', 'TurnOff', '1d0000', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('332', 'BEEF030600BAD2010000600100', '0x001001', '06', '开机', null, '12', 'boot', '1d0000', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('385', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', NULL, '13', 'Boot', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('386', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', NULL, '13', 'ShutDown', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('387', '7F0899A2B3C402FF0102CF', NULL, '7F0999A2B3C402FF010201CF', '静音', NULL, '13', 'Mute', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('388', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '13', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('389', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '13', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('390', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '13', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('391', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '13', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('392', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '13', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('393', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '13', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('394', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '13', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('395', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '13', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('396', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '13', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('397', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', NULL, '13', 'Volume-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('398', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', NULL, '13', 'Volume+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('399', '7F0899A2B3C402FF0119CF', NULL, '7F0999A2B3C402FF011901CF', '频道-', NULL, '13', 'TVChannel-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('400', '7F0899A2B3C402FF011ACF', NULL, '7F0999A2B3C402FF011A01CF', '频道+', NULL, '13', 'TVChannel+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('401', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', NULL, '13', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('402', '7F0899A2B3C402FF011FCF', NULL, '7F0999A2B3C402FF011F01CF', '截屏', NULL, '13', 'ScreenShot', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('403', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '13', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('404', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01音乐', '13', 'Music', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('405', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '13', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('406', '7F0899A2B3C402FF0303CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03运动', '13', 'Sport', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('407', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04用户', '13', 'Custom', '4', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('408', '7F0899A2B3C402FF05XXCF', NULL, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '13', 'VolumeSetting', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('409', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '13', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('410', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '13', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('411', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '13', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('412', '7F0899A2B3C402FF013DCF', NULL, '7F0999A2B3C402FF013D01CF', '查询固件版本号', NULL, '13', NULL, NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('413', '7F0899A2B3C402FF013ECF', NULL, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', NULL, '13', 'ChannelSearch', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('414', '7F0899A2B3C402FF0131CF', NULL, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '13', 'Model', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('415', '7F0899A2B3C402FF0132CF', NULL, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', NULL, '13', 'DisplayState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('416', '7F0899A2B3C402FF0133CF', NULL, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', NULL, '13', 'Volume', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('417', '7F0899A2B3C402FF0134CF', NULL, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', NULL, '13', 'AudioState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('418', '7F0899A2B3C402FF0135CF', NULL, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', NULL, '13', 'EnergyState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('419', '7F0899A2B3C402FF0137CF', NULL, '7F0999A2B3C402FF0137XXCF', '开关机状态', NULL, '13', 'SwitchState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('420', '7F0899A2B3C402FF013CCF', NULL, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', NULL, '13', 'TVState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('421', '7F0899A2B3C402FF04XXCF', NULL, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', NULL, '13', 'TVChannel', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('422', '7F0899A2B3C402FF0121CF', NULL, '7F0999A2B3C402FF012101CF', '数字1', NULL, '13', 'Number1', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('423', '7F0899A2B3C402FF0122CF', NULL, '7F0999A2B3C402FF012201CF', '数字2', NULL, '13', 'Number2', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('424', '7F0899A2B3C402FF0123CF', NULL, '7F0999A2B3C402FF012301CF', '数字3', NULL, '13', 'Number3', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('425', '7F0899A2B3C402FF0124CF', NULL, '7F0999A2B3C402FF012401CF', '数字4', NULL, '13', 'Number4', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('426', '7F0899A2B3C402FF0125CF', NULL, '7F0999A2B3C402FF012501CF', '数字5', NULL, '13', 'Number5', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('427', '7F0899A2B3C402FF0126CF', NULL, '7F0999A2B3C402FF012601CF', '数字6', NULL, '13', 'Number6', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('428', '7F0899A2B3C402FF0127CF', NULL, '7F0999A2B3C402FF012701CF', '数字7', NULL, '13', 'Number7', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('429', '7F0899A2B3C402FF0128CF', NULL, '7F0999A2B3C402FF012801CF', '数字8', NULL, '13', 'Number8', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('430', '7F0899A2B3C402FF0129CF', NULL, '7F0999A2B3C402FF012901CF', '数字9', NULL, '13', 'Number9', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('431', '7F0899A2B3C402FF012ACF', NULL, '7F0999A2B3C402FF012A01CF', '数字0', NULL, '13', 'Number0', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('432', '7F0899A2B3C402FF0146CF', NULL, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '13', 'MCUAddress', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('435', '7F0899A2B3C402FF0148CF', NULL, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '13', 'Brightness-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('436', '7F0899A2B3C402FF0147CF', NULL, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '13', 'Brightness+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('437', '7F0899A2B3C402FF09XXCF', NULL, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '13', 'SetBrightness', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('438', '7F0899A2B3C402FF0149CF', NULL, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '13', 'Brightness', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('439', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', NULL, '14', 'Boot', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('440', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', NULL, '14', 'ShutDown', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('441', '7F0899A2B3C402FF0102CF', NULL, '7F0999A2B3C402FF010201CF', '静音', NULL, '14', 'Mute', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('442', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '14', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('443', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '14', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('444', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '14', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('445', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '14', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('446', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '14', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('447', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '14', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('448', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '14', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('449', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '14', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('450', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '14', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('451', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', NULL, '14', 'Volume-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('452', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', NULL, '14', 'Volume+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('453', '7F0899A2B3C402FF0119CF', NULL, '7F0999A2B3C402FF011901CF', '频道-', NULL, '14', 'TVChannel-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('454', '7F0899A2B3C402FF011ACF', NULL, '7F0999A2B3C402FF011A01CF', '频道+', NULL, '14', 'TVChannel+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('455', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', NULL, '14', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('456', '7F0899A2B3C402FF011FCF', NULL, '7F0999A2B3C402FF011F01CF', '截屏', NULL, '14', 'ScreenShot', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('457', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '14', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('458', '7F0899A2B3C402FF0301CF', '0x013014', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01会议', '14', 'Meeting', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('459', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '14', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('460', '7F0899A2B3C402FF0303CF', '0x013013', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03教室', '14', 'Classroom', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('462', '7F0899A2B3C402FF05XXCF', NULL, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '14', 'VolumeSetting', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('463', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '14', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('464', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '14', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('465', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '14', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('466', '7F0899A2B3C402FF013DCF', NULL, '7F0999A2B3C402FF013D01CF', '查询固件版本号', NULL, '14', NULL, NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('467', '7F0899A2B3C402FF013ECF', NULL, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', NULL, '14', 'ChannelSearch', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('468', '7F0899A2B3C402FF0131CF', NULL, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '14', 'Model', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('469', '7F0899A2B3C402FF0132CF', NULL, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', NULL, '14', 'DisplayState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('470', '7F0899A2B3C402FF0133CF', NULL, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', NULL, '14', 'Volume', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('471', '7F0899A2B3C402FF0134CF', NULL, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', NULL, '14', 'AudioState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('472', '7F0899A2B3C402FF0135CF', NULL, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', NULL, '14', 'EnergyState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('473', '7F0899A2B3C402FF0137CF', NULL, '7F0999A2B3C402FF0137XXCF', '开关机状态', NULL, '14', 'SwitchState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('474', '7F0899A2B3C402FF013CCF', NULL, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', NULL, '14', 'TVState', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('475', '7F0899A2B3C402FF04XXCF', NULL, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', NULL, '14', 'TVChannel', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('476', '7F0899A2B3C402FF0121CF', NULL, '7F0999A2B3C402FF012101CF', '数字1', NULL, '14', 'Number1', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('477', '7F0899A2B3C402FF0122CF', NULL, '7F0999A2B3C402FF012201CF', '数字2', NULL, '14', 'Number2', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('478', '7F0899A2B3C402FF0123CF', NULL, '7F0999A2B3C402FF012301CF', '数字3', NULL, '14', 'Number3', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('479', '7F0899A2B3C402FF0124CF', NULL, '7F0999A2B3C402FF012401CF', '数字4', NULL, '14', 'Number4', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('480', '7F0899A2B3C402FF0125CF', NULL, '7F0999A2B3C402FF012501CF', '数字5', NULL, '14', 'Number5', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('481', '7F0899A2B3C402FF0126CF', NULL, '7F0999A2B3C402FF012601CF', '数字6', NULL, '14', 'Number6', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('482', '7F0899A2B3C402FF0127CF', NULL, '7F0999A2B3C402FF012701CF', '数字7', NULL, '14', 'Number7', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('483', '7F0899A2B3C402FF0128CF', NULL, '7F0999A2B3C402FF012801CF', '数字8', NULL, '14', 'Number8', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('484', '7F0899A2B3C402FF0129CF', NULL, '7F0999A2B3C402FF012901CF', '数字9', NULL, '14', 'Number9', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('485', '7F0899A2B3C402FF012ACF', NULL, '7F0999A2B3C402FF012A01CF', '数字0', NULL, '14', 'Number0', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('486', '7F0899A2B3C402FF0146CF', NULL, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '14', 'MCUAddress', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('489', '7F0899A2B3C402FF0148CF', NULL, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '14', 'Brightness-', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('490', '7F0899A2B3C402FF0147CF', NULL, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '14', 'Brightness+', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('491', '7F0899A2B3C402FF09XXCF', NULL, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '14', 'SetBrightness', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('492', '7F0899A2B3C402FF0149CF', NULL, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '14', 'Brightness', NULL, NULL) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('493', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '13', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('494', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '13', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('495', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '13', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('496', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '14', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('497', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '14', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('498', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '14', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('499', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', '', 15, 'Boot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('500', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', 15, 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('501', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', 15, 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('502', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', 15, 'HDMI1', 17, 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('503', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', 15, 'HDMI2', 18, 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('504', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', 15, 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('505', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', 15, 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('506', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', 15, 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('507', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', 15, 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('508', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', 15, 'TVChannel-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('509', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', 15, 'TVChannel+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('511', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', 15, 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('512', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00：标准', 15, 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('513', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01：音乐', 15, 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('514', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02：影院', 15, 'Movie', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('515', '7F0899A2B3C402FF0303CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03：运动', 15, 'Sport', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('516', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04：用户', 15, 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('517', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', 15, 'VolumeSetting', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('518', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00：标准', 15, 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('519', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01：节能', 15, 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('520', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02：自动', 15, 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('521', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '15', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('522', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '15', 'ChannelSearch', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('523', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '15', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('524', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '15', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('525', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '15', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('526', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '15', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('527', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '15', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('528', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '15', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('529', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '15', 'TVState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('530', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '15', 'TVChannel', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('531', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '15', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('532', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '15', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('533', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '15', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('534', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '15', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('535', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '15', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('536', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '15', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('537', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '15', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('538', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '15', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('539', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '15', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('540', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '15', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('541', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '15', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('542', '7F0899A2B3C402FF0157CF', '', '7F0999A2B3C402FF015701CF', '大屏锁定/解锁屏幕', '00:解锁，01:锁定', '15', 'ScreenLock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('543', '7F0899A2B3C402FF0158CF', '', '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', '00:解锁，01:锁定', '15', 'ScreenLockMode', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('544', '7F0899A2B3C402FF0148CF', '', '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '15', 'Brightness-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('545', '7F0899A2B3C402FF0147CF', '', '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '15', 'Brightness+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('546', '7F0899A2B3C402FF09XXCF', '', '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '15', 'SetBrightness', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('547', '7F0899A2B3C402FF0149CF', '', '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '15', 'Brightness', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('548', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', '19:OPS电脑(内置电脑)', '信号源切换：XX=19:OPS电脑(内置电脑)', '15', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('549', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定/解锁屏幕', '00:解锁，01:锁定', '15', 'ScreenLock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('550', '7F0899A2B3C402FF0158CF', '', '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', '00:解锁，01:锁定', '15', 'ScreenLockMode', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('551', '7F0899A2B3C402FF013ACF', '0x012019', '7F0899A2B3C402FF013A01CF', 'Multi-media', 'XX=09多媒体', '15', 'Media', '09', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('552', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', '', '15', 'ScreenUnlock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('553', 'Back', null, 'Back', '后退', '后退', '18', 'Back', null, 'QWeb') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('554', 'Forward', null, 'Forward', '前进', '前进', '18', 'Forward', null, 'QWeb') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('555', '', null, 'Video', '视频', '视频', '18', 'Video', null, 'Video') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('556', '', null, 'Notification', '通知', '通知', '18', 'Notification', null, 'Notification') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('557', '', null, 'Programme', '节目', '节目', '18', 'Programme', null, 'Programme') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('558', '', null, 'Return', '返回', '返回', '18', 'Return', null, 'Return') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('559', '7F08AA7E05007AA001CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量,音量查询', '中控控制音箱音量，音量查询', '19', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('560', '7F08AA7E05007AA002CECF', '0x014002', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量减小', '19', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('561', '7F08AA7E05007AA003CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量静音', '19', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('562', '7F08AA7E05007AA004CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量取消静音', '19', 'UnMute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('563', '7F08AA7E05007AA005CECF', '0x014001', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量增加，Y =  0X00~0X10,音量值；0X10为静音', '19', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('564', '7F08AA7E05007AA100CECF', '0x017004', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', 'Y0,Y = 0X00： 标准Y0,Y = 0X01: 省电', '19', 'Standard', null, 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('565', '7F08AA7E05007AA101CECF', '0x017005', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', 'Y0,Y = 0X00： 标准Y0,Y = 0X01: 省电', '19', 'PowerSaving', '', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('566', '7F08AA7E05007AA102CECF', '0x017003', '7F08AA7E0500FAA1YCECF', '中控设置投影机待机模式', '投影机待机模式Y0,Y = 0X02:正常Y0,Y = 0X03:节能', '19', 'Normal', '', 'Standby') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('567', '7F08AA7E05007AA103CECF', '0x016004', '7F08AA7E0500FAA1YCECF', '中控设置投影机待机模式', '投影机待机模式Y0,Y = 0X02:正常Y0,Y = 0X03:节能', '19', 'PowerSaving', '', 'Standby') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('568', '7F08AA7E05007AA104CECF', '0x001001', '7F08AA7E0500FAA1YCECF', '中控设置投影机电源模式', '投影机电源Y0,Y = 0X04:开机Y0,Y = 0X05:关机', '19', 'TurnOn', '', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('569', '7F08AA7E05007AA105CECF', '0x001002', '7F08AA7E0500FAA1YCECF', '中控设置投影机电源模式', '投影机电源Y0,Y = 0X04:开机Y0,Y = 0X05:关机', '19', 'TurnOff', '', 'Turn') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('570', '7F0899A2B3C402ff0100CF', '0x001001', '7E0999A2B3C402ff010001CF', '（00 ～2F）无返回参数,TV收到串口命令后将返回码写入串口，返回给上位机', '电源管理：01：开机', '23', 'Boot', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('571', '7F0899A2B3C402ff0101CF', '0x001002', '7F0999A2B3C402ff010101CF', '（00 ～2F）无返回参数,TV收到串口命令后将返回码写入串口，返回给上位机', '电源管理：关机', '23', 'ShutDown', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('572', '7F0899A2B3C402ff0102CF', null, '7F0999A2B3C402ff010201CF', '静音', null, '23', 'Mute', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('573', '7F0899A2B3C402ff0105CF', null, '7F0999A2B3C402ff010501CF', '音响模式（设置）XX=00：标准', null, '23', 'AudioMode', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('574', '7F0899A2B3C402ff0103CF', null, '7F0999A2B3C402ff010301CF', '触控状态', null, '23', 'Touch', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('575', '7F0899A2B3C402ff0104CF', null, '7F0999A2B3C402ff010401CF', 'WIFI', null, '23', '', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('576', '7F0899A2B3C402ff0106CF', null, '7F0999A2B3C402ff010601CF', '信号源', null, '23', '', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('577', '7F0899A2B3C402ff0109CF', null, '7F0999A2B3C402ff010901CF', '显示状态', null, '23', '', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('578', '7F0899A2B3C402ff010ACF', '0x012007', '7F0999A2B3C402ff010A01CF', '17:高清1(HDMI1)', '信号源切换：XX=17:高清1(HDMI1)', '23', 'HDMI1', '17', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('579', '7F0899A2B3C402ff010BCF', '0x012008', '7F0999A2B3C402ff010B01CF', '18:高清2(HDMI2)', '信号源切换：XX=18:高清2(HDMI2)', '23', 'HDMI2', '18', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('580', '7F0899A2B3C402ff010CCF', '0x012011', '7F0999A2B3C402ff010C01CF', '19:OPS电脑(内置电脑)', '信号源切换：XX=19:OPS电脑(内置电脑)', '23', 'OPS', '19', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('581', '7F0899A2B3C402ff010DCF', '0x012009', '7F0999A2B3C402ff010D01CF', '00:VGA 电脑1', '信号源切换：XX=00:VGA电脑1', '23', 'VGA1', '00', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('582', '7F0899A2B3C402ff010ECF', '0x012010', '7F0999A2B3C402ff010E01CF', '11: 电脑2 ', '信号源切换：XX=11:电脑2 ', '23', 'VGA2', '11', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('583', '7F0899A2B3C402ff0111CF', '0x012003', '7F0999A2B3C402ff011101CF', '02：视频1', '信号源切换：XX=02:视频1', '23', 'VIDEO1', '02', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('584', '7F0899A2B3C402ff0112CF', '0x012004', '7F0999A2B3C402ff011201CF', '03：视频2', '信号源切换：XX=03:视频2', '23', 'VIDEO2', '03', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('585', '7F0899A2B3C402ff0110CF', '0x012006', '7F0999A2B3C402ff011001CF', '10:分量', '信号源切换：XX=10:分量', '23', 'Weight', '10', 'Channel')ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('586', '7F0899A2B3C402ff0118CF', '0x014001', '7F0999A2B3C402ff011801CF', '声音+', null, '23', 'Volume+', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('587', '7F0899A2B3C402ff0117CF', '0x014002', '7F0999A2B3C402ff011701CF', '声音-', null, '23', 'Volume-', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('588', '7F0899A2B3C402ff011FCF', null, '7F0999A2B3C402ff011F01CF', '截屏', null, '23', 'ScreenShot', null, null)ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('589', '7F0899A2B3C402ff0131CF', null, '7F0999A2B3C402ff0131XXCF', '设备型号', null, '23', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('590', '7F0899A2B3C402ff0132CF', null, '7F0999A2B3C402ff0132XXCF', 'DSPLAYSTATE显示状态', 'XX指的当前通道号：XX=01：模拟电视，=1C：数字电视，=02：视频1，=03：视频2，=0B：S-端子，=10：分量，=17：高清1，（HDMI1）=18：高清2(HDMI2)，=19(内置电脑ops)=00：VGA(电脑1)=11(电脑2)', '23', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('591', '7F0899A2B3C402ff0133CF', null, '7F0999A2B3C402ff0133XXCF', 'VOLUME声音（如静音则返回0）', 'XX表示当前音量值（XX为十六进制数，范围为：00～64）。如：XX=20：当前音量值为32（十进制），=00：当前为静音。', '23', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('592', '7F0899A2B3C402ff0134CF', null, '7F0999A2B3C402ff0134XXCF', '音响模式', 'XX指的音响模式：XX=00：标准，=01：音乐，=02：运动，=03：新闻，=04：用户，=05：临场1，=06：临场2', '23', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('593', '7F0899A2B3C402ff0135CF', null, '7F0999A2B3C402ff0135XXCF', 'ECOMODE节能模式（31～37）有返回参数用返回码中的第11位保存需要返回的参数，返回给上位机', 'XX指的节能模式：XX=00：标准，=01：节能，=02：自动', '23', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('594', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', 'TOUCHSTATE触控状态', 'XX指的触摸模式：XX=00：电脑模式，=01：android模式(电脑模式下)', '23', 'TouchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('595', '7F0899A2B3C402ff0137CF', null, '7F0999A2B3C402ff0137XXCF', '开关机状态', 'XX指的开关机状态：XX=01：开机状态', '23', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('596', '7F0899A2B3C402ff0105CF', '0x013004', '7F0999A2B3C402ff010501CF', '音响模式-标准', '音响模式：XX=00：标准', '23', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('597', '7F0899A2B3C402ff0105CF', '0x013005', '7F0999A2B3C402ff010501CF', '音响模式-音乐', '音响模式：XX=01：音乐', '23', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('598', '7F0899A2B3C402ff0105CF', '0x013006', '7F0999A2B3C402ff010501CF', '音响模式-运动', '音响模式：XX=02：运动', '23', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('599', '7F0899A2B3C402ff0105CF', '0x013007', '7F0999A2B3C402ff010501CF', '音响模式-新闻', '音响模式：XX=03：新闻', '23', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('600', '7F0899A2B3C402ff0105CF', '0x013008', '7F0999A2B3C402ff010501CF', '音响模式-用户', '音响模式：XX=04：用户', '23', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('601', '7F0899A2B3C402ff0105CF', '0x013009', '7F0999A2B3C402ff010501CF', '音响模式-临场1', '音响模式：XX=05：临场1', '23', 'Spot1', '05', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('602', '7F0899A2B3C402ff0105CF', '0x013010', '7F0999A2B3C402ff010501CF', '音响模式-临场2', '音响模式：XX=06：临场2', '23', 'Spot2', '06', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('603', '7F0899A2B3C402ff0116CF', '0x016003', '7F0999A2B3C402ff011601CF', '远程节能-标准', '节能模式：XX=00：标准', '23', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('604', '7F0899A2B3C402ff0116CF', '0x016004', '7F0999A2B3C402ff011601CF', '远程节能-节能', '节能模式：XX=01：节能', '23', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('605', '7F0899A2B3C402ff0116CF', '0x016005', '7F0999A2B3C402ff011601CF', '远程节能-自动', '节能模式：XX=02：自动', '23', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('606', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', '触控状态-电脑模式', '触摸功能：XX=00：电脑模式', '23', 'computer', '00', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('607', '7F0899A2B3C402ff0136CF', null, '7F0999A2B3C402ff0136XXCF', '触控状态-android模式', '触摸功能：XX=01：android模式', '23', 'android', '01', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('608', '7F0899A2B3C402ff0108CF', '0x012001', '7F0999A2B3C402ff010B01CF', '01:模拟电视', '信号源切换：XX=01:模拟电视', '23', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('609', '7F0899A2B3C402ff010FCF', '0x012002', '7F0999A2B3C402ff010B01CF', '1C:数字电视', '信号源切换：XX=1C:数字电视', '23', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('610', '7F0899A2B3C402ff010BCF', '0x012005', '7F0999A2B3C402ff010B01CF', '信号源切换：XX=0B:S端子', '信号源切换：XX=0B:S端子', '23', 'STerminal', '0B', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('611', '7F0899A2B3C402ff011CCF', '0x012016', '7F0999A2B3C402ff011C01CF', '主页', '信号源切换：XX = 22：主页', '23', 'Home', '22', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('612', '7F0899A2B3C402ff0100CF', '0x001001', '7E0999A2B3C402ff010001CF', '开机', null, '24', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('613', '7F0899A2B3C402ff0101CF', '0x001002', '7E0999A2B3C402ff010101CF', '关机', null, '24', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('614', '7F0899A2B3C402ff0102CF', null, '7E0999A2B3C402ff010201CF', '静音', null, '24', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('615', '7F0899A2B3C402ff0103CF', '0x012001', '7E0999A2B3C402ff010301CF', 'TV', '模拟电视XX=00', '24', 'ATV', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('616', '7F0899A2B3C402ff0104CF', '0x012012', '7E0999A2B3C402ff010401CF', '电脑VGA', '电脑VGA XX= 06：电脑', '24', 'VGA', '06', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('617', '7F0899A2B3C402ff0106CF', '0x012021', '7E0999A2B3C402ff010601CF', 'AV', '视频XX= 08：视频', '24', 'VIDEO', '08', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('618', '7F0899A2B3C402ff0107CF', '0x012007', '7E0999A2B3C402ff010701CF', 'HDMI1', ' XX = 03', '24', 'HDMI1', '03', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('619', '7F0899A2B3C402ff0122CF', '0x012008', '7E0999A2B3C402ff012201CF', 'HDMI2', 'XX= 04：HDMI2', '24', 'HDMI2', '04', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('620', '7F0899A2B3C402ff0123CF', '0x012017', '7E0999A2B3C402ff012301CF', 'HDMI3', 'XX= 05：HDMI3', '24', 'HDMI3', '05', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('621', '7F0899A2B3C402ff0124CF', '0x012011', '7E0999A2B3C402ff012401CF', 'OPS电脑(内置电脑)', '02：OPS电脑', '24', 'OPS', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('622', '7F0899A2B3C402ff0109CF', '0x012019', '7E0999A2B3C402ff010901CF', 'Multi-media', 'XX=09多媒体', '24', 'Media', '09', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('623', '7F0899A2B3C402ff010CCF', '0x014002', '7E0999A2B3C402ff010C01CF', 'V-', '声音-', '24', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('624', '7F0899A2B3C402ff010DCF', '0x014001', '7E0999A2B3C402ff010D01CF', 'V+', '声音+', '24', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('625', '7F0899A2B3C402ff0125CF', '0x012002', '7E0999A2B3C402ff012501CF', 'DTV', '数字电视xx=01', '24', 'DTV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('626', '7F0899A2B3C402ff0136CF', null, '7E0999A2B3C402ff0136XXCF', '开关机状态', 'XX指的开关机状态：XX=00关机状态\r\nXX = 01：开机状态', '24', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('627', '7F0899A2B3C402ff0134CF', '0x013004', '7E0999A2B3C402ff0134XXCF', '音响模式', '标准', '24', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('628', '7F0899A2B3C402ff0134CF', '0x013005', '7E0999A2B3C402ff0134XXCF', '音响模式', '音乐', '24', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('629', '7F0899A2B3C402ff0134CF', '0x013011', '7E0999A2B3C402ff0134XXCF', '音响模式', '电影', '24', 'Movie', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('630', '7F0899A2B3C402ff0134CF', '0x013006', '7E0999A2B3C402ff0134XXCF', '音响模式', '运动', '24', 'Sport', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('631', '7F0899A2B3C402ff0134CF', '0x013008', '7E0999A2B3C402ff0134XXCF', '音响模式', '用户', '24', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('632', '7F0899A2B3C402ff0135CF', null, '7E0999A2B3C402ff0135XXCF', 'ECOMODE  节能模式', 'XX指的节能模式：\r\nXX = 00：标准，\r\n      = 01：节能，\r\n      = 02：自动  ', '24', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('633', '7F0899A2B3C402ff0134CF', null, '7E0999A2B3C402ff0134XXCF', '音响模式', 'XX=00:标准,XX=01:音乐,XX=02:电影,XX=03:运动XX=04:用户.', '24', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('634', '7F0899A2B3C402ff0133CF', null, '7E0999A2B3C402ff0133XXCF', 'VOLUME声音（如静音则返回0）', 'XX 表示当前音量值（XX为十六进制数，范围为：00～64）。如：\r\nXX = 20：当前音量值为32（十进制），\r\n      = 00：当前为静音。', '24', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('635', '7F0899A2B3C402ff0132CF', null, '7E0999A2B3C402ff0132XXCF', 'DSPLAYSTATE显示状态', 'XX = 00：模拟电视， XX= 01：数字电视，\r\nXX= 02：OPS电脑，   XX = 03：HDMI1\r\nXX= 04：HDMI2，     XX= 05：HDMI3，\r\nXX= 06：电脑，     XX= 07：分量，\r\nXX= 08：视频,     XX =09:多媒体', '24', 'DisplayState', '', null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('636', '7F0899A2B3C402ff0131CF', null, '7E0999A2B3C402ff0131XXCF', '设备型号', 'XX指的主IC的型号：\r\nXX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '24', 'Mosel', '03', null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('637', '7F0899A2B3C402ff0126CF', null, '7E0999A2B3C402ff012601CF', '节能', '节能设置命令', '24', 'Energy', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('638', '7F0899A2B3C402ff0135CF', '0x016003', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '标准', '24', 'Normal', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('639', '7F0899A2B3C402ff0135CF', '0x016004', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '节能', '24', 'PowerSaving', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('640', '7F0899A2B3C402ff0135CF', '0x016005', '7E0999A2B3C402ff0135XXCF', 'ECOMODE节能模式', '自动', '24', 'Auto', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('641', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '25', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('642', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '25', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('643', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '25', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('644', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '25', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('645', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '25', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('646', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '25', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('647', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '25', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('648', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '25', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('649', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '25', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('650', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '25', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('651', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '25', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('652', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '25', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('653', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '25', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('654', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '25', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('655', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('656', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('657', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '25', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('658', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '25', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('659', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '25', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('660', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '25', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('661', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '25', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('662', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '25', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('663', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '25', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('664', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('665', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '25', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('666', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '25', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('667', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '25', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('668', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('669', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('670', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '25', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('671', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '25', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('672', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '25', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('673', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '25', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('674', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '25', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('675', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '25', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('676', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('677', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '25', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('678', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '27', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('679', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视X = 01：模拟电视', '27', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('680', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI1 xx=17：高清1', '27', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('681', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2 xx=18', '27', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('682', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI3 xx=20', '27', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('683', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑） xx = 19：OPS', '27', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('684', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1   00：VGA1', '27', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('685', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视= 1C：数字电视', '27', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('686', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量  = 10：分量', '27', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('687', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 = 02：视频1', '27', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('688', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '27', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('689', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '27', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('690', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('691', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('692', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '27', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('693', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '27', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('694', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '27', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('695', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '27', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('696', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '27', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('697', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '27', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('698', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '27', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('699', '7F0899A2B3C402FF0305CF', '0x013009', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：05##临场1', '27', 'Spot1', '05', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('700', '7F0899A2B3C402FF0306CF', '0x013010', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：06##临场2', '27', 'Spot2', '06', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('701', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('702', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '27', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('703', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '27', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('704', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '27', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('705', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('706', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('707', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '27', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('708', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '27', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('709', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '27', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('710', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '27', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('711', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '27', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('712', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '27', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('713', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('714', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '27', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('715', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '26', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('716', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视 01：模拟电视', '26', 'ATV', '01', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('717', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI1  = 17：高清1', '26', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('718', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2   = 18：高清2', '26', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('719', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI3  =20：高清3', '26', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('720', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）    = 19：OPS', '26', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('721', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1  = 00：VGA1', '26', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('722', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视  = 1C：数字电视', '26', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('723', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量  = 10：分量', '26', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('724', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 = 02：视频1', '26', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('725', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '26', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('726', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '26', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('727', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('728', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('729', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', '', '26', 'Home', '', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('730', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '26', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('731', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00##标准', '26', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('732', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01##音乐', '26', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('733', '7F0899A2B3C402FF0302CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02##运动', '26', 'Sport', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('734', '7F0899A2B3C402FF0303CF', '0x013007', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03##新闻', '26', 'News', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('735', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04##用户', '26', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('736', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('737', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00##标准', '26', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('738', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01##节能', '26', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('739', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02##自动', '26', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('740', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('741', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('742', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '26', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('743', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '26', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('744', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '26', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('745', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '26', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('746', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '26', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('747', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '26', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('748', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('749', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '26', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('750', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '26', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('751', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '25', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('752', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '23', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('753', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '23', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('754', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '23', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('755', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '23', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('756', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '23', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('757', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '23', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('758', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '23', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('759', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '23', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('760', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '23', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('761', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '23', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('762', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '25', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('763', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '25', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('764', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '25', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('765', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '25', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('766', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '25', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('767', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '25', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('768', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '25', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('769', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '25', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('770', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '25', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('771', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '25', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('772', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '26', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('773', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '26', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('774', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '26', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('775', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '26', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('776', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '26', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('777', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '26', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('778', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '26', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('779', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '26', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('780', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '26', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('781', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '26', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('782', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '27', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('783', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '27', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('784', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '27', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('785', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '27', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('786', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '27', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('787', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '27', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('788', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '27', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('789', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '27', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('790', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '27', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('791', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '27', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('792', '7F0899A2B3C402FF0136CF', '', '7F0999A2B3C402FF0136XXCF', '触控状态-电脑模式', '触摸功能：XX=00：电脑模式', '25', 'computer', '00', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('793', '7F0899A2B3C402FF0136CF', '', '7F0999A2B3C402FF0136XXCF', '触控状态-android模式', '触摸功能：XX=01：android模式', '25', 'android', '01', 'Touch') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('794', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '23', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('795', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '24', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('796', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '23', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('797', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '25', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('798', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '26', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('799', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '27', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('810', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '23', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('811', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '23', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('812', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '23', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('813', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '24', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('814', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '24', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('815', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '24', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('816', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '25', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('817', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '25', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('818', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '25', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('819', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '26', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('820', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '26', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('821', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '26', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('822', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '27', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('823', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '27', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('824', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '27', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('827', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '28', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('828', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', null, '28', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('829', '7F0899A2B3C402FF0102CF', null, '7F0999A2B3C402FF010201CF', '静音', null, '28', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('830', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '28', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('831', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '28', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('832', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '28', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('833', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '28', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('834', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '28', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('835', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '28', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('836', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '28', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('837', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '28', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('838', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '28', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('839', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', null, '28', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('840', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', null, '28', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('841', '7F0899A2B3C402FF0119CF', null, '7F0999A2B3C402FF011901CF', '频道-', null, '28', 'TVChannel-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('842', '7F0899A2B3C402FF011ACF', null, '7F0999A2B3C402FF011A01CF', '频道+', null, '28', 'TVChannel+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('843', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', null, '28', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('844', '7F0899A2B3C402FF011FCF', null, '7F0999A2B3C402FF011F01CF', '截屏', null, '28', 'ScreenShot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('845', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '28', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('846', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01音乐', '28', 'Music', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('847', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '28', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('848', '7F0899A2B3C402FF0303CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03运动', '28', 'Sport', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('849', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04用户', '28', 'Custom', '4', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('850', '7F0899A2B3C402FF05XXCF', null, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '28', 'VolumeSetting', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('851', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '28', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('852', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '28', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('853', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '28', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('854', '7F0899A2B3C402FF013DCF', null, '7F0999A2B3C402FF013D01CF', '查询固件版本号', null, '28', null, null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('855', '7F0899A2B3C402FF013ECF', null, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', null, '28', 'ChannelSearch', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('856', '7F0899A2B3C402FF0131CF', null, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '28', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('857', '7F0899A2B3C402FF0132CF', null, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', null, '28', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('858', '7F0899A2B3C402FF0133CF', null, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', null, '28', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('859', '7F0899A2B3C402FF0134CF', null, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', null, '28', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('860', '7F0899A2B3C402FF0135CF', null, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', null, '28', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('861', '7F0899A2B3C402FF0137CF', null, '7F0999A2B3C402FF0137XXCF', '开关机状态', null, '28', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('862', '7F0899A2B3C402FF013CCF', null, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', null, '28', 'TVState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('863', '7F0899A2B3C402FF04XXCF', null, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', null, '28', 'TVChannel', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('864', '7F0899A2B3C402FF0121CF', null, '7F0999A2B3C402FF012101CF', '数字1', null, '28', 'Number1', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('865', '7F0899A2B3C402FF0122CF', null, '7F0999A2B3C402FF012201CF', '数字2', null, '28', 'Number2', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('866', '7F0899A2B3C402FF0123CF', null, '7F0999A2B3C402FF012301CF', '数字3', null, '28', 'Number3', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('867', '7F0899A2B3C402FF0124CF', null, '7F0999A2B3C402FF012401CF', '数字4', null, '28', 'Number4', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('868', '7F0899A2B3C402FF0125CF', null, '7F0999A2B3C402FF012501CF', '数字5', null, '28', 'Number5', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('869', '7F0899A2B3C402FF0126CF', null, '7F0999A2B3C402FF012601CF', '数字6', null, '28', 'Number6', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('870', '7F0899A2B3C402FF0127CF', null, '7F0999A2B3C402FF012701CF', '数字7', null, '28', 'Number7', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('871', '7F0899A2B3C402FF0128CF', null, '7F0999A2B3C402FF012801CF', '数字8', null, '28', 'Number8', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('872', '7F0899A2B3C402FF0129CF', null, '7F0999A2B3C402FF012901CF', '数字9', null, '28', 'Number9', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('873', '7F0899A2B3C402FF012ACF', null, '7F0999A2B3C402FF012A01CF', '数字0', null, '28', 'Number0', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('874', '7F0899A2B3C402FF0146CF', null, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '28', 'MCUAddress', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('875', '7F0899A2B3C402FF0148CF', null, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '28', 'Brightness-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('876', '7F0899A2B3C402FF0147CF', null, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '28', 'Brightness+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('877', '7F0899A2B3C402FF09XXCF', null, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '28', 'SetBrightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('878', '7F0899A2B3C402FF0149CF', null, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '28', 'Brightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('879', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '29', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('880', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', null, '29', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('881', '7F0899A2B3C402FF0102CF', null, '7F0999A2B3C402FF010201CF', '静音', null, '29', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('882', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '29', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('883', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '29', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('884', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '29', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('885', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '29', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('886', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '29', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('887', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '29', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('888', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '29', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('889', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '29', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('890', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '29', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('891', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', null, '29', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('892', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', null, '29', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('893', '7F0899A2B3C402FF0119CF', null, '7F0999A2B3C402FF011901CF', '频道-', null, '29', 'TVChannel-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('894', '7F0899A2B3C402FF011ACF', null, '7F0999A2B3C402FF011A01CF', '频道+', null, '29', 'TVChannel+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('895', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', null, '29', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('896', '7F0899A2B3C402FF011FCF', null, '7F0999A2B3C402FF011F01CF', '截屏', null, '29', 'ScreenShot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('897', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '29', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('898', '7F0899A2B3C402FF0301CF', '0x013014', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01会议', '29', 'Meeting', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('899', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '29', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('900', '7F0899A2B3C402FF0303CF', '0x013013', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03教室', '29', 'Classroom', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('901', '7F0899A2B3C402FF05XXCF', null, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '29', 'VolumeSetting', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('902', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '29', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('903', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '29', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('904', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '29', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('905', '7F0899A2B3C402FF013DCF', null, '7F0999A2B3C402FF013D01CF', '查询固件版本号', null, '29', null, null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('906', '7F0899A2B3C402FF013ECF', null, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', null, '29', 'ChannelSearch', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('907', '7F0899A2B3C402FF0131CF', null, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '29', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('908', '7F0899A2B3C402FF0132CF', null, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', null, '29', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('909', '7F0899A2B3C402FF0133CF', null, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', null, '29', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('910', '7F0899A2B3C402FF0134CF', null, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', null, '29', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('911', '7F0899A2B3C402FF0135CF', null, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', null, '29', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('912', '7F0899A2B3C402FF0137CF', null, '7F0999A2B3C402FF0137XXCF', '开关机状态', null, '29', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('913', '7F0899A2B3C402FF013CCF', null, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', null, '29', 'TVState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('914', '7F0899A2B3C402FF04XXCF', null, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', null, '29', 'TVChannel', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('915', '7F0899A2B3C402FF0121CF', null, '7F0999A2B3C402FF012101CF', '数字1', null, '29', 'Number1', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('916', '7F0899A2B3C402FF0122CF', null, '7F0999A2B3C402FF012201CF', '数字2', null, '29', 'Number2', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('917', '7F0899A2B3C402FF0123CF', null, '7F0999A2B3C402FF012301CF', '数字3', null, '29', 'Number3', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('918', '7F0899A2B3C402FF0124CF', null, '7F0999A2B3C402FF012401CF', '数字4', null, '29', 'Number4', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('919', '7F0899A2B3C402FF0125CF', null, '7F0999A2B3C402FF012501CF', '数字5', null, '29', 'Number5', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('920', '7F0899A2B3C402FF0126CF', null, '7F0999A2B3C402FF012601CF', '数字6', null, '29', 'Number6', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('921', '7F0899A2B3C402FF0127CF', null, '7F0999A2B3C402FF012701CF', '数字7', null, '29', 'Number7', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('922', '7F0899A2B3C402FF0128CF', null, '7F0999A2B3C402FF012801CF', '数字8', null, '29', 'Number8', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('923', '7F0899A2B3C402FF0129CF', null, '7F0999A2B3C402FF012901CF', '数字9', null, '29', 'Number9', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('924', '7F0899A2B3C402FF012ACF', null, '7F0999A2B3C402FF012A01CF', '数字0', null, '29', 'Number0', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('925', '7F0899A2B3C402FF0146CF', null, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '29', 'MCUAddress', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('926', '7F0899A2B3C402FF0148CF', null, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '29', 'Brightness-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('927', '7F0899A2B3C402FF0147CF', null, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '29', 'Brightness+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('928', '7F0899A2B3C402FF09XXCF', null, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '29', 'SetBrightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('929', '7F0899A2B3C402FF0149CF', null, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '29', 'Brightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('930', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '28', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('931', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '28', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('932', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '28', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('933', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '29', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('934', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '29', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('935', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '29', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('936', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', '', '30', 'Boot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('937', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', '', '30', 'ShutDown', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('938', '7F0899A2B3C402FF0102CF', '', '7F0999A2B3C402FF010201CF', '静音', '', '30', 'Mute', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('939', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '30', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('940', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '30', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('941', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '30', 'VGA1', '00', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('942', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '30', 'VIDEO1', '02', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('943', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', '', '30', 'Volume-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('944', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', '', '30', 'Volume+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('945', '7F0899A2B3C402FF0119CF', '', '7F0999A2B3C402FF011901CF', '频道-', '', '30', 'TVChannel-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('946', '7F0899A2B3C402FF011ACF', '', '7F0999A2B3C402FF011A01CF', '频道+', '', '30', 'TVChannel+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('947', '7F0899A2B3C402FF011FCF', '', '7F0999A2B3C402FF011F01CF', '截屏', '', '30', 'ScreenShot', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('948', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00：标准', '30', 'Standard', '00', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('949', '7F0899A2B3C402FF0301CF', '0x013005', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01：音乐', '30', 'Music', '01', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('950', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02：影院', '30', 'Movie', '02', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('951', '7F0899A2B3C402FF0303CF', '0x013006', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03：运动', '30', 'Sport', '03', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('952', '7F0899A2B3C402FF0304CF', '0x013008', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：04：用户', '30', 'Custom', '04', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('953', '7F0899A2B3C402FF05XXCF', '', '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '30', 'VolumeSetting', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('954', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00：标准', '30', 'Normal', '00', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('955', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01：节能', '30', 'PowerSaving', '01', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('956', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02：自动', '30', 'Auto', '02', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('957', '7F0899A2B3C402FF013DCF', '', '7F0999A2B3C402FF013D01CF', '查询固件版本号', '', '30', '', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('958', '7F0899A2B3C402FF013ECF', '', '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', '', '30', 'ChannelSearch', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('959', '7F0899A2B3C402FF0131CF', '', '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '30', 'Model', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('960', '7F0899A2B3C402FF0132CF', '', '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', '', '30', 'DisplayState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('961', '7F0899A2B3C402FF0133CF', '', '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', '', '30', 'Volume', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('962', '7F0899A2B3C402FF0134CF', '', '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', '', '30', 'AudioState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('963', '7F0899A2B3C402FF0135CF', '', '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', '', '30', 'EnergyState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('964', '7F0899A2B3C402FF0137CF', '', '7F0999A2B3C402FF0137XXCF', '开关机状态', '', '30', 'SwitchState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('965', '7F0899A2B3C402FF013CCF', '', '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', '', '30', 'TVState', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('966', '7F0899A2B3C402FF04XXCF', '', '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', '', '30', 'TVChannel', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('967', '7F0899A2B3C402FF0121CF', '', '7F0999A2B3C402FF012101CF', '数字1', '', '30', 'Number1', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('968', '7F0899A2B3C402FF0122CF', '', '7F0999A2B3C402FF012201CF', '数字2', '', '30', 'Number2', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('969', '7F0899A2B3C402FF0123CF', '', '7F0999A2B3C402FF012301CF', '数字3', '', '30', 'Number3', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('970', '7F0899A2B3C402FF0124CF', '', '7F0999A2B3C402FF012401CF', '数字4', '', '30', 'Number4', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('971', '7F0899A2B3C402FF0125CF', '', '7F0999A2B3C402FF012501CF', '数字5', '', '30', 'Number5', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('972', '7F0899A2B3C402FF0126CF', '', '7F0999A2B3C402FF012601CF', '数字6', '', '30', 'Number6', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('973', '7F0899A2B3C402FF0127CF', '', '7F0999A2B3C402FF012701CF', '数字7', '', '30', 'Number7', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('974', '7F0899A2B3C402FF0128CF', '', '7F0999A2B3C402FF012801CF', '数字8', '', '30', 'Number8', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('975', '7F0899A2B3C402FF0129CF', '', '7F0999A2B3C402FF012901CF', '数字9', '', '30', 'Number9', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('976', '7F0899A2B3C402FF012ACF', '', '7F0999A2B3C402FF012A01CF', '数字0', '', '30', 'Number0', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('977', '7F0899A2B3C402FF0146CF', '', '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '30', 'MCUAddress', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('978', '7F0899A2B3C402FF0157CF', '', '7F0999A2B3C402FF015701CF', '大屏锁定/解锁屏幕', '00:解锁，01:锁定', '30', 'ScreenLock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('979', '7F0899A2B3C402FF0158CF', '', '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', '00:解锁，01:锁定', '30', 'ScreenLockMode', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('980', '7F0899A2B3C402FF0148CF', '', '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '30', 'Brightness-', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('981', '7F0899A2B3C402FF0147CF', '', '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '30', 'Brightness+', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('982', '7F0899A2B3C402FF09XXCF', '', '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '30', 'SetBrightness', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('983', '7F0899A2B3C402FF0149CF', '', '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '30', 'Brightness', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('984', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', '19:OPS电脑(内置电脑)', '信号源切换：XX=19:OPS电脑(内置电脑)', '30', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('985', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定/解锁屏幕', '00:解锁，01:锁定', '30', 'ScreenLock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('986', '7F0899A2B3C402FF0158CF', '', '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', '00:解锁，01:锁定', '30', 'ScreenLockMode', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('987', '7F0899A2B3C402FF013ACF', '0x012019', '7F0899A2B3C402FF013A01CF', 'Multi-media', 'XX=09多媒体', '30', 'Media', '09', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('988', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', '', '30', 'ScreenUnlock', '', '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('989', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '36', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('990', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', null, '36', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('991', '7F0899A2B3C402FF0102CF', null, '7F0999A2B3C402FF010201CF', '静音', null, '36', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('992', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '36', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('993', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '36', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('994', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '36', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('995', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '36', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('996', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '36', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('997', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '36', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('998', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '36', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('999', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '36', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1000', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '36', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1001', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', null, '36', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1002', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', null, '36', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1003', '7F0899A2B3C402FF0119CF', null, '7F0999A2B3C402FF011901CF', '频道-', null, '36', 'TVChannel-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1004', '7F0899A2B3C402FF011ACF', null, '7F0999A2B3C402FF011A01CF', '频道+', null, '36', 'TVChannel+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1005', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', null, '36', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1006', '7F0899A2B3C402FF011FCF', null, '7F0999A2B3C402FF011F01CF', '截屏', null, '36', 'ScreenShot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1007', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '36', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1008', '7F0899A2B3C402FF0301CF', '0x013014', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01会议', '36', 'Meeting', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1009', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '36', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1010', '7F0899A2B3C402FF0303CF', '0x013013', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03教室', '36', 'Classroom', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1011', '7F0899A2B3C402FF05XXCF', null, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '36', 'VolumeSetting', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1012', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '36', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1013', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '36', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1014', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '36', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1015', '7F0899A2B3C402FF013DCF', null, '7F0999A2B3C402FF013D01CF', '查询固件版本号', null, '36', null, null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1016', '7F0899A2B3C402FF013ECF', null, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', null, '36', 'ChannelSearch', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1017', '7F0899A2B3C402FF0131CF', null, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '36', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1018', '7F0899A2B3C402FF0132CF', null, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', null, '36', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1019', '7F0899A2B3C402FF0133CF', null, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', null, '36', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1020', '7F0899A2B3C402FF0134CF', null, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', null, '36', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1021', '7F0899A2B3C402FF0135CF', null, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', null, '36', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1022', '7F0899A2B3C402FF0137CF', null, '7F0999A2B3C402FF0137XXCF', '开关机状态', null, '36', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1023', '7F0899A2B3C402FF013CCF', null, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', null, '36', 'TVState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1024', '7F0899A2B3C402FF04XXCF', null, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', null, '36', 'TVChannel', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1025', '7F0899A2B3C402FF0121CF', null, '7F0999A2B3C402FF012101CF', '数字1', null, '36', 'Number1', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1026', '7F0899A2B3C402FF0122CF', null, '7F0999A2B3C402FF012201CF', '数字2', null, '36', 'Number2', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1027', '7F0899A2B3C402FF0123CF', null, '7F0999A2B3C402FF012301CF', '数字3', null, '36', 'Number3', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1028', '7F0899A2B3C402FF0124CF', null, '7F0999A2B3C402FF012401CF', '数字4', null, '36', 'Number4', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1029', '7F0899A2B3C402FF0125CF', null, '7F0999A2B3C402FF012501CF', '数字5', null, '36', 'Number5', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1030', '7F0899A2B3C402FF0126CF', null, '7F0999A2B3C402FF012601CF', '数字6', null, '36', 'Number6', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1031', '7F0899A2B3C402FF0127CF', null, '7F0999A2B3C402FF012701CF', '数字7', null, '36', 'Number7', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1032', '7F0899A2B3C402FF0128CF', null, '7F0999A2B3C402FF012801CF', '数字8', null, '36', 'Number8', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1033', '7F0899A2B3C402FF0129CF', null, '7F0999A2B3C402FF012901CF', '数字9', null, '36', 'Number9', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1034', '7F0899A2B3C402FF012ACF', null, '7F0999A2B3C402FF012A01CF', '数字0', null, '36', 'Number0', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1035', '7F0899A2B3C402FF0146CF', null, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '36', 'MCUAddress', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1036', '7F0899A2B3C402FF0148CF', null, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '36', 'Brightness-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1037', '7F0899A2B3C402FF0147CF', null, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '36', 'Brightness+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1038', '7F0899A2B3C402FF09XXCF', null, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '36', 'SetBrightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1039', '7F0899A2B3C402FF0149CF', null, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '36', 'Brightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1040', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '36', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1041', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '36', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1042', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '36', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1043', '7F08AA7E05007BA1Y0CECF', '', '7F0BAA7E0800FBA1Y0Y1Y2Y3CECF', '中控设置投影机状态查询', 'Y0.投影机省电模式Y0= 0X00： 标准Y0= 0X01: 省电Y1.投影机待机模式Y1= 0X02:正常Y1= 0X03:节能Y2.投影机电源Y2= 0X04:开机Y2= 0X05:关机', '19', 'ProjectorState', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1044', '7F08AA7E05007BA301CECF', '0x015005', '7F08AA7E0500FAA3YCECF', '一体机一键锁定', '一键锁定成功y=0，失败y=1', '19', 'Lock', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1045', '7F08AA7E05007BA302CECF', '0x015006', '7F08AA7E0500FAA3YCECF', '一体机一键解锁', '一键锁定成功y=0，失败y=1', '19', 'UnLock', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1046', '7F08AA7E05007BA303CECF', '', '7F08AA7E0500FAA3YCECF', '一体机一键开机', '一键锁定成功y=0，失败y=1', '19', 'PowerOn', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1047', '7F08AA7E05007BA304CECF', '', '7F08AA7E0500FAA3YCECF', '一体机一键关机', '一键锁定成功y=0，失败y=1', '19', 'PowerOff', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1048', '7F08AA7E05007BA305CECF', NULL, '7F08AA7E0500FAA3YCECF', '查询一体机锁定状态', '锁定：y=0，未锁定：y=1', '19', 'LockedState', NULL, NULL) ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1049', '7F08AA7E05007A5200CECF', NULL, '7F08AA7E0500FA5201CECF', '心跳', '心跳', '19', 'HeartBeat', NULL, NULL)ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1050', '7F08AA7E05007AA001CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量,音量查询', '中控控制音箱音量，音量查询', '37', 'Volume', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1051', '7F08AA7E05007AA002CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量减小', '37', 'Volume-', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1052', '7F08AA7E05007AA003CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量静音', '37', 'Mute', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1053', '7F08AA7E05007AA004CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量取消静音', '37', 'UnMute', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1054', '7F08AA7E05007AA005CECF', '', '7F08AA7E0500FAA0YCECF', '中控控制音箱音量', '中控控制音箱音量增加，Y =  0X00~0X10,音量值；0X10为静音', '37', 'Volume+', '', '')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1055', '7F08AA7E05007AA100CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', 'Y0,Y = 0X00： 标准Y0,Y = 0X01: 省电', '37', 'Standard', '00', 'Energy')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1056', '7F08AA7E05007AA101CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', 'Y0,Y = 0X00： 标准Y0,Y = 0X01: 省电', '37', 'PowerSaving', '01', 'Energy')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1057', '7F08AA7E05007AA102CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机待机模式', '投影机待机模式Y0,Y = 0X02:正常Y0,Y = 0X03:节能', '37', 'Normal', '02', 'Standby') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1058', '7F08AA7E05007AA103CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机待机模式', '投影机待机模式Y0,Y = 0X02:正常Y0,Y = 0X03:节能', '37', 'LowPower', '03', 'Standby') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1059', '7F08AA7E05007AA104CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', '投影机电源Y0,Y = 0X04:开机Y0,Y = 0X05:关机', '37', 'TurnOn', '04', 'Turn') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1060', '7F08AA7E05007AA105CECF', '', '7F08AA7E0500FAA1YCECF', '中控设置投影机省电模式', '投影机电源Y0,Y = 0X04:开机Y0,Y = 0X05:关机', '37', 'TurnOff', '05', 'Turn') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1061', '7F08AA7E05007BA1Y0CECF', '', '7F0BAA7E0800FBA1Y0Y1Y2Y3CECF', '中控设置投影机状态查询', 'Y0.投影机省电模式Y0= 0X00： 标准Y0= 0X01: 省电Y1.投影机待机模式Y1= 0X02:正常Y1= 0X03:节能Y2.投影机电源Y2= 0X04:开机Y2= 0X05:关机', '37', 'ProjectorState', '', '')ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1062', '7F08AA7E05007BA401CECF', '0x015005', '7F08AA7E0500FAA4YCECF', '一体机一键锁定', '一键锁定成功y=0，失败y=1', '37', 'Lock', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1063', '7F08AA7E05007BA402CECF', '0x015006', '7F08AA7E0500FAA4YCECF', '一体机一键解锁', '一键锁定成功y=0，失败y=1', '37', 'UnLock', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1064', '7F08AA7E05007BA403CECF', '0x001001', '7F08AA7E0500FAA4YCECF', '一体机一键开机', '一键锁定成功y=0，失败y=1', '37', 'PowerOn', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1065', '7F08AA7E05007BA404CECF', '0x001002', '7F08AA7E0500FAA4YCECF', '一体机一键关机', '一键锁定成功y=0，失败y=1', '37', 'PowerOff', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1066', '7F08AA7E05007BA405CECF', '', '7F08AA7E0500FAA4YCECF', '查询一体机锁定状态', '锁定：y=1，未锁定：y=0', '37', 'LockedState', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1067', '7F08AA7E05007A5200CECF', '', '7F08AA7E0500FA5201CECF', '心跳', '心跳', '37', 'HeartBeat', '', '') ON DUPLICATE KEY UPDATE dspec_id=VALUES(dspec_id)##
INSERT INTO `device_command_code` VALUES ('1068', '7F0899A2B3C402FF0100CF', '0x001001', '7F0999A2B3C402FF010001CF0', '开机', null, '38', 'Boot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1069', '7F0899A2B3C402FF0101CF', '0x001002', '7F0999A2B3C402FF010101CF', '关机', null, '38', 'ShutDown', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1070', '7F0899A2B3C402FF0102CF', null, '7F0999A2B3C402FF010201CF', '静音', null, '38', 'Mute', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1071', '7F0899A2B3C402FF0108CF', '0x012001', '7F0999A2B3C402FF010801CF', 'ATV', '模拟电视XX = 01：模拟电视', '38', 'ATV', '1', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1072', '7F0899A2B3C402FF010ACF', '0x012007', '7F0999A2B3C402FF010A01CF', 'HDMI1', 'HDMI117：高清1', '38', 'HDMI1', '17', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1073', '7F0899A2B3C402FF010BCF', '0x012008', '7F0999A2B3C402FF010B01CF', 'HDMI2', 'HDMI2= 18：高清2', '38', 'HDMI2', '18', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1074', '7F0899A2B3C402FF010CCF', '0x012017', '7F0999A2B3C402FF010C01CF', 'HDMI3', 'HDMI320：高清3', '38', 'HDMI3', '20', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1075', '7F0899A2B3C402FF0138CF', '0x012011', '7F0999A2B3C402FF013801CF', 'HDMI4', 'HDMI4（ops电脑）= 19：OPS', '38', 'OPS', '19', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1076', '7F0899A2B3C402FF010DCF', '0x012009', '7F0999A2B3C402FF010D01CF', '电脑1', '电脑1 00：VGA1', '38', 'VGA1', '0', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1077', '7F0899A2B3C402FF010FCF', '0x012002', '7F0999A2B3C402FF010F01CF', 'DTV', '数字电视1C：数字电视', '38', 'DTV', '1C', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1078', '7F0899A2B3C402FF0110CF', '0x012006', '7F0999A2B3C402FF011001CF', '分量', '分量 = 10：分量', '38', 'Weight', '10', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1079', '7F0899A2B3C402FF0111CF', '0x012003', '7F0999A2B3C402FF011101CF', '视频1', '视频1 02：视频1', '38', 'VIDEO1', '2', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1080', '7F0899A2B3C402FF0117CF', '0x014002', '7F0999A2B3C402FF011701CF', '声音-', null, '38', 'Volume-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1081', '7F0899A2B3C402FF0118CF', '0x014001', '7F0999A2B3C402FF011801CF', '声音+', null, '38', 'Volume+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1082', '7F0899A2B3C402FF0119CF', null, '7F0999A2B3C402FF011901CF', '频道-', null, '38', 'TVChannel-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1083', '7F0899A2B3C402FF011ACF', null, '7F0999A2B3C402FF011A01CF', '频道+', null, '38', 'TVChannel+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1084', '7F0899A2B3C402FF011CCF', '0x012016', '7F0999A2B3C402FF011C01CF', '主页', null, '38', 'Home', '30', 'Channel') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1085', '7F0899A2B3C402FF011FCF', null, '7F0999A2B3C402FF011F01CF', '截屏', null, '38', 'ScreenShot', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1086', '7F0899A2B3C402FF0300CF', '0x013004', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：00标准', '38', 'Standard', '0', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1087', '7F0899A2B3C402FF0301CF', '0x013014', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：01会议', '38', 'Meeting', '1', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1088', '7F0899A2B3C402FF0302CF', '0x013011', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：02影院', '38', 'Movie', '2', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1089', '7F0899A2B3C402FF0303CF', '0x013013', '7F0899A2B3C402FF03XX01CF', '设置音响模式', 'XX表示各种模式：03教室', '38', 'Classroom', '3', 'Audio') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1090', '7F0899A2B3C402FF05XXCF', null, '7F0999A2B3C402FF05XX01CF', '设置音量', 'XX表示音量值（0-100），对应十六进制：00-64', '38', 'VolumeSetting', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1091', '7F0899A2B3C402FF0600CF', '0x016003', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：00标准', '38', 'Normal', '0', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1092', '7F0899A2B3C402FF0601CF', '0x016004', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：01节能', '38', 'PowerSaving', '1', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1093', '7F0899A2B3C402FF0602CF', '0x016005', '7F0999A2B3C402FF06XX01CF', '设置节能模式', 'XX表示各种模式：02自动', '38', 'Auto', '2', 'Energy') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1094', '7F0899A2B3C402FF013DCF', null, '7F0999A2B3C402FF013D01CF', '查询固件版本号', null, '38', null, null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1095', '7F0899A2B3C402FF013ECF', null, '7F0999A2B3C402FF013E01CF', '一键ATV+DTV自动搜台', null, '38', 'ChannelSearch', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1096', '7F0899A2B3C402FF0131CF', null, '7F0999A2B3C402FF0131XXCF', '设备型号', 'XX = 01：801 , = 02：901.1  , =03：V69.5，=04：918，=05：901.6，=06：V69.8', '38', 'Model', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1097', '7F0899A2B3C402FF0132CF', null, '7F0999A2B3C402FF0132XXCF', 'DSPLAYSTATE显示状态XX = 01：模拟电视， = 1C：数字电视，= 02：视频1，= 03：视频2，= 0B：S视频，= 10：分量， = 17：高清1， = 18：高清2，= 00：V', null, '38', 'DisplayState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1098', '7F0899A2B3C402FF0133CF', null, '7F0999A2B3C402FF0133XXCF', 'VOLUME声音（如静音则返回0）', null, '38', 'Volume', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1099', '7F0899A2B3C402FF0134CF', null, '7F0999A2B3C402FF0134XXCF', '音响模式（状态）', null, '38', 'AudioState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1100', '7F0899A2B3C402FF0135CF', null, '7F0999A2B3C402FF0135XXCF', 'ECOMODE  节能模式', null, '38', 'EnergyState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1101', '7F0899A2B3C402FF0137CF', null, '7F0999A2B3C402FF0137XXCF', '开关机状态', null, '38', 'SwitchState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1102', '7F0899A2B3C402FF013CCF', null, '7F0999A2B3C402FF013CXXCF', '查询ATV/DTV频道号', null, '38', 'TVState', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1103', '7F0899A2B3C402FF04XXCF', null, '7F0999A2B3C402FF04XX01CF', 'ATV/设置ATV/DTV频道号XX表示台号（1-255），对应十六进制：01-FF', null, '38', 'TVChannel', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1104', '7F0899A2B3C402FF0121CF', null, '7F0999A2B3C402FF012101CF', '数字1', null, '38', 'Number1', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1105', '7F0899A2B3C402FF0122CF', null, '7F0999A2B3C402FF012201CF', '数字2', null, '38', 'Number2', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1106', '7F0899A2B3C402FF0123CF', null, '7F0999A2B3C402FF012301CF', '数字3', null, '38', 'Number3', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1107', '7F0899A2B3C402FF0124CF', null, '7F0999A2B3C402FF012401CF', '数字4', null, '38', 'Number4', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1108', '7F0899A2B3C402FF0125CF', null, '7F0999A2B3C402FF012501CF', '数字5', null, '38', 'Number5', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1109', '7F0899A2B3C402FF0126CF', null, '7F0999A2B3C402FF012601CF', '数字6', null, '38', 'Number6', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1110', '7F0899A2B3C402FF0127CF', null, '7F0999A2B3C402FF012701CF', '数字7', null, '38', 'Number7', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1111', '7F0899A2B3C402FF0128CF', null, '7F0999A2B3C402FF012801CF', '数字8', null, '38', 'Number8', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1112', '7F0899A2B3C402FF0129CF', null, '7F0999A2B3C402FF012901CF', '数字9', null, '38', 'Number9', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1113', '7F0899A2B3C402FF012ACF', null, '7F0999A2B3C402FF012A01CF', '数字0', null, '38', 'Number0', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1114', '7F0899A2B3C402FF0146CF', null, '7F0999A2B3XXXXXXXXXXXXCF', '获得MCU地址', '返回值从第六个开始11个止', '38', 'MCUAddress', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1115', '7F0899A2B3C402FF0148CF', null, '7F0999A2B3C402FF014801CF', '亮度-', '背光亮度-', '38', 'Brightness-', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1116', '7F0899A2B3C402FF0147CF', null, '7F0999A2B3C402FF014701CF', '亮度+', '背光亮度+', '38', 'Brightness+', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1117', '7F0899A2B3C402FF09XXCF', null, '7F0999A2B3C402FF09XX01CF', '设置亮度值', '设置亮度值', '38', 'SetBrightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1118', '7F0899A2B3C402FF0149CF', null, '7F0999A2B3C402FF0149XXCF', '获得亮度值', '获得亮度值', '38', 'Brightness', null, null) ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1119', '7F0899A2B3C402FF0157CF', '0x015005', '7F0999A2B3C402FF015701CF', '大屏锁定', null, '38', 'ScreenLock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1120', '7F0899A2B3C402FF0157CF', '0x015006', '7F0999A2B3C402FF015701CF', '解锁屏幕', null, '38', 'ScreenUnlock', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##
INSERT INTO `device_command_code` VALUES ('1121', '7F0899A2B3C402FF0158CF', null, '7F0999A2B3C402FF015801CF', '获得大屏屏锁状态', null, '38', 'ScreenLockMode', null, '') ON DUPLICATE KEY UPDATE code_id=VALUES(code_id)##


CREATE TABLE  IF NOT EXISTS `device_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备名称（班级名称）',
  `device_ip` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备ip地址',
  `log_time` int(11) DEFAULT NULL COMMENT '日志时间',
  `log_desc` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '日志描述',
  `device_type` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备类型，如 hhrec,hhtc',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=261 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='设备错误日志'##


CREATE TABLE  IF NOT EXISTS`device_host` (
  `host_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备详情id',
  `host_name` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备名称',
  `host_ipaddr` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备ip',
  `host_serialno` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '不同设备类型对应的类，如录播设备：com.hht.device.OnvifDevice',
  `dspec_id` int(11) DEFAULT NULL COMMENT '设备类型id',
  `host_desc` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '0 代表简易录播，1代表精品录播',
  `host_systype` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '用于区分班牌终端的系统类型',
  `host_username` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备账号名称（录播用的）',
  `host_password` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备密码（录播用的）',
  `host_mac` varchar(60) COLLATE utf8_general_ci DEFAULT NULL COMMENT 'mac 地址',
  `host_hhtcmac` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '大屏的mac地址',
  `host_factory` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '厂家标识',
  `host_port` varchar(60) COLLATE utf8_general_ci DEFAULT '' COMMENT '设备端口号',
  `host_channel` varchar(60) COLLATE utf8_general_ci DEFAULT '' COMMENT '设备通道号',
  `isTour` int(11) DEFAULT NULL COMMENT '是否支持巡课',
  `mcu_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`host_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT = '注册设备详情表'##


-- ----------------------------
-- Table structure for deviceconfig
-- ----------------------------

CREATE TABLE  IF NOT EXISTS  `device_config` (
  `id` int(11) NOT NULL COMMENT '设备镜头配置信息id',
  `connect_param` varchar(200) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备型号',
  `name` varchar(45) COLLATE utf8_general_ci DEFAULT NULL COMMENT '镜头名称',
  `main` varchar(45) COLLATE utf8_general_ci DEFAULT NULL COMMENT '主镜头名称',
  `sub` varchar(45) COLLATE utf8_general_ci DEFAULT NULL COMMENT '子镜头名称',
  `is_ptz` int(11) DEFAULT NULL COMMENT '是否支持云台操作 0 不支持 1 支持',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci  COMMENT = '设备镜头配置信息表'##

-- ----------------------------
-- Records of deviceconfig
-- ----------------------------
INSERT INTO `device_config` VALUES ('1', 'C6F0SjZ3N0P0L0', '教师全景', 'MainProfileToken', 'SubProfileToken', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('2', 'HL-ZF0400', '教师全景', 'FrontToken', 'FrontSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('3', 'HL-ZF0400', '学生全景', 'StudentsMainToken', 'StudentsSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('4', 'HL-ZF0400', '教师特写', 'TearcherToken', 'TearcherSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('5', 'HL-ZF0400', 'VGA', 'ComputerToken', 'ComputerSubToken', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('6', 'HL-ZF0400', '电影模式', 'MovieToken', 'MovieSubToken', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('7', '12345678', '教师全景', 'platform', 'platform', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('8', '12345678', '学生全景', 'students', 'students', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('9', '12345678', '学生特写', 'student', 'student', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('10', '12345678', '电影模式', 'movie', 'movie', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('11', '12345678', '教师特写', 'teacher', 'teacher', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('12', '12345678', '板书', 'blackboard', 'blackboard', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('13', '12345678', 'VGA', 'computer', 'computer', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('14', '12345678', '通道一', 'channel1', 'channel1', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('15', '12345678', '通道二', 'channel2', 'channel2', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('16', '12345678', '通道三', 'channel3', 'channel3', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('17', '12345678', '通道四', 'channel4', 'channel4', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('18', '12345678', '通道五', 'channel5', 'channel5', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('19', '12345678', '通道六', 'channel6', 'channel6', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('20', '12345678', '通道七', 'channel7', 'channel7', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('21', '12345678', '通道八', 'channel8', 'channel8', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('22', 'HL-ZJ0500', '电影模式', 'movie', 'movie', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('23', 'HL-ZJ0500', '教师全景', 'platform', 'platform', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('24', 'HL-ZJ0500', '学生全景', 'students', 'students', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('25', 'HL-ZJ0500', '学生特写', 'student', 'student', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('26', 'HL-ZJ0500', '教师特写', 'teacher', 'teacher', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('27', 'HL-ZJ0500', '板书', 'blackboard', 'blackboard', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('28', 'HL-ZJ0500', 'VGA', 'computer', 'computer', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('29', 'TBOX-1234', '教师全景', '4', '4_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('30', 'TBOX-1234', '教师特写', '5', '5_1', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('31', 'TBOX-1234', '学生全景', '6', '6_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('32', 'TBOX-1234', '学生特写', '7', '7_1', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('33', 'TBOX-1234', '板书-1', '8', '8_1', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('34', 'TBOX-1234', '电影模式', '0', '0_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('35', 'TBOX-1234', 'HDMI/VGA', '9', '9_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('36', 'TBOX-1234', '测试一', '10', '10_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('37', 'TBOX-1234', '测试二', '11', '11_1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('38', 'HL-ZF0500', '电影模式', 'MovieToken', 'MovieSubToken', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('39', 'HL-ZF0500', '教师全景', 'FrontToken', 'FrontSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('40', 'HL-ZF0500', '学生全景', 'StudentsMainToken', 'StudentsSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('41', 'HL-ZF0500', '教师特写', 'TearcherToken', 'TearcherSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('42', 'HL-ZF0500', '学生特写', 'StudentToken', 'StudentSubToken', '1')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('43', 'HL-ZF0500', 'VGA', 'ComputerToken', 'ComputerSubToken', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('44', 'honghe-wbox', '教师全景', 'wbReource1', 'wbReource1', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('45', 'honghe-wbox', '教师特写', 'wbReource2', 'wbReource2', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('46', 'honghe-wbox', '学生全景', 'wbReource3', 'wbReource3', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('47', 'honghe-wbox', '学生特写', 'wbReource4', 'wbReource4', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('48', 'honghe-wbox', '板书-1', 'wbReource5', 'wbReource5', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('49', 'honghe-wbox', '电影模式', 'wbMovie_Main', 'wbMovie_Sub', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('50', 'honghe-wbox', '自定义', 'wbReource6', 'wbReource6', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('51', 'honghe-wbox', '电脑1', 'wbReource7', 'wbReource7', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##
INSERT INTO `device_config` VALUES ('52', 'honghe-wbox', '电脑2', 'wbReource8', 'wbReource8', '0')ON DUPLICATE KEY UPDATE id=VALUES(id)##


CREATE TABLE IF NOT EXISTS `device`(
  `device_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备镜头id' ,
  `device_name` varchar(50) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备镜头名称',
  `device_maintoken` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备主镜头名称',
  `device_subtoken` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备子镜头名称',
  `host_id` int(11) DEFAULT NULL COMMENT '设备id',
  `device_mainstream` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备主镜头流地址',
  `device_substream` varchar(255) COLLATE utf8_general_ci DEFAULT NULL COMMENT '设备子镜头流地址',
  PRIMARY KEY (`device_id`),
  KEY `FK_ipi2a5qoc7yc1xn1l9hkwon1n` (`host_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci  COMMENT = '设备镜头详情列表'##


DROP TRIGGER IF EXISTS `ins_check_copy`;
CREATE TRIGGER `ins_check_copy` AFTER INSERT ON `device_command_code` FOR EACH ROW BEGIN
  DECLARE cnt INT;
  SET @cnt = (select count(dspec_id) from device_cmdcode_update where dspec_id = NEW.dspec_id);
  if(@cnt = 0) THEN
    insert into device_cmdcode_update(dspec_id,cmdcode_update) VALUES (NEW.dspec_id,UUID());
  else

    update device_cmdcode_update set cmdcode_update = UUID() where dspec_id = NEW.dspec_id;
  END IF;

END
;
DROP TRIGGER IF EXISTS `upd_check_copy`;

CREATE TRIGGER `upd_check_copy` AFTER UPDATE ON `device_command_code` FOR EACH ROW BEGIN
  DECLARE cnt INT;
  SET @cnt = (select count(dspec_id) from device_cmdcode_update where dspec_id = OLD.dspec_id);
  if(@cnt = 0) THEN
    insert into device_cmdcode_update(dspec_id,cmdcode_update) VALUES ( OLD.dspec_id,UUID());
  else

    update device_cmdcode_update set cmdcode_update = UUID() where dspec_id = OLD.dspec_id;
  END IF;

END
;
DROP TRIGGER IF EXISTS `del_check_copy`;
CREATE TRIGGER `del_check_copy` AFTER DELETE ON `device_command_code` FOR EACH ROW BEGIN
  DECLARE cnt INT;
  SET @cnt = (select count(dspec_id) from device_cmdcode_update where dspec_id = OLD.dspec_id);
  if(@cnt > 0) THEN
    update device_cmdcode_update set cmdcode_update = UUID() where dspec_id = OLD.dspec_id;
  END IF;

END;
