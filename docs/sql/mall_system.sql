use mall_system;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Department name',
                             `parent_id` bigint NULL DEFAULT 0 COMMENT 'Parent node ID',
                             `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Parent node ID path',
                             `sort` int NULL DEFAULT 0 COMMENT 'Display order',
                             `status` tinyint NULL DEFAULT 1 COMMENT 'Status (1: Normal; 0: Disabled)',
                             `deleted` tinyint NULL DEFAULT 0 COMMENT 'Logical delete flag (1: Deleted; 0: Not deleted)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Department table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 'Mall Technology', 0, '0', 1, 1, 0, NULL, NULL);
INSERT INTO `sys_dept` VALUES (2, 'Research and Development Department', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');
INSERT INTO `sys_dept` VALUES (3, 'Testing Department', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                             `type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Dictionary type code',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Dictionary item name',
                             `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Dictionary item value',
                             `sort` int NULL DEFAULT 0 COMMENT 'Sorting',
                             `status` tinyint NULL DEFAULT 0 COMMENT 'Status (1: Normal; 0: Disabled)',
                             `defaulted` tinyint NULL DEFAULT 0 COMMENT 'Default flag (1: Yes; 0: No)',
                             `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Remark',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Dictionary data table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', 'Male', '1', 1, 1, 0, NULL, '2019-05-05 13:07:52', '2022-06-12 23:20:39');
INSERT INTO `sys_dict` VALUES (2, 'gender', 'Female', '2', 2, 1, 0, NULL, '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (3, 'gender', 'Unknown', '0', 1, 1, 0, NULL, '2020-10-17 08:09:31', '2020-10-17 08:09:31');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key ',
                                  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Type name',
                                  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Type code',
                                  `status` tinyint(1) NULL DEFAULT 0 COMMENT 'Status (0: Normal; 1: Disabled)',
                                  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `type_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Dictionary type table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'Gender', 'gender', 1, NULL, '2019-12-06 19:03:32', '2022-06-12 16:21:28');
INSERT INTO `sys_dict_type` VALUES (2, 'Authorization Method', 'grant_type', 1, NULL, '2020-10-17 08:09:50', '2021-01-31 09:48:24');
INSERT INTO `sys_dict_type` VALUES (3, 'Microservice List', 'micro_service', 1, NULL, '2021-06-17 00:13:43', '2021-06-17 00:17:22');
INSERT INTO `sys_dict_type` VALUES (4, 'Request Method', 'request_method', 1, NULL, '2021-06-17 00:18:07', '2021-06-17 00:18:07');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `parent_id` bigint NULL DEFAULT NULL COMMENT 'Parent menu ID',
                             `type` tinyint NULL DEFAULT NULL COMMENT 'Menu type (1: Menu; 2: Directory; 3: External link; 4: Button)',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Menu name',
                             `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Route path (browser address bar path)',
                             `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Component path (Vue page full path, omit .vue suffix)',
                             `perm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Button permission identifier',
                             `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Menu icon',
                             `sort` int NULL DEFAULT 0 COMMENT 'Sorting',
                             `visible` tinyint(1) NULL DEFAULT 1 COMMENT 'Status (0: Disabled; 1: Enabled)',
                             `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Redirect path',
                             `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                             `always_show` tinyint NULL DEFAULT NULL COMMENT 'Whether only one child route of the directory always shows (1: Yes, 0: No)',
                             `keep_alive` tinyint NULL DEFAULT NULL COMMENT 'Whether to enable page cache for the menu (1: Yes, 0: No)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Menu management' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 2, 'System Management', '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (2, 1, 1, 'User Management', 'user', 'system/user/index', NULL, 'user', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (3, 1, 1, 'Role Management', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (4, 1, 1, 'Menu Management', 'menu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (5, 0, 2, 'Medical Beauty Management', '/health', 'Layout', NULL, 'health', 2, 1, '/health/medItem', NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (6, 5, 1, 'Medical Item Management', 'medItem', 'health/medItem/index', NULL, 'medItem', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (7, 5, 1, 'Medical Project Management', 'medProject', 'health/medProject/index', NULL, 'medProject', 2, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (8, 5, 1, 'Medical Group Management', 'medGroup', 'health/medGroup/index', NULL, 'medGroup', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (9, 0, 2, 'Order Management', '/order', 'Layout', NULL, 'order', 3, 1, '/order/order', NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (10, 9, 1, 'Order Query', 'order', 'order/order/index', NULL, 'order', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (11, 9, 1, 'Refund Management', 'refund', 'order/refund/index', NULL, 'refund', 2, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (12, 9, 1, 'Logistics Management', 'logistics', 'order/logistics/index', NULL, 'logistics', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
                             `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Role name',
                             `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Role description',
                             `status` tinyint NULL DEFAULT 0 COMMENT 'Status (0: Normal; 1: Disabled)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Role table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'Administrator', 'Super administrator, unlimited authority', 0, '2021-12-16 11:18:04', '2021-12-16 11:18:04');
INSERT INTO `sys_role` VALUES (2, 'Manager', 'Business manager, full authority to manage business modules', 0, '2021-12-16 11:18:19', '2021-12-16 11:18:19');
INSERT INTO `sys_role` VALUES (3, 'User', 'Normal user, basic access and operation permissions', 0, '2021-12-16 11:18:31', '2021-12-16 11:18:31');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                  `role_id` bigint NOT NULL COMMENT 'Role ID',
                                  `menu_id` bigint NOT NULL COMMENT 'Menu ID',
                                  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Role menu association table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 9);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Username',
                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Password',
                             `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Salt',
                             `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Avatar address',
                             `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Email address',
                             `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Mobile phone number',
                             `status` tinyint NULL DEFAULT 0 COMMENT 'Status (0: Normal; 1: Disabled)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '06ee317d6a6976ab45e95605c67b6020', '123456', '', 'admin@youlai.com', '18888888888', 0, '2021-12-16 11:22:17', '2021-12-16 11:22:17');
INSERT INTO `sys_user` VALUES (2, 'manager', 'd18f4c3e045ea79b1942f27e109e97ac', '123456', '', 'manager@youlai.com', '18899999999', 0, '2021-12-16 11:22:46', '2021-12-16 11:22:46');
INSERT INTO `sys_user` VALUES (3, 'user', '42cb2a7fe48b699a8f53ebf1e0871b72', '123456', '', 'user@youlai.com', '18866666666', 0, '2021-12-16 11:23:21', '2021-12-16 11:23:21');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` bigint NOT NULL COMMENT 'User ID',
                                  `role_id` bigint NOT NULL COMMENT 'Role ID',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User role association table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);
COMMIT;
