CREATE TABLE IF NOT EXISTS `course_student` (
                                                `student_id` bigint NOT NULL,
                                                `course_id` bigint NOT NULL,
                                                PRIMARY KEY (`student_id`,`course_id`),
                                                KEY `FKlmsbddqkv96q4nijkrxuof3ug` (`course_id`),
                                                CONSTRAINT `FKacc7gn1l63go6x8dsx0wdnr38` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
                                                CONSTRAINT `FKlmsbddqkv96q4nijkrxuof3ug` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;