CREATE TABLE `enrollments` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `enrollment_date` datetime NOT NULL,
                               `course_id` bigint DEFAULT NULL,
                               `student_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKho8mcicp4196ebpltdn9wl6co` (`course_id`),
                               KEY `FK8kf1u1857xgo56xbfmnif2c51` (`student_id`),
                               CONSTRAINT `FK8kf1u1857xgo56xbfmnif2c51` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
                               CONSTRAINT `FKho8mcicp4196ebpltdn9wl6co` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;