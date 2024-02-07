-- ----------------------------
-- Table structure for pms_album
-- ----------------------------
DROP TABLE IF EXISTS `pms_album`;
CREATE TABLE `pms_album`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `cover_pic` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `pic_count` int(11) NULL DEFAULT NULL,
                              `sort` int(11) NULL DEFAULT NULL,
                              `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Album table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_album_pic
-- ----------------------------
DROP TABLE IF EXISTS `pms_album_pic`;
CREATE TABLE `pms_album_pic`  (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `album_id` bigint(20) NULL DEFAULT NULL,
                                  `pic` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Album picture table' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                              `first_letter` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'First letter',
                              `sort` int(11) NULL DEFAULT NULL,
                              `factory_status` int(1) NULL DEFAULT NULL COMMENT 'Whether it is a brand manufacturer: 0->No; 1->Yes',
                              `show_status` int(1) NULL DEFAULT NULL,
                              `product_count` int(11) NULL DEFAULT NULL COMMENT 'Product quantity',
                              `product_comment_count` int(11) NULL DEFAULT NULL COMMENT 'Product comment quantity',
                              `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Brand logo',
                              `big_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Special area big picture',
                              `brand_story` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Brand story',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Brand table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_comment
-- ----------------------------
DROP TABLE IF EXISTS `pms_comment`;
CREATE TABLE `pms_comment`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `product_id` bigint(20) NULL DEFAULT NULL,
                                `member_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `star` int(3) NULL DEFAULT NULL COMMENT 'Rating stars: 0->5',
                                `member_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP of the rating',
                                `create_time` datetime NULL DEFAULT NULL,
                                `show_status` int(1) NULL DEFAULT NULL,
                                `product_attribute` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product attributes at the time of purchase',
                                `collect_count` int(11) NULL DEFAULT NULL,
                                `read_count` int(11) NULL DEFAULT NULL,
                                `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                `pics` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Upload picture address, separated by commas',
                                `member_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Comment user avatar',
                                `reply_count` int(11) NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product comment table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `pms_comment_reply`;
CREATE TABLE `pms_comment_reply`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `comment_id` bigint(20) NULL DEFAULT NULL,
                                       `member_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `member_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                       `create_time` datetime NULL DEFAULT NULL,
                                       `type` int(1) NULL DEFAULT NULL COMMENT 'Commentator type; 0->Member; 1->Administrator',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product comment reply table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_freight_template
-- ----------------------------
DROP TABLE IF EXISTS `pms_feight_template`;
CREATE TABLE `pms_freight_template`  (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                        `charge_type` int(1) NULL DEFAULT NULL COMMENT 'Billing type: 0->By weight; 1->By piece',
                                        `first_weight` decimal(10, 2) NULL DEFAULT NULL COMMENT 'First weight in kg',
                                        `first_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT 'First fee (in yuan)',
                                        `continue_weight` decimal(10, 2) NULL DEFAULT NULL,
                                        `continue_fee` decimal(10, 2) NULL DEFAULT NULL,
                                        `dest` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Destination (province, city)',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Freight template' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_member_price
