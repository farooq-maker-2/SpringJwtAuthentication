CREATE TABLE `courses` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `course_name` varchar(255) NOT NULL,
                           `description` varchar(255) NOT NULL,
                           `level` varchar(255) NOT NULL,
                           `teacher_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKt4ba5fab1x56tmt4nsypv5lm5` (`teacher_id`),
                           CONSTRAINT `FKt4ba5fab1x56tmt4nsypv5lm5` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
