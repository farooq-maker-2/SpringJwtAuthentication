CREATE TABLE `courses` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `course_name` varchar(255) NOT NULL,
                           `description` varchar(255) NOT NULL,
                           `level` varchar(255) NOT NULL,
                           `teacher_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FK468oyt88pgk2a0cxrvxygadqg` (`teacher_id`),
                           CONSTRAINT `FK468oyt88pgk2a0cxrvxygadqg` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