-- ----------------------------
DROP TABLE IF EXISTS `pms_member_price`;
CREATE TABLE `pms_member_price`  (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `product_id` bigint(20) NULL DEFAULT NULL,
                                     `member_level_id` bigint(20) NULL DEFAULT NULL,
                                     `member_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Member price',
                                     `member_level_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 426 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product member price table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product
-- ----------------------------
DROP TABLE IF EXISTS `pms_product`;
CREATE TABLE `pms_product`  (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `brand_id` bigint(20) NULL DEFAULT NULL,
                                `product_category_id` bigint(20) NULL DEFAULT NULL,
                                `freight_template_id` bigint(20) NULL DEFAULT NULL,
                                `product_attribute_category_id` bigint(20) NULL DEFAULT NULL,
                                `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `product_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Product number',
                                `delete_status` int(1) NULL DEFAULT NULL COMMENT 'Delete status: 0->Not deleted; 1->Deleted',
                                `publish_status` int(1) NULL DEFAULT NULL COMMENT 'Publish status: 0->Unpublished; 1->Published',
                                `new_status` int(1) NULL DEFAULT NULL COMMENT 'New product status: 0->Not new; 1->New',
                                `recommend_status` int(1) NULL DEFAULT NULL COMMENT 'Recommend status: 0->Not recommended; 1->Recommended',
                                `verify_status` int(1) NULL DEFAULT NULL COMMENT 'Verify status: 0->Unverified; 1->Verified',
                                `sort` int(11) NULL DEFAULT NULL COMMENT 'Sort',
                                `sale` int(11) NULL DEFAULT NULL COMMENT 'Sales volume',
                                `price` decimal(10, 2) NULL DEFAULT NULL,
                                `promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Promotion price',
                                `gift_growth` int(11) NULL DEFAULT 0 COMMENT 'Gift growth value',
                                `gift_point` int(11) NULL DEFAULT 0 COMMENT 'Gift points',
                                `use_point_limit` int(11) NULL DEFAULT NULL COMMENT 'Limit of points usage',
                                `sub_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Subtitle',
                                `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Product description',
                                `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Original price',
                                `stock` int(11) NULL DEFAULT NULL COMMENT 'Stock',
                                `low_stock` int(11) NULL DEFAULT NULL COMMENT 'Stock alert value',
                                `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Unit',
                                `weight` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Product weight, default in grams',
                                `preview_status` int(1) NULL DEFAULT NULL COMMENT 'Preview product status: 0->Not a preview; 1->Preview',
                                `service_ids` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product services split by comma: 1->No worry return; 2->Fast refund; 3->Free shipping',
                                `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `album_pics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Album pictures, limit to 5 pictures, split by comma',
                                `detail_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                `detail_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
                                `detail_html` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Product detail webpage content',
                                `detail_mobile_html` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Mobile webpage detail',
                                `promotion_start_time` datetime NULL DEFAULT NULL COMMENT 'Promotion start time',
                                `promotion_end_time` datetime NULL DEFAULT NULL COMMENT 'Promotion end time',
                                `promotion_per_limit` int(11) NULL DEFAULT NULL COMMENT 'Promotion limit quantity',
                                `promotion_type` int(1) NULL DEFAULT NULL COMMENT 'Promotion type: 0->No promotion use original price; 1->Use promotion price; 2->Use member price; 3->Use ladder price; 4->Use full reduction price; 5->Flash sale',
                                `brand_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Brand name',
                                `product_category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product category name',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product information' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_attribute`;
CREATE TABLE `pms_product_attribute`  (
                                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                          `product_attribute_category_id` bigint(20) NULL DEFAULT NULL,
                                          `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                          `select_type` int(1) NULL DEFAULT NULL COMMENT 'Attribute selection type: 0->Unique; 1->Single selection; 2->Multiple selection',
                                          `input_type` int(1) NULL DEFAULT NULL COMMENT 'Attribute input method: 0->Manual input; 1->Select from list',
                                          `input_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'List of optional values, separated by commas',
                                          `sort` int(11) NULL DEFAULT NULL COMMENT 'Sort field: the highest can upload images separately',
                                          `filter_type` int(1) NULL DEFAULT NULL COMMENT 'Category filter style: 1->Normal; 1->Color',
                                          `search_type` int(1) NULL DEFAULT NULL COMMENT 'Search type; 0->No need for search; 1->Keyword search; 2->Range search',
                                          `related_status` int(1) NULL DEFAULT NULL COMMENT 'Whether products with the same attribute are related; 0->Not related; 1->Related',
                                          `hand_add_status` int(1) NULL DEFAULT NULL COMMENT 'Whether to support manual addition; 0->Not supported; 1->Supported',
                                          `type` int(1) NULL DEFAULT NULL COMMENT 'Type of the attribute; 0->Specification; 1->Parameter',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product attribute parameter table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_attribute_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_attribute_category`;
CREATE TABLE `pms_product_attribute_category`  (
                                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                   `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                                   `attribute_count` int(11) NULL DEFAULT 0 COMMENT 'Attribute count',
                                                   `param_count` int(11) NULL DEFAULT 0 COMMENT 'Parameter count',
                                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product attribute category table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_attribute_value`;
CREATE TABLE `pms_product_attribute_value`  (
                                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                `product_id` bigint(20) NULL DEFAULT NULL,
                                                `product_attribute_id` bigint(20) NULL DEFAULT NULL,
                                                `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Value of manually added specification or parameter, single value for parameter, when there are multiple specifications, they are separated by commas',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 517 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Table storing product parameter information' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_category`;
CREATE TABLE `pms_product_category`  (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `parent_id` bigint(20) NULL DEFAULT NULL COMMENT 'Number of the parent category: 0 means a first-level category',
                                         `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `level` int(1) NULL DEFAULT NULL COMMENT 'Category level: 0->1st level; 1->2nd level',
                                         `product_count` int(11) NULL DEFAULT NULL,
                                         `product_unit` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `nav_status` int(1) NULL DEFAULT NULL COMMENT 'Whether to display in the navigation bar: 0->Do not display; 1->Display',
                                         `show_status` int(1) NULL DEFAULT NULL COMMENT 'Display status: 0->Do not display; 1->Display',
                                         `sort` int(11) NULL DEFAULT NULL,
                                         `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Icon',
                                         `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                                         `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Description',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product category' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_category_attribute_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_category_attribute_relation`;
CREATE TABLE `pms_product_category_attribute_relation`  (
                                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                            `product_category_id` bigint(20) NULL DEFAULT NULL,
                                                            `product_attribute_id` bigint(20) NULL DEFAULT NULL,
                                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Table of the relationship between product categories and attributes, used to set category filter conditions (only supports first-level categories)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_full_reduction
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_full_reduction`;
CREATE TABLE `pms_product_full_reduction`  (
                                               `id` bigint(11) NOT NULL AUTO_INCREMENT,
                                               `product_id` bigint(20) NULL DEFAULT NULL,
                                               `full_price` decimal(10, 2) NULL DEFAULT NULL,
                                               `reduce_price` decimal(10, 2) NULL DEFAULT NULL,
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product full reduction table (only for the same product)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_ladder
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_ladder`;
CREATE TABLE `pms_product_ladder`  (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `product_id` bigint(20) NULL DEFAULT NULL,
                                       `count` int(11) NULL DEFAULT NULL COMMENT 'Quantity of product to qualify',
                                       `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Discount',
                                       `price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Price after discount',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product ladder pricing table (only for the same product)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_operate_log`;
CREATE TABLE `pms_product_operate_log`  (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `product_id` bigint(20) NULL DEFAULT NULL,
                                            `price_old` decimal(10, 2) NULL DEFAULT NULL,
                                            `price_new` decimal(10, 2) NULL DEFAULT NULL,
                                            `sale_price_old` decimal(10, 2) NULL DEFAULT NULL,
                                            `sale_price_new` decimal(10, 2) NULL DEFAULT NULL,
                                            `gift_point_old` int(11) NULL DEFAULT NULL COMMENT 'Gift points given',
                                            `gift_point_new` int(11) NULL DEFAULT NULL,
                                            `use_point_limit_old` int(11) NULL DEFAULT NULL,
                                            `use_point_limit_new` int(11) NULL DEFAULT NULL,
                                            `operate_man` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Operator',
                                            `create_time` datetime NULL DEFAULT NULL,
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_product_verify_record
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_vertify_record`;
CREATE TABLE `pms_product_verify_record`  (
                                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                               `product_id` bigint(20) NULL DEFAULT NULL,
                                               `create_time` datetime NULL DEFAULT NULL,
                                               `verify_man` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Verifier',
                                               `status` int(1) NULL DEFAULT NULL,
                                               `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Feedback details',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product verification record' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_sku_stock
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_stock`;
CREATE TABLE `pms_sku_stock`  (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `product_id` bigint(20) NULL DEFAULT NULL,
                                  `sku_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'SKU code',
                                  `price` decimal(10, 2) NULL DEFAULT NULL,
                                  `stock` int(11) NULL DEFAULT 0 COMMENT 'Inventory',
                                  `low_stock` int(11) NULL DEFAULT NULL COMMENT 'Inventory warning',
                                  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Display picture',
                                  `sale` int(11) NULL DEFAULT NULL COMMENT 'Sales volume',
                                  `promotion_price` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Promotional price of the single product',
                                  `lock_stock` int(11) NULL DEFAULT 0 COMMENT 'Locked inventory',
                                  `sp_data` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product sales attributes, in JSON format',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 243 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'SKU inventory' ROW_FORMAT = DYNAMIC;
