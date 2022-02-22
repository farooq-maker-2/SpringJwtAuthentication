CREATE TABLE `user_course` (
                               `student_id` bigint NOT NULL,
                               `course_id` bigint NOT NULL,
                               PRIMARY KEY (`student_id`,`course_id`),
                               KEY `FKf4qni5wnlq0x70fm39w9tv7x4` (`course_id`),
                               created_at   datetime DEFAULT NOW() NOT NULL,
                               CONSTRAINT `FKcq93rmc3om4taei4w2c0bl8xk` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `FKf4qni5wnlq0x70fm39w9tv7x4` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;