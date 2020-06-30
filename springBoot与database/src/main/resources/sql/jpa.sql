DROP TABLE IF EXISTS `t_jpa`;
CREATE TABLE `t_jpa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '',
  `rate` double DEFAULT '0',
  `sale` decimal(16,8) DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_name`(`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_other`;
CREATE TABLE `t_other` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jpa_id` bigint(20) DEFAULT NULL,
  `desc` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
BEGIN;
INSERT INTO `t_other` VALUES (1, 4, 'test');
INSERT INTO `t_other` VALUES (2, 4, 'aaa');
COMMIT;

