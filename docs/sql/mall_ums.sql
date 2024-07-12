use mall_ums;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `member_id` bigint NULL DEFAULT NULL COMMENT 'Member ID',
                               `consignee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Consignee Name',
                               `consignee_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Consignee Mobile',
                               `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Province',
                               `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'City',
                               `area` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Area',
                               `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Detail Address',
                               `zip_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Zip Code',
                               `defaulted` tinyint NULL DEFAULT NULL COMMENT 'Default Address Flag',
                               `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                               `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=190 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `gender` tinyint(1) NULL DEFAULT NULL,
                              `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `birthday` date NULL DEFAULT NULL,
                              `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `openid` char(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `session_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `status` tinyint(1) NULL DEFAULT 1,
                              `point` int NULL DEFAULT 0 COMMENT 'Member Points',
                              `deleted` tinyint(1) NULL DEFAULT 0,
                              `create_time` datetime NULL DEFAULT NULL,
                              `update_time` datetime NULL DEFAULT NULL,
                              `balance` bigint NULL DEFAULT 1000000000,
                              `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `language` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `branch_id` bigint NOT NULL COMMENT 'Branch Transaction ID',
                            `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Global Transaction ID',
                            `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Undo Log Context, such as serialization',
                            `rollback_info` longblob NOT NULL COMMENT 'Rollback Info',
                            `log_status` int NOT NULL COMMENT '0: Normal Status, 1: Defense Status',
                            `log_created` datetime(6) NOT NULL COMMENT 'Create Datetime',
                            `log_modified` datetime(6) NOT NULL COMMENT 'Modify Datetime',
                            UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='AT Transaction Mode Undo Table' ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
