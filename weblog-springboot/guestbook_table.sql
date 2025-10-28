-- 留言板表
CREATE TABLE `t_guestbook` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `content` varchar(500) NOT NULL COMMENT '留言内容',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父留言ID（用于回复）',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否管理员留言',
  `is_audited` tinyint(1) DEFAULT '1' COMMENT '是否已审核',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_is_audited` (`is_audited`),
  KEY `idx_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言板表';

-- 插入一些示例数据
INSERT INTO `t_guestbook` (`nickname`, `email`, `content`, `parent_id`, `like_count`, `is_admin`, `is_audited`, `create_time`, `update_time`, `is_deleted`) VALUES
('小明', 'xiaoming@example.com', '这个博客真的很不错！内容很丰富，设计也很美观。期待更多精彩的文章！', NULL, 5, 0, 1, '2024-01-15 10:30:00', '2024-01-15 10:30:00', 0),
('博主', 'admin@example.com', '谢谢小明的支持！我会继续努力创作更多优质内容的。', NULL, 3, 1, 1, '2024-01-15 11:00:00', '2024-01-15 11:00:00', 0),
('技术爱好者', 'tech@example.com', '最近在学习Vue3，看到这个项目很有启发。请问博主有没有相关的教程推荐？', NULL, 2, 0, 1, '2024-01-14 15:20:00', '2024-01-14 15:20:00', 0),
('设计师', 'designer@example.com', 'UI设计很棒！配色和布局都很舒服，能分享一下设计心得吗？', NULL, 4, 0, 1, '2024-01-14 09:15:00', '2024-01-14 09:15:00', 0),
('前端新手', 'newbie@example.com', '刚接触前端开发，这个项目对我帮助很大！', NULL, 1, 0, 1, '2024-01-13 14:30:00', '2024-01-13 14:30:00', 0);
