-- 创建单词基本信息表
CREATE TABLE `word` (
                        `word_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '单词ID',
                        `spell` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单词拼写',
                        `language` ENUM('en', 'fr', 'ko', 'ja') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'en' COMMENT '单词所属语言',
                        PRIMARY KEY (`word_id`) COMMENT '主键',
                        UNIQUE INDEX `spell` (`spell` ASC) USING BTREE COMMENT '单词拼写的唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='单词基本信息表';

-- 创建单词含义表
CREATE TABLE `meaning` (
                           `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                           `meaning_id` INT UNSIGNED NOT NULL COMMENT '含义ID',
                           `meaning_type` ENUM('noun', 'verb', 'adjective', 'adverb', 'preposition', 'conjunction', 'interjection') NOT NULL COMMENT '词性',
                           `meaning_definition` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '含义定义',
                           PRIMARY KEY (`word_id`, `meaning_id`) COMMENT '复合主键',
                           FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT -- 修正：注释放在外键定义之后
) ENGINE=InnoDB COMMENT='单词含义表';


-- 创建单词短语表
CREATE TABLE `phrase` (
                          `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                          `phrase_id` INT UNSIGNED NOT NULL COMMENT '短语ID',
                          `phrase_text` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短语文本',
                          `phrase_definition` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短语定义',
                          PRIMARY KEY (`word_id`, `phrase_id`) COMMENT '复合主键',
                          FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='单词短语表';

-- 创建单词例句表
CREATE TABLE `examplesentence` (
                                   `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                                   `sentence_id` INT UNSIGNED NOT NULL COMMENT '例句ID',
                                   `sentence_text` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '例句文本',
                                   `sentence_definition` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '例句定义',
                                   PRIMARY KEY (`word_id`, `sentence_id`) COMMENT '复合主键',
                                   FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='单词例句表';

-- 创建单词发音表
CREATE TABLE `pronunciation` (
                                 `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                                 `pronunciation_type` ENUM('US', 'UK') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发音类型',
                                 `transcription` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '音标转录',
                                 PRIMARY KEY (`word_id`, `pronunciation_type`) COMMENT '复合主键',
                                 FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='单词发音表';

-- 创建单词时态变形表
CREATE TABLE `inflection` (
                              `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                              `inflection_type` ENUM('present', 'past', 'past_participle', 'third_person_singular') NOT NULL COMMENT '时态类型',
                              `inflection_text` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '时态变形',
                              PRIMARY KEY (`word_id`, `inflection_type`) COMMENT '复合主键',
                              FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='单词时态变形表';

-- 创建单词阶段要求表
CREATE TABLE `stage` (
                         `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                         `stage_text` ENUM('HSK', 'CET4', 'CET6', 'TOEFL', 'IELTS') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试等级',
                         PRIMARY KEY (`word_id`, `stage_text`) COMMENT '复合主键',
                         FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='单词阶段要求表';


CREATE TABLE `user_word_annotation` (
                                        `annotation_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '注释ID',
                                        `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                                        `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                                        `last_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
                                        `annotation` JSON NOT NULL COMMENT '用户注释，JSON格式',
                                        PRIMARY KEY (`annotation_id`) COMMENT '主键',
                                        UNIQUE INDEX `user_word_unique` (`user_id`, `word_id`) USING BTREE COMMENT '用户单词唯一索引',
                                        FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT ,
                                        FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='用户单词注释表';

CREATE TABLE `friendship` (
                              `friendship_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '好友关系ID',
                              `user_id_1` BIGINT(20) NOT NULL COMMENT '用户1ID',
                              `user_id_2` BIGINT(20) NOT NULL COMMENT '用户2ID',
                              `remark_user_1` VARCHAR(255) DEFAULT '' COMMENT '用户1对用户2的备注',
                              `remark_user_2` VARCHAR(255) DEFAULT '' COMMENT '用户2对用户1的备注',
                              `add_time` DATETIME NOT NULL COMMENT '添加时间',
                              PRIMARY KEY (`friendship_id`),
                              UNIQUE INDEX `unique_friendship` (`user_id_1`, `user_id_2`) USING BTREE,
                              FOREIGN KEY (`user_id_1`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                              FOREIGN KEY (`user_id_2`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='用户好友关系表';

CREATE TABLE `points_log` (
                              `log_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '积分变动ID',
                              `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                              `action_time` DATETIME NOT NULL COMMENT '操作时间',
                              `action_type` SMALLINT NOT NULL COMMENT '操作类型（参考数据字典）',
                              `points_change` INT NOT NULL COMMENT '积分变化量',
                              PRIMARY KEY (`log_id`),
                              FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='用户积分变动记录表';

CREATE TABLE `user_points` (
                               `point_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '积分记录ID',
                               `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                               `points` INT NOT NULL DEFAULT 0 COMMENT '用户积分',
                               `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '积分记录创建时间',
                               PRIMARY KEY (`point_id`) COMMENT '主键',
                               UNIQUE INDEX `user_id_unique` (`user_id`) USING BTREE COMMENT '用户ID唯一索引',
                               FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='用户积分表';

CREATE TABLE `book` (
                        `book_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '书本ID',
                        `title` VARCHAR(255) NOT NULL COMMENT '书本标题',
                        `description` TEXT COMMENT '书本描述',
                        `is_published` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '书本是否已上架',
                        `owner_id` BIGINT(20) NOT NULL COMMENT '书本拥有者ID',
                        `permission` ENUM('public', 'private', 'paid') NOT NULL DEFAULT 'private' COMMENT '书本权限',
                        PRIMARY KEY (`book_id`),
                        INDEX `idx_owner_id` (`owner_id`) COMMENT '加速按用户ID查询书本',
                        FOREIGN KEY (`owner_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='书本信息表';



CREATE TABLE `book_word` (
                             `book_id` INT UNSIGNED NOT NULL COMMENT '书本ID',
                             `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                             `annotation` JSON NOT NULL COMMENT '用户注释，JSON格式',
                             PRIMARY KEY (`book_id`, `word_id`),
                             FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                             FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='书本单词关联表';

CREATE TABLE `user_book` (
                             `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                             `book_id` INT UNSIGNED NOT NULL COMMENT '书本ID',
                             `collection_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                             `collection_note` VARCHAR(255) DEFAULT '' COMMENT '收藏备注',
                             `is_owned` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否拥有此书',
                             `acquisition_method` ENUM('bought', 'gift', 'other') DEFAULT 'other' COMMENT '获取途径',
                             PRIMARY KEY (`user_id`, `book_id`),
                             FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                             FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=INNODB COMMENT='用户收藏书本表';


CREATE TABLE `book_listing` (
                                `listing_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '上架记录ID',
                                `book_id` INT UNSIGNED NOT NULL COMMENT '书本ID',
                                `tags` JSON NOT NULL COMMENT '书本标签',
                                `price` DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '书本价格',
                                `description` TEXT NOT NULL COMMENT '书本描述简介',
                                PRIMARY KEY (`listing_id`),
                                FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='书本上架信息表';

CREATE TABLE `book_rating` (
                               `rating_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评分ID',
                               `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                               `book_id` INT UNSIGNED NOT NULL COMMENT '书本ID',
                               `score` TINYINT UNSIGNED NOT NULL COMMENT '评分',
                               `review` TEXT COMMENT '评论',
                               PRIMARY KEY (`rating_id`),
                               FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                               FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='书本评分与评论表';


CREATE TABLE `review_history` (
                                  `review_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '背诵记录ID',
                                  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
                                  `word_id` INT UNSIGNED NOT NULL COMMENT '单词ID',
                                  `review_time` DATETIME NOT NULL COMMENT '背诵时间',
                                  `memory_status` ENUM('remembered', 'vague', 'forgotten') NOT NULL COMMENT '记忆状态',
                                  PRIMARY KEY (`review_id`),
                                  FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
                                  FOREIGN KEY (`word_id`) REFERENCES `word` (`word_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB COMMENT='用户背诵单词记录表';


