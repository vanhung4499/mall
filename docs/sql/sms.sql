-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `type` int(1) NULL DEFAULT NULL COMMENT 'Coupon type; 0->Full gift coupon; 1->Member gift coupon; 2->Shopping gift coupon; 3->Registration gift coupon',
                               `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `platform` int(1) NULL DEFAULT NULL COMMENT 'Use platform: 0->All; 1->Mobile; 2->PC',
                               `count` int(11) NULL DEFAULT NULL COMMENT 'Quantity',
                               `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Amount',
                               `per_limit` int(11) NULL DEFAULT NULL COMMENT 'Limit per person',
                               `min_point` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Use threshold; 0 means no threshold',
                               `start_time` datetime NULL DEFAULT NULL,
                               `end_time` datetime NULL DEFAULT NULL,
                               `use_type` int(1) NULL DEFAULT NULL COMMENT 'Use type: 0->Universal; 1->Designated category; 2->Designated product',
                               `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Note',
                               `publish_count` int(11) NULL DEFAULT NULL COMMENT 'Issued quantity',
                               `use_count` int(11) NULL DEFAULT NULL COMMENT 'Used quantity',
                               `receive_count` int(11) NULL DEFAULT NULL COMMENT 'Received quantity',
                               `enable_time` datetime NULL DEFAULT NULL COMMENT 'Date when it can be received',
                               `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Coupon code',
                               `member_level` int(1) NULL DEFAULT NULL COMMENT 'Member level that can receive: 0->Unlimited',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Coupon table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `coupon_id` bigint(20) NULL DEFAULT NULL,
                                       `member_id` bigint(20) NULL DEFAULT NULL,
                                       `coupon_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `member_nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Recipient nickname',
                                       `get_type` int(1) NULL DEFAULT NULL COMMENT 'Acquisition type: 0->Backend gift; 1->Active acquisition',
                                       `create_time` datetime NULL DEFAULT NULL,
                                       `use_status` int(1) NULL DEFAULT NULL COMMENT 'Use status: 0->Unused; 1->Used; 2->Expired',
                                       `use_time` datetime NULL DEFAULT NULL COMMENT 'Use time',
                                       `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'Order ID',
                                       `order_sn` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Order number',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       INDEX `idx_member_id`(`member_id`) USING BTREE,
                                       INDEX `idx_coupon_id`(`coupon_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Coupon usage and receipt history table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_coupon_product_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_category_relation`;
CREATE TABLE `sms_coupon_product_category_relation`  (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                         `coupon_id` bigint(20) NULL DEFAULT NULL,
                                                         `product_category_id` bigint(20) NULL DEFAULT NULL,
                                                         `product_category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product category name',
                                                         `parent_category_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Parent category name',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Coupon and product category relation table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_coupon_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_product_relation`;
CREATE TABLE `sms_coupon_product_relation`  (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                `coupon_id` bigint(20) NULL DEFAULT NULL,
                                                `product_id` bigint(20) NULL DEFAULT NULL,
                                                `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product name',
                                                `product_sn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product code',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Coupon and product relation table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_flash_promotion
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion`;
CREATE TABLE `sms_flash_promotion`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Flash sale time slot name',
                                        `start_date` date NULL DEFAULT NULL COMMENT 'Start date',
                                        `end_date` date NULL DEFAULT NULL COMMENT 'End date',
                                        `status` int(1) NULL DEFAULT NULL COMMENT 'Online/Offline status',
                                        `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Limited time purchase table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_flash_promotion_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_log`;
CREATE TABLE `sms_flash_promotion_log`  (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `member_id` int(11) NULL DEFAULT NULL,
                                            `product_id` bigint(20) NULL DEFAULT NULL,
                                            `member_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                            `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                            `subscribe_time` datetime NULL DEFAULT NULL COMMENT 'Member subscription time',
                                            `send_time` datetime NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Flash sale notification log' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_flash_promotion_product_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_product_relation`;
CREATE TABLE `sms_flash_promotion_product_relation`  (
                                                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                                         `flash_promotion_id` bigint(20) NULL DEFAULT NULL,
                                                         `flash_promotion_session_id` bigint(20) NULL DEFAULT NULL COMMENT 'Id',
                                                         `product_id` bigint(20) NULL DEFAULT NULL,
                                                         `flash_promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Flash sale price',
                                                         `flash_promotion_count` int(11) NULL DEFAULT NULL COMMENT 'Flash sale count',
                                                         `flash_promotion_limit` int(11) NULL DEFAULT NULL COMMENT 'Per-person limit',
                                                         `sort` int(11) NULL DEFAULT NULL COMMENT 'Sort',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product flash sale and product relationship table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_flash_promotion_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_flash_promotion_session`;
CREATE TABLE `sms_flash_promotion_session`  (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
                                                `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Session name',
                                                `start_time` time NULL DEFAULT NULL COMMENT 'Daily start time',
                                                `end_time` time NULL DEFAULT NULL COMMENT 'Daily end time',
                                                `status` int(1) NULL DEFAULT NULL COMMENT 'Enable status: 0->Not enabled; 1->Enabled',
                                                `create_time` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Flash sale session table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_home_advertise
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_advertise`;
CREATE TABLE `sms_home_advertise`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `type` int(1) NULL DEFAULT NULL COMMENT 'Carousel position: 0->PC home carousel; 1->app home carousel',
                                       `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `start_time` datetime NULL DEFAULT NULL,
                                       `end_time` datetime NULL DEFAULT NULL,
                                       `status` int(1) NULL DEFAULT NULL COMMENT 'Online status: 0->Offline; 1->Online',
                                       `click_count` int(11) NULL DEFAULT NULL COMMENT 'Click count',
                                       `order_count` int(11) NULL DEFAULT NULL COMMENT 'Order count',
                                       `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Link address',
                                       `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remarks',
                                       `sort` int(11) NULL DEFAULT 0 COMMENT 'Sort',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Home carousel advertisement table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_home_brand
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_brand`;
CREATE TABLE `sms_home_brand`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `brand_id` bigint(20) NULL DEFAULT NULL,
                                   `brand_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `recommend_status` int(1) NULL DEFAULT NULL,
                                   `sort` int(11) NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Home recommended brand table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_home_new_product
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_new_product`;
CREATE TABLE `sms_home_new_product`  (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `product_id` bigint(20) NULL DEFAULT NULL,
                                         `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `recommend_status` int(1) NULL DEFAULT NULL,
                                         `sort` int(1) NULL DEFAULT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'New and fresh products table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_home_recommend_product
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_recommend_product`;
CREATE TABLE `sms_home_recommend_product`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `product_id` bigint(20) NULL DEFAULT NULL,
                                               `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `recommend_status` int(1) NULL DEFAULT NULL,
                                               `sort` int(1) NULL DEFAULT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Popular recommended products table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sms_home_recommend_subject
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_recommend_subject`;
CREATE TABLE `sms_home_recommend_subject`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `subject_id` bigint(20) NULL DEFAULT NULL,
                                               `subject_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                               `recommend_status` int(1) NULL DEFAULT NULL,
                                               `sort` int(11) NULL DEFAULT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Home page recommended subjects table' ROW_FORMAT = DYNAMIC;
