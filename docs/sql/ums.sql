-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Avatar',
                              `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email',
                              `nick_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Nickname',
                              `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Note',
                              `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation Time',
                              `login_time` datetime NULL DEFAULT NULL COMMENT 'Last Login Time',
                              `status` int(1) NULL DEFAULT 1 COMMENT 'Account Status: 0->Disabled; 1->Enabled',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Admin User Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_login_log`;
CREATE TABLE `ums_admin_login_log`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `admin_id` bigint(20) NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `user_agent` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Browser Login Type',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 413 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Admin User Login Log Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_admin_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_permission_relation`;
CREATE TABLE `ums_admin_permission_relation`  (
                                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                  `admin_id` bigint(20) NULL DEFAULT NULL,
                                                  `permission_id` bigint(20) NULL DEFAULT NULL,
                                                  `type` int(1) NULL DEFAULT NULL,
                                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Admin User and Permission Relation Table (Additional permissions beyond those defined in roles)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_admin_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation`  (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `admin_id` bigint(20) NULL DEFAULT NULL,
                                            `role_id` bigint(20) NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Admin User and Role Relation Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_growth_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_growth_change_history`;
CREATE TABLE `ums_growth_change_history`  (
                                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                              `member_id` bigint(20) NULL DEFAULT NULL,
                                              `create_time` datetime NULL DEFAULT NULL,
                                              `change_type` int(1) NULL DEFAULT NULL COMMENT 'Change type: 0->Increase; 1->Decrease',
                                              `change_count` int(11) NULL DEFAULT NULL COMMENT 'Change in points',
                                              `operate_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operator',
                                              `operate_note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operation notes',
                                              `source_type` int(1) NULL DEFAULT NULL COMMENT 'Source of points: 0->Shopping; 1->Admin modification',
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Growth Change History Record Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_integration_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_integration_change_history`;
CREATE TABLE `ums_integration_change_history`  (
                                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                   `member_id` bigint(20) NULL DEFAULT NULL,
                                                   `create_time` datetime NULL DEFAULT NULL,
                                                   `change_type` int(1) NULL DEFAULT NULL COMMENT 'Change type: 0->Increase; 1->Decrease',
                                                   `change_count` int(11) NULL DEFAULT NULL COMMENT 'Change in points',
                                                   `operate_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operator',
                                                   `operate_note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operation notes',
                                                   `source_type` int(1) NULL DEFAULT NULL COMMENT 'Source of points: 0->Shopping; 1->Admin modification',
                                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Integration Change History Record Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_integration_consume_setting
-- ----------------------------
DROP TABLE IF EXISTS `ums_integration_consume_setting`;
CREATE TABLE `ums_integration_consume_setting`  (
                                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                    `deduction_per_amount` int(11) NULL DEFAULT NULL COMMENT 'Number of points needed to deduct per unit currency',
                                                    `max_percent_per_order` int(11) NULL DEFAULT NULL COMMENT 'Maximum percentage of order value that can be covered by points',
                                                    `use_unit` int(11) NULL DEFAULT NULL COMMENT 'Minimum unit of points that can be used at a time, 100',
                                                    `coupon_status` int(1) NULL DEFAULT NULL COMMENT 'Whether it can be used with coupons; 0->No; 1->Yes',
                                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Integration Consume Setting' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `member_level_id` bigint(20) NULL DEFAULT NULL,
                               `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Username',
                               `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Password',
                               `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Nickname',
                               `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Phone number',
                               `status` int(1) NULL DEFAULT NULL COMMENT 'Account status: 0->Disabled; 1->Enabled',
                               `create_time` datetime NULL DEFAULT NULL COMMENT 'Registration time',
                               `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Avatar',
                               `gender` int(1) NULL DEFAULT NULL COMMENT 'Gender: 0->Unknown; 1->Male; 2->Female',
                               `birthday` date NULL DEFAULT NULL COMMENT 'Birthday',
                               `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'City',
                               `job` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Job',
                               `personalized_signature` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Personalized signature',
                               `source_type` int(1) NULL DEFAULT NULL COMMENT 'User source',
                               `integration` int(11) NULL DEFAULT NULL COMMENT 'Integration points',
                               `growth` int(11) NULL DEFAULT NULL COMMENT 'Growth value',
                               `luckey_count` int(11) NULL DEFAULT NULL COMMENT 'Remaining number of draws',
                               `history_integration` int(11) NULL DEFAULT NULL COMMENT 'Historical integration points',
                               PRIMARY KEY (`id`) USING BTREE,
                               UNIQUE INDEX `idx_username`(`username`) USING BTREE,
                               UNIQUE INDEX `idx_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_level
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_level`;
CREATE TABLE `ums_member_level`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     `growth_point` int(11) NULL DEFAULT NULL,
                                     `default_status` int(1) NULL DEFAULT NULL COMMENT 'Whether it is the default level: 0->No; 1->Yes',
                                     `free_freight_point` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Free freight standard',
                                     `comment_growth_point` int(11) NULL DEFAULT NULL COMMENT 'Growth value obtained each time a comment is made',
                                     `privilege_free_freight` int(1) NULL DEFAULT NULL COMMENT 'Whether there is a free postage privilege',
                                     `privilege_sign_in` int(1) NULL DEFAULT NULL COMMENT 'Whether there is a sign-in privilege',
                                     `privilege_comment` int(1) NULL DEFAULT NULL COMMENT 'Whether there is a comment reward privilege',
                                     `privilege_promotion` int(1) NULL DEFAULT NULL COMMENT 'Whether there is an exclusive event privilege',
                                     `privilege_member_price` int(1) NULL DEFAULT NULL COMMENT 'Whether there is a member price privilege',
                                     `privilege_birthday` int(1) NULL DEFAULT NULL COMMENT 'Whether there is a birthday privilege',
                                     `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Level Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_login_log`;
CREATE TABLE `ums_member_login_log`  (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `member_id` bigint(20) NULL DEFAULT NULL,
                                         `create_time` datetime NULL DEFAULT NULL,
                                         `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `login_type` int(1) NULL DEFAULT NULL COMMENT 'Login type: 0->PC; 1->android; 2->ios; 3->mini program',
                                         `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Login Record' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_member_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_member_tag_relation`;
CREATE TABLE `ums_member_member_tag_relation`  (
                                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                   `member_id` bigint(20) NULL DEFAULT NULL,
                                                   `tag_id` bigint(20) NULL DEFAULT NULL,
                                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User and Tag Relation Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_product_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_product_category_relation`;
CREATE TABLE `ums_member_product_category_relation`  (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                         `member_id` bigint(20) NULL DEFAULT NULL,
                                                         `product_category_id` bigint(20) NULL DEFAULT NULL,
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member and Product Category Relation Table (User Favorite Categories)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_receive_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_receive_address`;
CREATE TABLE `ums_member_receive_address`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `member_id` bigint(20) NULL DEFAULT NULL,
                                               `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Receiver Name',
                                               `phone_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `default_status` int(1) NULL DEFAULT NULL COMMENT 'Default Status',
                                               `post_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Postal Code',
                                               `province` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Province/Direct-controlled municipality',
                                               `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'City',
                                               `region` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Region',
                                               `detail_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Detailed Address (Street)',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Shipping Address Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_rule_setting
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_rule_setting`;
CREATE TABLE `ums_member_rule_setting`  (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `continue_sign_day` int(11) NULL DEFAULT NULL COMMENT 'Continuous Sign-in Days',
                                            `continue_sign_point` int(11) NULL DEFAULT NULL COMMENT 'Points Awarded for Continuous Sign-in',
                                            `consume_per_point` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Amount Spent to Earn 1 Point',
                                            `low_order_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Minimum Order Amount to Earn Points',
                                            `max_point_per_order` int(11) NULL DEFAULT NULL COMMENT 'Maximum Points Per Order',
                                            `type` int(1) NULL DEFAULT NULL COMMENT 'Type: 0->Point Rule; 1->Growth Rule',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Points and Growth Rules Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_statistics_info
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_statistics_info`;
CREATE TABLE `ums_member_statistics_info`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `member_id` bigint(20) NULL DEFAULT NULL,
                                               `consume_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Total Consumption Amount',
                                               `order_count` int(11) NULL DEFAULT NULL COMMENT 'Order Count',
                                               `coupon_count` int(11) NULL DEFAULT NULL COMMENT 'Coupon Count',
                                               `comment_count` int(11) NULL DEFAULT NULL COMMENT 'Comment Count',
                                               `return_order_count` int(11) NULL DEFAULT NULL COMMENT 'Return Order Count',
                                               `login_count` int(11) NULL DEFAULT NULL COMMENT 'Login Count',
                                               `attend_count` int(11) NULL DEFAULT NULL COMMENT 'Follow Count',
                                               `fans_count` int(11) NULL DEFAULT NULL COMMENT 'Fan Count',
                                               `collect_product_count` int(11) NULL DEFAULT NULL COMMENT 'Product Collection Count',
                                               `collect_subject_count` int(11) NULL DEFAULT NULL COMMENT 'Subject Collection Count',
                                               `collect_topic_count` int(11) NULL DEFAULT NULL COMMENT 'Topic Collection Count',
                                               `collect_comment_count` int(11) NULL DEFAULT NULL COMMENT 'Comment Collection Count',
                                               `invite_friend_count` int(11) NULL DEFAULT NULL COMMENT 'Invite Friend Count',
                                               `recent_order_time` datetime NULL DEFAULT NULL COMMENT 'Recent Order Time',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Statistics Info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_tag
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_tag`;
CREATE TABLE `ums_member_tag`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `finish_order_count` int(11) NULL DEFAULT NULL COMMENT 'Completed Order Count for Automatic Tagging',
                                   `finish_order_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Completed Order Amount for Automatic Tagging',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User Tag Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_member_task
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_task`;
CREATE TABLE `ums_member_task`  (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                    `growth` int(11) NULL DEFAULT NULL COMMENT 'Growth Reward',
                                    `integration` int(11) NULL DEFAULT NULL COMMENT 'Integration Reward',
                                    `type` int(1) NULL DEFAULT NULL COMMENT 'Task Type: 0->Newbie Task; 1->Daily Task',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Member Task Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `parent_id` bigint(20) NULL DEFAULT NULL COMMENT 'Parent ID',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation Time',
                             `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Menu Name',
                             `level` int(4) NULL DEFAULT NULL COMMENT 'Menu Level',
                             `sort` int(4) NULL DEFAULT NULL COMMENT 'Menu Sort Order',
                             `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Front-end Name',
                             `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Front-end Icon',
                             `hidden` int(1) NULL DEFAULT NULL COMMENT 'Front-end Hidden',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend Menu Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `pid` bigint(20) NULL DEFAULT NULL COMMENT 'Parent Permission ID',
                                   `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
                                   `value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Permission Value',
                                   `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Icon',
                                   `type` int(1) NULL DEFAULT NULL COMMENT 'Permission Type: 0->Directory; 1->Menu; 2->Button (Interface Bound Permission)',
                                   `uri` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Front-end Resource Path',
                                   `status` int(1) NULL DEFAULT NULL COMMENT 'Enable Status; 0->Disabled; 1->Enabled',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation Time',
                                   `sort` int(11) NULL DEFAULT NULL COMMENT 'Sort Order',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend User Permission Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource`  (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation Time',
                                 `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Resource Name',
                                 `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Resource URL',
                                 `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Description',
                                 `category_id` bigint(20) NULL DEFAULT NULL COMMENT 'Resource Category ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend Resource Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category`  (
                                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                          `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation Time',
                                          `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Category Name',
                                          `sort` int(4) NULL DEFAULT NULL COMMENT 'Sort Order',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Resource Category Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
                             `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Description',
                             `admin_count` int(11) NULL DEFAULT NULL COMMENT 'Number of backend users',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                             `status` int(1) NULL DEFAULT 1 COMMENT 'Enable status: 0->Disabled; 1->Enabled',
                             `sort` int(11) NULL DEFAULT 0,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend user role table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation`  (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                           `role_id` bigint(20) NULL DEFAULT NULL COMMENT 'Role ID',
                                           `menu_id` bigint(20) NULL DEFAULT NULL COMMENT 'Menu ID',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend role-menu relation table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_permission_relation`;
CREATE TABLE `ums_role_permission_relation`  (
                                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                 `role_id` bigint(20) NULL DEFAULT NULL COMMENT 'Role ID',
                                                 `permission_id` bigint(20) NULL DEFAULT NULL COMMENT 'Permission ID',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend user role and permission relation table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `role_id` bigint(20) NULL DEFAULT NULL COMMENT 'Role ID',
                                               `resource_id` bigint(20) NULL DEFAULT NULL COMMENT 'Resource ID',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 249 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Backend role-resource relation table' ROW_FORMAT = DYNAMIC;
