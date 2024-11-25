-- gamerec.t_game definition

CREATE TABLE `t_game` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '游戏id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '游戏名字',
  `typeId` bigint DEFAULT NULL COMMENT '游戏分类id',
  `developer` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '游戏开发商',
  `sell_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发售时间',
  `collect_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收录时间',
  `platform` varchar(255) DEFAULT 'PC' COMMENT '游戏平台',
  `official_web` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '官方网站',
  `score` float NOT NULL DEFAULT '0' COMMENT '游戏评分',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `game_content` longtext NOT NULL COMMENT '介绍内容',
  `config_require` longtext NOT NULL COMMENT '配置信息',
  `first_picture` varchar(255) DEFAULT NULL COMMENT '首图地址',
  `published` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否公开',
  `share_statement` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否已发布',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `comment_count` bigint NOT NULL DEFAULT '0' COMMENT '评论次数',
  `recommend` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以回复',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `views` int DEFAULT '0' COMMENT '浏览量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `typeId` (`typeId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=557 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_game_image definition

CREATE TABLE `t_game_image` (
  `gameId` bigint DEFAULT NULL,
  `imageUrl` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  UNIQUE KEY `t_game_image_unique` (`gameId`,`imageUrl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_tag definition

CREATE TABLE `t_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `color` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL DEFAULT 'Blue' COMMENT '颜色',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_type definition

CREATE TABLE `t_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_user definition

CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT ' ' COMMENT '头像',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT ' ' COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT ' ' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT '123456' COMMENT '密码',
  `role` int DEFAULT '2' COMMENT '角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `userName` varchar(255) NOT NULL DEFAULT (_utf8mb4'默认用户名') COMMENT '用户名',
  `want` tinyint(1) DEFAULT '0' COMMENT '申请',
  `secret` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_user_unique` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_blog definition

CREATE TABLE `t_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `game_id` bigint NOT NULL COMMENT '相关游戏id',
  `userId` bigint DEFAULT NULL COMMENT '作者id',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '描述',
  `first_picture` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '首图地址',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL COMMENT '博客内容',
  `original` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '原创',
  `published` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否公开',
  `processed` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否审核',
  `share_statement` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已发布',
  `isDelete` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `comment_count` bigint NOT NULL DEFAULT '0' COMMENT '评论次数',
  `recommend` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可以回复',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `views` int DEFAULT '0' COMMENT '浏览量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `game_id` (`game_id`),
  CONSTRAINT `t_blog_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `t_game` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_blog_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_comment_blog definition

CREATE TABLE `t_comment_blog` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'blog评论id',
  `blogId` bigint DEFAULT NULL COMMENT '博客id',
  `userId` bigint NOT NULL COMMENT '评论用户id',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  `parent_comment_id` bigint DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `blogId` (`blogId`) USING BTREE,
  KEY `parent_comment_id` (`parent_comment_id`) USING BTREE,
  KEY `userId` (`userId`),
  CONSTRAINT `t_comment_blog_ibfk_1` FOREIGN KEY (`blogId`) REFERENCES `t_blog` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_comment_blog_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_comment_game definition

CREATE TABLE `t_comment_game` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'game评论id',
  `gameId` bigint DEFAULT NULL COMMENT '游戏id',
  `userId` bigint NOT NULL COMMENT '评论用户id',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  `parent_comment_id` bigint DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `gameId` (`gameId`) USING BTREE,
  KEY `parent_comment_id` (`parent_comment_id`) USING BTREE,
  KEY `userId` (`userId`),
  CONSTRAINT `t_comment_game_ibfk_1` FOREIGN KEY (`gameId`) REFERENCES `t_game` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_comment_game_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_favor_blog definition

CREATE TABLE `t_favor_blog` (
  `userId` bigint NOT NULL COMMENT '用户id',
  `blogId` bigint NOT NULL COMMENT '博客id',
  KEY `userId` (`userId`),
  KEY `blogId` (`blogId`),
  CONSTRAINT `t_favor_blog_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_favor_blog_ibfk_2` FOREIGN KEY (`blogId`) REFERENCES `t_blog` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_favor_game definition

CREATE TABLE `t_favor_game` (
  `userId` bigint NOT NULL COMMENT '用户id',
  `gameId` bigint NOT NULL COMMENT '游戏id',
  KEY `userId` (`userId`),
  KEY `gameId` (`gameId`),
  CONSTRAINT `t_favor_game_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_favor_game_ibfk_2` FOREIGN KEY (`gameId`) REFERENCES `t_game` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_game_tags definition

CREATE TABLE `t_game_tags` (
  `gamesId` bigint NOT NULL COMMENT '游戏id',
  `tagsId` bigint NOT NULL COMMENT '标签id',
  KEY `tagsId` (`tagsId`) USING BTREE,
  KEY `gamesId` (`gamesId`) USING BTREE,
  CONSTRAINT `t_game_tags_ibfk_1` FOREIGN KEY (`gamesId`) REFERENCES `t_game` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_game_tags_ibfk_2` FOREIGN KEY (`tagsId`) REFERENCES `t_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_like_blog definition

CREATE TABLE `t_like_blog` (
  `userId` bigint NOT NULL COMMENT '用户id',
  `blogId` bigint NOT NULL COMMENT '博客id',
  KEY `userId` (`userId`),
  KEY `blogId` (`blogId`),
  CONSTRAINT `t_like_blog_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_like_blog_ibfk_2` FOREIGN KEY (`blogId`) REFERENCES `t_blog` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- gamerec.t_like_game definition

CREATE TABLE `t_like_game` (
  `userId` bigint NOT NULL COMMENT '用户id',
  `gameId` bigint NOT NULL COMMENT '游戏id',
  KEY `userId` (`userId`),
  KEY `gameId` (`gameId`),
  CONSTRAINT `t_like_game_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `t_like_game_ibfk_2` FOREIGN KEY (`gameId`) REFERENCES `t_game` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;