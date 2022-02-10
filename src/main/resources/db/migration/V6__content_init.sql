CREATE TABLE `contents` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `content_type` varchar(255) DEFAULT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `filename` varchar(255) DEFAULT NULL,
                            `course_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK26ra050idh8wetyhulbictirv` (`course_id`),
                            CONSTRAINT `FK26ra050idh8wetyhulbictirv` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;