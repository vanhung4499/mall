use mall_sms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert` (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT,
                              `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Advert Title',
                              `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Image URL',
                              `begin_time` datetime(0) NULL DEFAULT NULL COMMENT 'Start Time',
                              `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
                              `status` tinyint(1) NOT NULL COMMENT 'Status (1: Enabled; 0: Disabled)',
                              `sort` int(0) NULL DEFAULT NULL COMMENT 'Sort Order',
                              `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Redirect URL',
                              `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
                              `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Create Time',
                              `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update Time (has value when modified)',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Advert Table' ROW_FORMAT=Dynamic;

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon` (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                              `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Coupon Name',
                              `type` tinyint(0) NOT NULL DEFAULT 1 COMMENT 'Coupon Type (1: Full Reduction; 2: Direct Reduction; 3: Discount)',
                              `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT 'Coupon Code',
                              `platform` int(0) NULL DEFAULT NULL COMMENT 'Usage Platform (0: All Platforms; 1: APP; 2: PC)',
                              `face_value_type` tinyint(0) NULL DEFAULT NULL COMMENT 'Coupon Face Value Type',
                              `face_value` bigint(0) NULL DEFAULT NULL COMMENT 'Coupon Face Value (in cents)',
                              `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Discount',
                              `min_point` bigint(0) NULL DEFAULT NULL COMMENT 'Usage Threshold (0: No Threshold)',
                              `per_limit` int(0) NULL DEFAULT 1 COMMENT 'Limit Per Person (-1: No Limit)',
                              `validity_period_type` tinyint(0) NULL DEFAULT NULL COMMENT 'Validity Period Type (1: Days from receipt; 2: Specific Period)',
                              `validity_days` int(0) NULL DEFAULT 1 COMMENT 'Validity Days',
                              `validity_begin_time` datetime(0) NULL DEFAULT NULL COMMENT 'Validity Start Time',
                              `validity_end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Validity End Time',
                              `application_scope` tinyint(0) NULL DEFAULT NULL COMMENT 'Application Scope (0: All Products; 1: Specific Category; 2: Specific Products)',
                              `circulation` int(0) NULL DEFAULT 1 COMMENT 'Distribution Quantity (-1: Unlimited)',
                              `received_count` int(0) NULL DEFAULT 0 COMMENT 'Number of Coupons Received (statistics)',
                              `used_count` int(0) NULL DEFAULT 0 COMMENT 'Number of Coupons Used (statistics)',
                              `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Remark',
                              `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Create Time',
                              `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Modify Time',
                              `deleted` tinyint(0) NULL DEFAULT 1 COMMENT 'Logical Deletion Flag (0: Normal; 1: Deleted)',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Coupon Table' ROW_FORMAT=Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history` (
                                      `id` bigint(0) NOT NULL,
                                      `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT 'Coupon ID',
                                      `member_id` bigint(0) NULL DEFAULT NULL COMMENT 'Member ID',
                                      `member_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Member Nickname',
                                      `coupon_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Coupon Code',
                                      `get_type` tinyint(0) NULL DEFAULT NULL COMMENT 'Acquisition Type (1: Backend Addition/Deletion; 2: Active Acquisition)',
                                      `status` tinyint(0) NULL DEFAULT NULL COMMENT 'Status (0: Unused; 1: Used; 2: Expired)',
                                      `use_time` datetime(0) NULL DEFAULT NULL COMMENT 'Usage Time',
                                      `order_id` bigint(0) NULL DEFAULT NULL COMMENT 'Order ID',
                                      `order_sn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Order Number',
                                      `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Create Time',
                                      `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update Time',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_spu
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu`;
CREATE TABLE `sms_coupon_spu` (
                                  `id` bigint(0) NOT NULL AUTO_INCREMENT,
                                  `coupon_id` bigint(0) NOT NULL COMMENT 'Coupon ID',
                                  `spu_id` bigint(0) NOT NULL COMMENT 'Product ID',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1548148841429663756 CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- Table structure for sms_coupon_spu_category
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_category`;
CREATE TABLE `sms_coupon_spu_category` (
                                           `id` bigint(0) NOT NULL AUTO_INCREMENT,
                                           `coupon_id` bigint(0) NOT NULL COMMENT 'Coupon ID',
                                           `category_id` bigint(0) NOT NULL COMMENT 'Product Category ID',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=Dynamic;

-- ----------------------------
-- Records of sms_coupon_spu_category
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `branch_id` bigint(0) NOT NULL COMMENT 'Branch Transaction ID',
                            `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Global Transaction ID',
                            `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Undo Log Context, such as serialization',
                            `rollback_info` longblob NOT NULL COMMENT 'Rollback Info',
                            `log_status` int(0) NOT NULL COMMENT '0: Normal Status, 1: Defense Status',
                            `log_created` datetime(6) NOT NULL COMMENT 'Create Datetime',
                            `log_modified` datetime(6) NOT NULL COMMENT 'Modify Datetime',
                            UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='AT transaction mode undo table' ROW_FORMAT=Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
