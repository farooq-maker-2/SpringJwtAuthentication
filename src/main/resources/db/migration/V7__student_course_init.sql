CREATE TABLE `student_course`
(
    `student_id` bigint                 NOT NULL,
    `course_id`  bigint                 NOT NULL,
    KEY `FKbf35esdv7aaugq38nacus0bp3` (`courses_id`),
    KEY `FK7rp1cplfqvpf2cyad4j6wbcb6` (`students_id`),
    created_at   datetime DEFAULT NOW() NOT NULL,
    CONSTRAINT `FK7rp1cplfqvpf2cyad4j6wbcb6` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
    CONSTRAINT `FKbf35esdv7aaugq38nacus0bp3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;