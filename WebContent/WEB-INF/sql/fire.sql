-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.1.62-community - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 fire.article_tab 结构
DROP TABLE IF EXISTS `article_tab`;
CREATE TABLE IF NOT EXISTS `article_tab` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `author` varchar(50) DEFAULT NULL,
  `module_id` int(11) NOT NULL COMMENT '所属模块',
  `is_top` int(11) NOT NULL DEFAULT '0' COMMENT '1置顶，0不置顶',
  `is_publish` int(11) NOT NULL DEFAULT '0' COMMENT '1发布，0不发布',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `hit_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '点击次数',
  `origin` varchar(100) NOT NULL COMMENT '来源',
  `brief` varchar(200) DEFAULT NULL COMMENT '简介',
  `thumbnail` varchar(200) DEFAULT NULL COMMENT '缩略图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- 数据导出被取消选择。


-- 导出  表 fire.media_tab 结构
DROP TABLE IF EXISTS `media_tab`;
CREATE TABLE IF NOT EXISTS `media_tab` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `brief` varchar(1000) DEFAULT NULL COMMENT '简介',
  `url` varchar(200) NOT NULL,
  `create_date` datetime NOT NULL,
  `author` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='音乐视频表';

-- 数据导出被取消选择。


-- 导出  表 fire.message_tab 结构
DROP TABLE IF EXISTS `message_tab`;
CREATE TABLE IF NOT EXISTS `message_tab` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `author` varchar(50) DEFAULT NULL COMMENT '留言者',
  `message` varchar(1000) NOT NULL COMMENT '留言',
  `response` varchar(1000) DEFAULT NULL COMMENT '回复消息',
  `responser` varchar(50) DEFAULT NULL COMMENT '回复者',
  `create_date` datetime NOT NULL COMMENT '留言时间',
  `update_date` datetime DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='留言';

-- 数据导出被取消选择。


-- 导出  表 fire.module_tab 结构
DROP TABLE IF EXISTS `module_tab`;
CREATE TABLE IF NOT EXISTS `module_tab` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` int(10) DEFAULT NULL COMMENT '上级模块id',
  `level` int(5) NOT NULL COMMENT '层级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块分组';

-- 数据导出被取消选择。


-- 导出  表 fire.user_tab 结构
DROP TABLE IF EXISTS `user_tab`;
CREATE TABLE IF NOT EXISTS `user_tab` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `passowrd` varchar(50) NOT NULL,
  `email` varchar(200) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1普通用户，2管理员',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
