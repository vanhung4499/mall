use mall_oms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `order_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Order Number',
                              `total_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Total Amount of the Order (in cents)',
                              `total_quantity` int NOT NULL DEFAULT 0 COMMENT 'Total Number of Products',
                              `source` tinyint NULL DEFAULT NULL COMMENT 'Order Source (1: APP; 2: Web)',
                              `status` int NOT NULL DEFAULT 101 COMMENT 'Order Status:\r\n101->To be paid;\r\n102->User canceled;\r\n103->System canceled;\r\n201->Paid;\r\n202->Refund requested;\r\n203->Refunded;\r\n301->To be shipped;\r\n401->Shipped;\r\n501->User received;\r\n502->System received;\r\n901->Completed;',
                              `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Order Remark',
                              `member_id` bigint NOT NULL DEFAULT 0 COMMENT 'Member ID',
                              `coupon_id` bigint NOT NULL DEFAULT 0 COMMENT 'Coupon used',
                              `coupon_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Coupon Deduction Amount (in cents)',
                              `freight_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Shipping Fee (in cents)',
                              `payment_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Total Amount Payable (in cents)',
                              `payment_time` datetime NULL DEFAULT NULL COMMENT 'Payment Time',
                              `payment_method` tinyint NULL DEFAULT NULL COMMENT 'Payment Method (1: WeChat JSAPI; 2: Alipay; 3: Balance; 4: WeChat APP)',
                              `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Merchant Order Number for WeChat Pay and other third-party payment platforms',
                              `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'WeChat Payment Order Number',
                              `out_refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Merchant Refund Number',
                              `refund_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'WeChat Refund Number',
                              `delivery_time` datetime NULL DEFAULT NULL COMMENT 'Shipping Time',
                              `receive_time` datetime NULL DEFAULT NULL COMMENT 'Confirmed Receipt Time',
                              `comment_time` datetime NULL DEFAULT NULL COMMENT 'Review Time',
                              `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion [0->Normal; 1->Deleted]',
                              `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                              `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `index_order_sn`(`order_sn` ASC) USING BTREE COMMENT 'Unique Index on Order Number',
                              UNIQUE INDEX `index_otn`(`out_trade_no` ASC) USING BTREE COMMENT 'Unique Index on Merchant Order Number',
                              UNIQUE INDEX `index_ti`(`transaction_id` ASC) USING BTREE COMMENT 'Unique Index on Merchant Payment Order Number',
                              UNIQUE INDEX `index_orn`(`out_refund_no` ASC) USING BTREE COMMENT 'Unique Index on Merchant Refund Number',
                              UNIQUE INDEX `index_ri`(`refund_id` ASC) USING BTREE COMMENT 'Unique Index on Refund Number'
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424822283 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Order Details Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_delivery`;
CREATE TABLE `oms_order_delivery`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                       `order_id` bigint NOT NULL COMMENT 'Order ID',
                                       `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Logistics Company (Delivery Method)',
                                       `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Logistics Tracking Number',
                                       `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Recipient Name',
                                       `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Recipient Phone Number',
                                       `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Recipient Postal Code',
                                       `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Province/ Municipality Directly under the Central Government',
                                       `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'City',
                                       `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'District',
                                       `receiver_detail_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Detailed Address',
                                       `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Remark',
                                       `delivery_status` tinyint NULL DEFAULT 0 COMMENT 'Logistics Status [0->In Transit; 1->Received]',
                                       `delivery_time` datetime NULL DEFAULT NULL COMMENT 'Shipping Time',
                                       `receive_time` datetime NULL DEFAULT NULL COMMENT 'Confirmed Receipt Time',
                                       `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion [0->Normal; 1->Deleted]',
                                       `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                       `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Order Logistics Record Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `order_id` bigint NOT NULL COMMENT 'Order ID',
                                   `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Name',
                                   `sku_id` bigint NOT NULL DEFAULT 0 COMMENT 'Product ID',
                                   `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Product Code',
                                   `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Specification Name',
                                   `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Product Image',
                                   `price` bigint NOT NULL DEFAULT 0 COMMENT 'Unit Price of the Product (in cents)',
                                   `quantity` int NULL DEFAULT NULL COMMENT 'Product Quantity',
                                   `total_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Total Price of the Product (in cents)',
                                   `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion Flag (1: Deleted; 0: Normal)',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                   `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `index_order_id`(`order_id` ASC) USING BTREE COMMENT 'Index on Order ID'
) ENGINE = InnoDB AUTO_INCREMENT = 551 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Order Product Information Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `order_id` bigint NOT NULL COMMENT 'Order ID',
                                  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Operator [User; System; Back-end Administrator]',
                                  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Operation Detail',
                                  `order_status` int NULL DEFAULT NULL COMMENT 'Order Status at the Time of Operation',
                                  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Remark',
                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion [0->Normal; 1->Deleted]',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Order Operation History Record' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `order_id` bigint NOT NULL COMMENT 'Order ID',
                                  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Payment Serial Number',
                                  `pay_amount` bigint NOT NULL DEFAULT 0 COMMENT 'Total Amount Payable (in cents)',
                                  `pay_time` datetime NULL DEFAULT NULL COMMENT 'Payment Time',
                                  `pay_type` tinyint NULL DEFAULT NULL COMMENT 'Payment Method [1->Alipay; 2->WeChat; 3->UnionPay; 4->Cash on Delivery;]',
                                  `pay_status` tinyint NULL DEFAULT NULL COMMENT 'Payment Status',
                                  `confirm_time` datetime NULL DEFAULT NULL COMMENT 'Confirmation Time',
                                  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Callback Content',
                                  `callback_time` datetime NULL DEFAULT NULL COMMENT 'Callback Time',
                                  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'Transaction Content',
                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion [0->Normal; 1->Deleted]',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Payment Information Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `flash_order_overtime` int NULL DEFAULT NULL COMMENT 'Time to Close Flash Orders (in minutes)',
                                      `normal_order_overtime` int NULL DEFAULT NULL COMMENT 'Normal Order Timeout (in minutes)',
                                      `confirm_overtime` int NULL DEFAULT NULL COMMENT 'Automatic Confirmation of Receipt Time After Delivery (in days)',
                                      `finish_overtime` int NULL DEFAULT NULL COMMENT 'Automatic Completion of Transaction Time, No Return (in days)',
                                      `comment_overtime` int NULL DEFAULT NULL COMMENT 'Automatic Evaluation Time After Order Completion (in days)',
                                      `member_level` tinyint NULL DEFAULT NULL COMMENT 'Member Level [0-Unlimited Member Level, Universal; Other-Corresponding Other Member Level]',
                                      `deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Logical Deletion [0->Normal; 1->Deleted]',
                                      `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                      `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Order Configuration Information Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
                             `branch_id` bigint NOT NULL COMMENT 'Branch Transaction ID',
                             `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Global Transaction ID',
                             `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Undo Log Context, such as Serialization',
                             `rollback_info` longblob NOT NULL COMMENT 'Rollback Information',
                             `log_status` int NOT NULL COMMENT '0: Normal Status, 1: Defense Status',
                             `log_created` datetime(6) NOT NULL COMMENT 'Create Datetime',
                             `log_modified` datetime(6) NOT NULL COMMENT 'Modify Datetime',
                             UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT Transaction Mode Undo Table' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
