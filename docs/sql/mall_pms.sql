use mall_pms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Brand Name',
                             `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Logo Image URL',
                             `sort` int NULL DEFAULT NULL COMMENT 'Sorting',
                             `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                             `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Brand Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
                                `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Category Name',
                                `parent_id` bigint NOT NULL COMMENT 'Parent ID',
                                `level` int NULL DEFAULT NULL COMMENT 'Level',
                                `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Icon URL',
                                `sort` int NULL DEFAULT NULL COMMENT 'Sorting',
                                `visible` tinyint(1) NULL DEFAULT 1 COMMENT 'Display Status (0: Hidden, 1: Visible)',
                                `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Category Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_category_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attribute`;
CREATE TABLE `pms_category_attribute` (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
                                          `category_id` bigint NOT NULL COMMENT 'Category ID',
                                          `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Attribute Name',
                                          `type` tinyint NOT NULL COMMENT 'Type (1: Specification; 2: Attribute)',
                                          `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                          `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          INDEX `fk_pms_attr_pms_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 183 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Attribute Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_brand`;
CREATE TABLE `pms_category_brand` (
                                      `category_id` bigint NOT NULL,
                                      `brand_id` bigint NOT NULL,
                                      PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Code',
                           `spu_id` bigint NOT NULL COMMENT 'SPU ID',
                           `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Name',
                           `spec_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Specification Values, comma-separated',
                           `price` bigint NULL DEFAULT NULL COMMENT 'Product Price (in cents)',
                           `stock` int UNSIGNED NULL DEFAULT NULL COMMENT 'Stock Quantity',
                           `locked_stock` int NULL DEFAULT NULL COMMENT 'Locked Stock Quantity',
                           `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Image URL',
                           `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                           `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `fk_pms_sku_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 755 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Stock Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
                           `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Product Name',
                           `category_id` bigint NOT NULL COMMENT 'Product Category ID',
                           `brand_id` bigint NULL DEFAULT NULL COMMENT 'Product Brand ID',
                           `origin_price` bigint NOT NULL COMMENT 'Original Price (Start)',
                           `price` bigint NOT NULL COMMENT 'Current Price (Start)',
                           `sales` int NULL DEFAULT 0 COMMENT 'Sales Volume',
                           `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Main Product Image',
                           `album` json NULL COMMENT 'Product Album',
                           `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Unit',
                           `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Product Brief Introduction',
                           `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'Product Details',
                           `status` tinyint NULL DEFAULT 1 COMMENT 'Product Status (0: Off-shelf, 1: On-shelf)',
                           `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                           `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `fk_pms_spu_pms_brand`(`brand_id` ASC) USING BTREE,
                           INDEX `fk_pms_spu_pms_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
                                     `spu_id` bigint NOT NULL COMMENT 'Product ID',
                                     `attribute_id` bigint NULL DEFAULT NULL COMMENT 'Attribute ID',
                                     `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Attribute Name',
                                     `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Attribute Value',
                                     `type` tinyint NOT NULL COMMENT 'Type (1: Specification; 2: Attribute)',
                                     `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Specification Image',
                                     `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
                                     `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     INDEX `fk_pms_spu_attribute_pms_attr`(`name` ASC) USING BTREE,
                                     INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 847 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Product Attribute/Specification Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
                            `branch_id` bigint NOT NULL COMMENT 'Branch Transaction ID',
                            `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Global Transaction ID',
                            `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Undo Log Context, such as serialization',
                            `rollback_info` longblob NOT NULL COMMENT 'Rollback Info',
                            `log_status` int NOT NULL COMMENT '0: Normal Status, 1: Defense Status',
                            `log_created` datetime(6) NOT NULL COMMENT 'Create Datetime',
                            `log_modified` datetime(6) NOT NULL COMMENT 'Modify Datetime',
                            UNIQUE INDEX `ux_undo_log` (`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT Transaction Mode Undo Table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
