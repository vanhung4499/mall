/*
 * Mall databases
 * MySQL 8.x version
 */

-- ----------------------------
-- System Management Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_system DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- OAuth2 Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS oauth2_server DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Mall Member Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_ums DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Mall Product Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_pms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Mall Order Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_oms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Mall Marketing Database
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_sms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
