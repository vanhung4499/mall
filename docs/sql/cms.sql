-- ----------------------------
-- Table structure for cms_help
-- ----------------------------
DROP TABLE IF EXISTS `cms_help`;
CREATE TABLE `cms_help`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `category_id` bigint(20) NULL DEFAULT NULL,
                             `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `show_status` int(1) NULL DEFAULT NULL,
                             `create_time` datetime NULL DEFAULT NULL,
                             `read_count` int(1) NULL DEFAULT NULL,
                             `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Help table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_help_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_help_category`;
CREATE TABLE `cms_help_category`  (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Category Icon',
                                      `help_count` int(11) NULL DEFAULT NULL COMMENT 'Number of help topics',
                                      `show_status` int(2) NULL DEFAULT NULL,
                                      `sort` int(11) NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Help Category Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_member_report
-- ----------------------------
DROP TABLE IF EXISTS `cms_member_report`;
CREATE TABLE `cms_member_report`  (
                                      `id` bigint(20) NULL DEFAULT NULL,
                                      `report_type` int(1) NULL DEFAULT NULL COMMENT 'Report Type: 0->Product Review; 1->Topic Content; 2->User Comment',
                                      `report_member_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Reporter',
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `report_object` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `report_status` int(1) NULL DEFAULT NULL COMMENT 'Report Status: 0->Untreated; 1->Treated',
                                      `handle_status` int(1) NULL DEFAULT NULL COMMENT 'Processing Result: 0->Invalid; 1->Valid; 2->Malicious',
                                      `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User Report Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_preference_area
-- ----------------------------
DROP TABLE IF EXISTS `cms_prefrence_area`;
CREATE TABLE `cms_preference_area`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `pic` varbinary(500) NULL DEFAULT NULL COMMENT 'Display Picture',
                                       `sort` int(11) NULL DEFAULT NULL,
                                       `show_status` int(1) NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Preference Area' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_preference_area_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `cms_preference_area_product_relation`;
CREATE TABLE `cms_preference_area_product_relation`  (
                                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                        `preference_area_id` bigint(20) NULL DEFAULT NULL,
                                                        `product_id` bigint(20) NULL DEFAULT NULL,
                                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Preference Area and Product Relation Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_subject
-- ----------------------------
DROP TABLE IF EXISTS `cms_subject`;
CREATE TABLE `cms_subject`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `category_id` bigint(20) NULL DEFAULT NULL,
                                `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Subject main picture',
                                `product_count` int(11) NULL DEFAULT NULL COMMENT 'Associated product count',
                                `recommend_status` int(1) NULL DEFAULT NULL,
                                `create_time` datetime NULL DEFAULT NULL,
                                `collect_count` int(11) NULL DEFAULT NULL,
                                `read_count` int(11) NULL DEFAULT NULL,
                                `comment_count` int(11) NULL DEFAULT NULL,
                                `album_pics` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Album pictures separated by commas',
                                `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `show_status` int(1) NULL DEFAULT NULL COMMENT 'Display status: 0->Not displayed; 1->Displayed',
                                `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                `forward_count` int(11) NULL DEFAULT NULL COMMENT 'Forward count',
                                `category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Subject category name',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Subject Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_subject_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_subject_category`;
CREATE TABLE `cms_subject_category`  (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Category Icon',
                                         `subject_count` int(11) NULL DEFAULT NULL COMMENT 'Subject Count',
                                         `show_status` int(2) NULL DEFAULT NULL,
                                         `sort` int(11) NULL DEFAULT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Subject Category Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_subject_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_subject_comment`;
CREATE TABLE `cms_subject_comment`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `subject_id` bigint(20) NULL DEFAULT NULL,
                                        `member_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `member_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `create_time` datetime NULL DEFAULT NULL,
                                        `show_status` int(1) NULL DEFAULT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Subject Comment Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_subject_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `cms_subject_product_relation`;
CREATE TABLE `cms_subject_product_relation`  (
                                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                 `subject_id` bigint(20) NULL DEFAULT NULL,
                                                 `product_id` bigint(20) NULL DEFAULT NULL,
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Subject Product Relation Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_topic
-- ----------------------------
DROP TABLE IF EXISTS `cms_topic`;
CREATE TABLE `cms_topic`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `category_id` bigint(20) NULL DEFAULT NULL,
                              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `create_time` datetime NULL DEFAULT NULL,
                              `start_time` datetime NULL DEFAULT NULL,
                              `end_time` datetime NULL DEFAULT NULL,
                              `attend_count` int(11) NULL DEFAULT NULL COMMENT 'Number of participants',
                              `attention_count` int(11) NULL DEFAULT NULL COMMENT 'Number of followers',
                              `read_count` int(11) NULL DEFAULT NULL,
                              `award_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Award name',
                              `attend_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Participation type',
                              `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Topic content',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Topic Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_topic_category
-- ----------------------------
DROP TABLE IF EXISTS `cms_topic_category`;
CREATE TABLE `cms_topic_category`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Category Icon',
                                       `subject_count` int(11) NULL DEFAULT NULL COMMENT 'Number of Subjects',
                                       `show_status` int(2) NULL DEFAULT NULL,
                                       `sort` int(11) NULL DEFAULT NULL,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Topic Category Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for cms_topic_comment
-- ----------------------------
DROP TABLE IF EXISTS `cms_topic_comment`;
CREATE TABLE `cms_topic_comment`  (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `member_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `topic_id` bigint(20) NULL DEFAULT NULL,
                                      `member_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                      `create_time` datetime NULL DEFAULT NULL,
                                      `show_status` int(1) NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Topic Comment Table' ROW_FORMAT = DYNAMIC;
