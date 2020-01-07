
/*

Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : ssmm

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-10-30 09:40:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tsys_acces
-- ----------------------------
DROP TABLE IF EXISTS `tsys_acces`;
CREATE TABLE `tsys_acces` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '文件名称',
  `path` varchar(500) NOT NULL COMMENT '文件路径',
  `size` bigint(20) unsigned NOT NULL COMMENT '文件大小',
  `type` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `user` varchar(100) NOT NULL COMMENT '上传用户',
  `date` varchar(19) NOT NULL COMMENT '上传时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '文件描述',
  `uuid` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件信息表';

-- ----------------------------
-- Records of tsys_acces
-- ----------------------------

-- ----------------------------
-- Table structure for tsys_authority
-- ----------------------------
DROP TABLE IF EXISTS `tsys_authority`;
CREATE TABLE `tsys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `description` varchar(200) DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_authority_idx1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限信息表';

-- ----------------------------
-- Records of tsys_authority
-- ----------------------------
INSERT INTO `tsys_authority` VALUES ('1', 'A_Admin', '系统管理员');

-- ----------------------------
-- Table structure for tsys_authority_resource
-- ----------------------------
DROP TABLE IF EXISTS `tsys_authority_resource`;
CREATE TABLE `tsys_authority_resource` (
  `authority_id` int(11) NOT NULL COMMENT '权限ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限资源关系表';

-- ----------------------------
-- Records of tsys_authority_resource
-- ----------------------------
INSERT INTO `tsys_authority_resource` VALUES ('1', '1');
INSERT INTO `tsys_authority_resource` VALUES ('1', '2');
INSERT INTO `tsys_authority_resource` VALUES ('1', '4');
INSERT INTO `tsys_authority_resource` VALUES ('1', '5');
INSERT INTO `tsys_authority_resource` VALUES ('1', '6');

-- ----------------------------
-- Table structure for tsys_check_code
-- ----------------------------
DROP TABLE IF EXISTS `tsys_check_code`;
CREATE TABLE `tsys_check_code` (
  `fi_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fs_username` varchar(50) NOT NULL COMMENT '登录账号',
  `fs_code` varchar(30) NOT NULL COMMENT '唯一登录校验码',
  `fs_create_time` varchar(30) NOT NULL COMMENT '创建时间',
  `fs_update_time` varchar(30) NOT NULL COMMENT '更新时间',
  `fi_login_way` int(11) NOT NULL COMMENT '登录渠道 0--手机终端、1--客户网站、2--其它渠道',
  `fi_state` int(11) DEFAULT NULL COMMENT '是否有效 0--有效、1--无效',
  PRIMARY KEY (`fi_id`,`fs_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_check_code
-- ----------------------------

-- ----------------------------
-- Table structure for tsys_dept
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dept`;
CREATE TABLE `tsys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(50) NOT NULL COMMENT '机构名称',
  `number` char(9) NOT NULL COMMENT '机构编码',
  `description` varchar(200) DEFAULT NULL COMMENT '机构描述',
  `linkman` varchar(50) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `type` int(11) NOT NULL COMMENT '机构类别(1--公司、2--分公司、3--项目部、4----部门)',
  `seq` int(11) DEFAULT NULL COMMENT '显示顺序',
  `pid` int(11) DEFAULT NULL COMMENT '上级机构',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_dept_idx1` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Records of tsys_dept
-- ----------------------------
INSERT INTO `tsys_dept` VALUES ('1', '嘉捷信诚', '000000000', '嘉捷信诚', 'admin', '88888888', '1', '1', null);
INSERT INTO `tsys_dept` VALUES ('4', '市场部', '001000000', '管理市场部', '余波', '18684019908', '4', '2', '1');
INSERT INTO `tsys_dept` VALUES ('6', '科信科', '002000000', '科信科部门', '', '', '4', '3', '1');

-- ----------------------------
-- Table structure for tsys_login
-- ----------------------------
DROP TABLE IF EXISTS `tsys_login`;
CREATE TABLE `tsys_login` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户账号',
  `channel` varchar(20) NOT NULL COMMENT '登录渠道',
  `platform` varchar(50) DEFAULT NULL COMMENT '设备操作系统平台',
  `version` varchar(50) DEFAULT NULL COMMENT '设备操作系统版本',
  `uuid` varchar(50) DEFAULT NULL COMMENT '设备UUID',
  `model` varchar(50) DEFAULT NULL COMMENT '产品型号或产品名称',
  `manufacturer` varchar(50) DEFAULT NULL COMMENT '设备厂商',
  `date` char(10) NOT NULL COMMENT '登录日期',
  `time` char(8) NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='登录流水';

-- ----------------------------
-- Records of tsys_login
-- ----------------------------

-- ----------------------------
-- Table structure for tsys_message_code
-- ----------------------------
DROP TABLE IF EXISTS `tsys_message_code`;
CREATE TABLE `tsys_message_code` (
  `fi_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fs_phone` varchar(50) NOT NULL COMMENT '手机号码',
  `fs_code` varchar(50) NOT NULL COMMENT '短信校验码',
  `fs_input_time` varchar(30) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`fi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tsys_message_code
-- ----------------------------

-- ----------------------------
-- Table structure for tsys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `tsys_parameter`;
CREATE TABLE `tsys_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(11) NOT NULL COMMENT '参数类型(0--不可修改、1--允许修改)',
  `name` varchar(50) NOT NULL COMMENT '参数名称',
  `value` varchar(50) NOT NULL COMMENT '参数值',
  `remark` varchar(100) DEFAULT NULL COMMENT '参数说明',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_parameter_idx1` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统参数表';

-- ----------------------------
-- Records of tsys_parameter
-- ----------------------------
INSERT INTO `tsys_parameter` VALUES ('1', '1', 'default_password', '123456', '用户默认密码');

-- ----------------------------
-- Table structure for tsys_resource
-- ----------------------------
DROP TABLE IF EXISTS `tsys_resource`;
CREATE TABLE `tsys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `type` int(11) NOT NULL COMMENT '资源类型(1--菜单、2--功能)',
  `name` varchar(50) NOT NULL COMMENT '资源名称',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径',
  `menuId` varchar(50) DEFAULT NULL COMMENT '资源标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '资源图标',
  `description` varchar(200) DEFAULT NULL COMMENT '资源描述',
  `seq` int(11) DEFAULT NULL COMMENT '显示顺序',
  `pid` int(11) DEFAULT NULL COMMENT '上级资源',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_resource_idx1` (`menuId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='资源信息表';

-- ----------------------------
-- Records of tsys_resource
-- ----------------------------
INSERT INTO `tsys_resource` VALUES ('1', '1', '系统管理', '', 'menu-security', 'Hui-iconfont-system', '管理系统的部门、用户、角色、权限、资源、系统参数等信息', '1', null);
INSERT INTO `tsys_resource` VALUES ('2', '1', '部门管理', 'pages/security/dept.jsp', 'menu-security-dept', '', '管理系统中的部门信息', '1', '1');
INSERT INTO `tsys_resource` VALUES ('4', '1', '用户管理', 'pages/security/user.jsp', 'menu-security-user', '', '管理系统中的用户信息', '5', '1');
INSERT INTO `tsys_resource` VALUES ('5', '1', '角色管理', 'pages/security/role.jsp', 'menu-security-role', '', '管理系统中的角色信息', '4', '1');
INSERT INTO `tsys_resource` VALUES ('6', '1', '权限管理', 'pages/security/authority.jsp', 'menu-security-authority', '', '管理系统中的权限信息', '3', '1');
INSERT INTO `tsys_resource` VALUES ('7', '1', '资源管理', 'pages/security/resource.jsp', 'menu-security-resource', null, '管理系统中的资源信息', '2', '1');
INSERT INTO `tsys_resource` VALUES ('8', '1', '参数管理', 'pages/security/parameter.jsp', 'menu-security-parameter', null, '管理系统中的参数信息', '6', '1');

-- ----------------------------
-- Table structure for tsys_role
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role`;
CREATE TABLE `tsys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_role_idx1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of tsys_role
-- ----------------------------
INSERT INTO `tsys_role` VALUES ('1', '管理员', '阿斯蒂芬爱的色放');
INSERT INTO `tsys_role` VALUES ('2', '采集员', '啊哈哈哈');

-- ----------------------------
-- Table structure for tsys_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `tsys_role_authority`;
CREATE TABLE `tsys_role_authority` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `authority_id` int(11) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of tsys_role_authority
-- ----------------------------
INSERT INTO `tsys_role_authority` VALUES ('1', '1');
INSERT INTO `tsys_role_authority` VALUES ('2', '1');

-- ----------------------------
-- Table structure for tsys_token
-- ----------------------------
DROP TABLE IF EXISTS `tsys_token`;
CREATE TABLE `tsys_token` (
  `username` varchar(50) NOT NULL COMMENT '用户账号',
  `access_token` varchar(32) NOT NULL COMMENT '接口调用凭证',
  `platform` varchar(50) DEFAULT NULL COMMENT '设备操作系统平台(如Android、IOS)',
  `uuid` varchar(50) DEFAULT NULL COMMENT '设备UUID',
  `state` char(1) NOT NULL COMMENT '凭证状态（1--有效，2--密码修改，3--权限修改）',
  `create_datetime` char(19) NOT NULL COMMENT '凭证发放时间',
  PRIMARY KEY (`username`),
  UNIQUE KEY `tsys_token_idx1` (`access_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户凭证登记表';

-- ----------------------------
-- Records of tsys_token
-- ----------------------------

-- ----------------------------
-- Table structure for tsys_user
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user`;
CREATE TABLE `tsys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `name` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `enable` char(1) NOT NULL COMMENT '用户状态(0--禁用、1--启用)',
  `dept_id` int(11) DEFAULT NULL COMMENT '用户部门',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tsys_user_idx1` (`username`),
  UNIQUE KEY `tsys_name_password` (`username`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of tsys_user
-- ----------------------------
INSERT INTO `tsys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '', '', '1', '1');
INSERT INTO `tsys_user` VALUES ('2', 'lc', 'e10adc3949ba59abbe56e057f20f883e', '李成', '18684019908', '522678737@qq.com', '1', '4');
INSERT INTO `tsys_user` VALUES ('7', 'test', 'e10adc3949ba59abbe56e057f20f883e', '测试账户', '18684019908', '522678737@qq.com', '1', '4');

-- ----------------------------
-- Table structure for tsys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tsys_user_role`;
CREATE TABLE `tsys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of tsys_user_role
-- ----------------------------
INSERT INTO `tsys_user_role` VALUES ('1', '1');
INSERT INTO `tsys_user_role` VALUES ('7', '1');
INSERT INTO `tsys_user_role` VALUES ('7', '2');
INSERT INTO `tsys_user_role` VALUES ('2', '1');
INSERT INTO `tsys_user_role` VALUES ('2', '2');
