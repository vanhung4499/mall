-- ----------------------------
-- Table structure for oms_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_cart_item`;
CREATE TABLE `oms_cart_item`  (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `product_id` bigint(20) NULL DEFAULT NULL,
                                  `product_sku_id` bigint(20) NULL DEFAULT NULL,
                                  `member_id` bigint(20) NULL DEFAULT NULL,
                                  `quantity` int(11) NULL DEFAULT NULL COMMENT 'Purchase quantity',
                                  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Price when added to cart',
                                  `product_pic` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product main image',
                                  `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product name',
                                  `product_sub_title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product subtitle (selling point)',
                                  `product_sku_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product SKU barcode',
                                  `member_nickname` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Member nickname',
                                  `create_date` datetime NULL DEFAULT NULL COMMENT 'Creation time',
                                  `modify_date` datetime NULL DEFAULT NULL COMMENT 'Modification time',
                                  `delete_status` int(1) NULL DEFAULT 0 COMMENT 'Whether deleted',
                                  `product_category_id` bigint(20) NULL DEFAULT NULL COMMENT 'Product category',
                                  `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                  `product_sn` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                  `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product sales attributes: [{"key":"Color","value":"Color"},{"key":"Capacity","value":"4G"}]',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Shopping cart table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_company_address
-- ----------------------------
DROP TABLE IF EXISTS `oms_company_address`;
CREATE TABLE `oms_company_address`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `address_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Address name',
                                        `send_status` int(1) NULL DEFAULT NULL COMMENT 'Default shipping address: 0->No; 1->Yes',
                                        `receive_status` int(1) NULL DEFAULT NULL COMMENT 'Is it the default receiving address: 0->No; 1->Yes',
                                        `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name of the receiver/sender',
                                        `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Receiver\'s phone number',
                                      `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Province / Municipality',
                                      `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'City',
                                      `region` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Region',
                                      `detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Detailed address',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Company shipping and receiving address table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Order id',
                              `member_id` bigint(20) NOT NULL,
                              `coupon_id` bigint(20) NULL DEFAULT NULL,
                              `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Order number',
                              `create_time` datetime NULL DEFAULT NULL COMMENT 'Submission time',
                              `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'User account',
                              `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Total order amount',
                              `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Payable amount (actual payment amount)',
                              `freight_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Freight amount',
                              `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Promotion optimization amount (promotion price, full reduction, ladder price)',
                              `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Points deduction amount',
                              `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Coupon deduction amount',
                              `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Discount amount used by the administrator to adjust the order in the background',
                              `pay_type` int(1) NULL DEFAULT NULL COMMENT 'Payment method: 0->Unpaid; 1->Alipay; 2->WeChat',
                              `source_type` int(1) NULL DEFAULT NULL COMMENT 'Order source: 0->PC order; 1->app order',
                              `status` int(1) NULL DEFAULT NULL COMMENT 'Order status: 0->Pending payment; 1->To be shipped; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order',
                              `order_type` int(1) NULL DEFAULT NULL COMMENT 'Order type: 0->Normal order; 1->Flash sale order',
                              `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Logistics company (delivery method)',
                              `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Logistics order number',
                              `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT 'Automatic confirmation time (days)',
                              `integration` int(11) NULL DEFAULT NULL COMMENT 'Points that can be obtained',
                              `growth` int(11) NULL DEFAULT NULL COMMENT 'Growth value that can be obtained',
                              `promotion_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Promotion information',
                              `bill_type` int(1) NULL DEFAULT NULL COMMENT 'Invoice type: 0->No invoice; 1->Electronic invoice; 2->Paper invoice',
                              `bill_header` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Invoice header',
                              `bill_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Invoice content',
                              `bill_receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Invoice receiver\'s phone',
                              `bill_receiver_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Invoice receiver\'s email',
                              `receiver_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Receiver\'s name',
                              `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Receiver\'s phone',
                              `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Receiver\'s postal code',
                              `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Province/Municipality',
                              `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'City',
                              `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Region',
                              `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Detailed address',
                              `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Order note',
                              `confirm_status` int(1) NULL DEFAULT NULL COMMENT 'Confirmation status: 0->Not confirmed; 1->Confirmed',
                              `delete_status` int(1) NOT NULL DEFAULT 0 COMMENT 'Delete status: 0->Not deleted; 1->Deleted',
                              `use_integration` int(11) NULL DEFAULT NULL COMMENT 'Points used when placing the order',
                              `payment_time` datetime NULL DEFAULT NULL COMMENT 'Payment time',
                              `delivery_time` datetime NULL DEFAULT NULL COMMENT 'Delivery time',
                              `receive_time` datetime NULL DEFAULT NULL COMMENT 'Confirmation of receipt time',
                              `comment_time` datetime NULL DEFAULT NULL COMMENT 'Evaluation time',
                              `modify_time` datetime NULL DEFAULT NULL COMMENT 'Modification time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Order table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'Order id',
                                   `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Order number',
                                   `product_id` bigint(20) NULL DEFAULT NULL,
                                   `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                   `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Selling price',
                                   `product_quantity` int(11) NULL DEFAULT NULL COMMENT 'Purchase quantity',
                                   `product_sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'Product SKU number',
                                   `product_sku_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product SKU barcode',
                                   `product_category_id` bigint(20) NULL DEFAULT NULL COMMENT 'Product category id',
                                   `promotion_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product promotion name',
                                   `promotion_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Product promotion decomposition amount',
                                   `coupon_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Coupon discount decomposition amount',
                                   `integration_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Points discount decomposition amount',
                                   `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'The decomposition amount of this product after discount',
                                   `gift_integration` int(11) NULL DEFAULT 0,
                                   `gift_growth` int(11) NULL DEFAULT 0,
                                   `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product sales attributes: [{\"key\":\"Color\",\"value\":\"Color\"},{\"key\":\"Capacity\",\"value\":\"4G\"}]',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Products included in the order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
                                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                              `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'Order id',
                                              `operate_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operator: User; System; Back-end Administrator',
                                              `create_time` datetime NULL DEFAULT NULL COMMENT 'Operation time',
                                              `order_status` int(1) NULL DEFAULT NULL COMMENT 'Order status: 0->Pending payment; 1->To be shipped; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order',
                                              `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Note',
                                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Order operation history record' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                           `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'Order id',
                                           `company_address_id` bigint(20) NULL DEFAULT NULL COMMENT 'Receiving address table id',
                                           `product_id` bigint(20) NULL DEFAULT NULL COMMENT 'Return product id',
                                           `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Order number',
                                           `create_time` datetime NULL DEFAULT NULL COMMENT 'Application time',
                                           `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Member username',
                                           `return_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Refund amount',
                                           `return_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Returner name',
                                           `return_phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Returner phone',
                                           `status` int(1) NULL DEFAULT NULL COMMENT 'Application status: 0->Pending; 1->Returning; 2->Completed; 3->Rejected',
                                           `handle_time` datetime NULL DEFAULT NULL COMMENT 'Processing time',
                                           `product_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product picture',
                                           `product_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product name',
                                           `product_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product brand',
                                           `product_attr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product sale attributes: Color: Red; Size: XL;',
                                           `product_count` int(11) NULL DEFAULT NULL COMMENT 'Return quantity',
                                           `product_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Product unit price',
                                           `product_real_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Product actual payment unit price',
                                           `reason` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Reason',
                                           `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Description',
                                           `proof_pics` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Proof pictures, separated by commas',
                                           `handle_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Processing note',
                                           `handle_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Processing person',
                                           `receive_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Receiver',
                                           `receive_time` datetime NULL DEFAULT NULL COMMENT 'Receiving time',
                                           `receive_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Receiving note',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Order return application' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Return type',
                                            `sort` int(11) NULL DEFAULT NULL,
                                            `status` int(1) NULL DEFAULT NULL COMMENT 'Status: 0->Not enabled; 1->Enabled',
                                            `create_time` datetime NULL DEFAULT NULL COMMENT 'Addition time',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Return reason table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT 'Flash sale order timeout closure time (minutes)',
                                      `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT 'Normal order timeout time (minutes)',
                                      `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT 'Automatic confirmation of receipt time after delivery (days)',
                                      `finish_overtime` int(11) NULL DEFAULT NULL COMMENT 'Automatic transaction completion time, after-sales cannot be applied (days)',
                                      `comment_overtime` int(11) NULL DEFAULT NULL COMMENT 'Automatic positive review time after order completion (days)',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Order settings table' ROW_FORMAT = DYNAMIC;
