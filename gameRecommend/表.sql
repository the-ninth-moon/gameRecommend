DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT ' ' COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT ' ' COMMENT '邮箱',
 `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT ' ' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '123456' COMMENT '密码',
  `role` int(5) NULL DEFAULT 2 COMMENT '角色',
`want` BOOLEAN DEFAULT FALSE COMMENT '申请';
   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `userName` varchar(255) NOT NULL DEFAULT ('默认用户名') COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
);

DROP TABLE IF EXISTS `t_game`;
CREATE TABLE `t_game`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '游戏id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '游戏名字',
  `typeId` bigint(20) NULL DEFAULT NULL COMMENT '游戏分类id',
  `developer`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '游戏开发商',
  `sell_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发售时间',
  `collect_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收录时间',
  `platform`  varchar(255)  NULL DEFAULT 'PC' COMMENT '游戏平台',
  `official_web`  varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '官方网站',
  `score` FLOAT NOT NULL DEFAULT 0.0 COMMENT '游戏评分',
   `description` varchar(255) NULL DEFAULT NULL COMMENT '描述',
  `game_content` longtext NOT NULL COMMENT '介绍内容',
  `config_require` longtext NOT NULL COMMENT '配置信息',
  `first_picture` varchar(255) NULL DEFAULT NULL COMMENT '首图地址',
  `published` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否公开',
  `share_statement` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否已发布',
   `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
   `comment_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '评论次数',
  `recommend` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以回复',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `views` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE
);

DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `game_id` bigint(20) NOT NULL COMMENT '相关游戏id',
  `userId` bigint(20) NULL COMMENT '作者id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '首图地址',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '博客内容',
  `original` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '原创',
  `published` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否公开',
  `processed` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否审核',
  `share_statement` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已发布',
  `isDelete` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  `comment_count` bigint(20) NOT NULL DEFAULT 0 COMMENT '评论次数',
  `recommend` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以回复',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `views` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  FOREIGN KEY (`game_id`) REFERENCES `t_game`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE
);


DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Blue' COMMENT '颜色',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
);

DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
);

DROP TABLE IF EXISTS `t_game_tags`;
CREATE TABLE `t_game_tags` (
  `gamesId` bigint(20) NOT NULL COMMENT '游戏id',
  `tagsId` bigint(20) NOT NULL COMMENT '标签id',
  INDEX `tagsId`(`tagsId`) USING BTREE,
  INDEX `gamesId`(`gamesId`) USING BTREE,
  FOREIGN KEY (`gamesId`) REFERENCES `t_game`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`tagsId`) REFERENCES `t_tag`(`id`) 
);


DROP TABLE IF EXISTS `t_comment_game`;
CREATE TABLE `t_comment_game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'game评论id',
  `gameId` bigint(20) NULL DEFAULT NULL COMMENT '游戏id',
  `userId` bigint(20) NOT NULL COMMENT '评论用户id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `parent_comment_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `gameId`(`gameId`) USING BTREE,
  INDEX `parent_comment_id`(`parent_comment_id`) USING BTREE,
  FOREIGN KEY (`gameId`) REFERENCES `t_game`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE
);


DROP TABLE IF EXISTS `t_comment_blog`;
CREATE TABLE `t_comment_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'blog评论id',
  `blogId` bigint(20) NULL DEFAULT NULL COMMENT '博客id',
  `userId` bigint(20) NOT NULL COMMENT '评论用户id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `parent_comment_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `blogId`(`blogId`) USING BTREE,
  INDEX `parent_comment_id`(`parent_comment_id`) USING BTREE,
  FOREIGN KEY (`blogId`) REFERENCES `t_blog`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE
);

-- 删除并重新创建 t_favor_blog 表
DROP TABLE IF EXISTS `t_favor_blog`;
CREATE TABLE `t_favor_blog` (
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `blogId` bigint(20) NOT NULL COMMENT '博客id',
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`blogId`) REFERENCES `t_blog`(`id`) ON DELETE CASCADE
);

-- 删除并重新创建 t_favor_game 表
DROP TABLE IF EXISTS `t_favor_game`;
CREATE TABLE `t_favor_game` (
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `gameId` bigint(20) NOT NULL COMMENT '游戏id',
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`gameId`) REFERENCES `t_game`(`id`) ON DELETE CASCADE
);

-- 删除并重新创建 t_like_blog 表
DROP TABLE IF EXISTS `t_like_blog`;
CREATE TABLE `t_like_blog` (
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `blogId` bigint(20) NOT NULL COMMENT '博客id',
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`blogId`) REFERENCES `t_blog`(`id`) ON DELETE CASCADE
);

-- 删除并重新创建 t_like_game 表
DROP TABLE IF EXISTS `t_like_game`;
CREATE TABLE `t_like_game` (
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `gameId` bigint(20) NOT NULL COMMENT '游戏id',
  FOREIGN KEY (`userId`) REFERENCES `t_user`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`gameId`) REFERENCES `t_game`(`id`) ON DELETE CASCADE
);
