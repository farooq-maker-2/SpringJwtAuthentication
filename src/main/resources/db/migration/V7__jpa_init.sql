# CREATE TABLE `courses_course_contents` (
#                                            `course_id` bigint NOT NULL,
#                                            `course_contents_id` bigint NOT NULL,
#                                            UNIQUE KEY `UK_7sttjpei6ee0tbp673v2h8cal` (`course_contents_id`),
#                                            KEY `FKkp44hbyl5hbegk7lc2tasrchd` (`course_id`),
#                                            CONSTRAINT `FKd6vl7frjesq3er3qojgov6t9f` FOREIGN KEY (`course_contents_id`) REFERENCES `contents` (`id`),
#                                            CONSTRAINT `FKkp44hbyl5hbegk7lc2tasrchd` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# CREATE TABLE `courses_enrollments` (
#                                        `course_id` bigint NOT NULL,
#                                        `enrollments_id` bigint NOT NULL,
#                                        UNIQUE KEY `UK_qs8jl8bol57olb3porqhoqepm` (`enrollments_id`),
#                                        KEY `FK3lq1si5ty6erdf67t0q3qq6vl` (`course_id`),
#                                        CONSTRAINT `FK3lq1si5ty6erdf67t0q3qq6vl` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
#                                        CONSTRAINT `FKcmxyx1e9bx1prqjfx7je3l7qr` FOREIGN KEY (`enrollments_id`) REFERENCES `enrollments` (`id`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `student_course` (
                                  `students_id` bigint NOT NULL,
                                  `courses_id` bigint NOT NULL,
                                  KEY `FKbf35esdv7aaugq38nacus0bp3` (`courses_id`),
                                  KEY `FK7rp1cplfqvpf2cyad4j6wbcb6` (`students_id`),
                                  Date datetime DEFAULT NOW() NOT NULL,
                                  CONSTRAINT `FK7rp1cplfqvpf2cyad4j6wbcb6` FOREIGN KEY (`students_id`) REFERENCES `students` (`id`),
                                  CONSTRAINT `FKbf35esdv7aaugq38nacus0bp3` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;