CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name`  varchar(50),
    `email`      varchar(50),
    `password`   varchar(255),
    `role`       varchar(20),
    `status`     varchar(20)

) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;



CREATE TABLE IF NOT EXISTS `admins`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name`  varchar(50),
    `email`      varchar(50),
    `password`   varchar(255),
    `role`       varchar(20),
    `status`     varchar(20)

) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;



CREATE TABLE IF NOT EXISTS `teachers`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name`  varchar(50),
    `email`      varchar(50),
    `password`   varchar(255),
    `role`       varchar(20),
    `status`     varchar(20)

) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;



CREATE TABLE IF NOT EXISTS `students`
(
    `id`         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` varchar(50),
    `last_name`  varchar(50),
    `email`      varchar(50),
    `password`   varchar(255),
    `role`       varchar(20),
    `status`     varchar(20)

) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;



CREATE TABLE IF NOT EXISTS `courses`
(
    `id`                   bigint       NOT NULL AUTO_INCREMENT,
    `all_time_enrollments` bigint DEFAULT NULL,
    `course_name`          varchar(255) NOT NULL,
    `description`          varchar(255) NOT NULL,
    `level`                varchar(255) NOT NULL,
    `trending_enrollments` bigint DEFAULT NULL,
    `teacher_id`           bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK468oyt88pgk2a0cxrvxygadqg` (`teacher_id`),
    CONSTRAINT `FK468oyt88pgk2a0cxrvxygadqg` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `contents`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `content_type`      varchar(255) DEFAULT NULL,
    `description`       varchar(255) DEFAULT NULL,
    `filename`          varchar(255) DEFAULT NULL,
    `course_id`         bigint       DEFAULT NULL,
    `course_content_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK26ra050idh8wetyhulbictirv` (`course_id`),
    KEY `FKqs3182hxdyjgpa6gohmgoxpa5` (`course_content_id`),
    CONSTRAINT `FK26ra050idh8wetyhulbictirv` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
    CONSTRAINT `FKqs3182hxdyjgpa6gohmgoxpa5` FOREIGN KEY (`course_content_id`) REFERENCES `courses` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `course_student` (
                                  `student_id` bigint NOT NULL,
                                  `course_id` bigint NOT NULL,
                                  PRIMARY KEY (`student_id`,`course_id`),
                                  KEY `FKlmsbddqkv96q4nijkrxuof3ug` (`course_id`),
                                  CONSTRAINT `FKacc7gn1l63go6x8dsx0wdnr38` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
                                  CONSTRAINT `FKlmsbddqkv96q4nijkrxuof3ug` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `enrollments`
(
    `id`              bigint   NOT NULL AUTO_INCREMENT,
    `enrollment_date` datetime NOT NULL,
    `course_id`       bigint DEFAULT NULL,
    `enrollment_id`   bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKho8mcicp4196ebpltdn9wl6co` (`course_id`),
    KEY `FKavvewmqty4yf4m3gbi5rrf8ox` (`enrollment_id`),
    CONSTRAINT `FKavvewmqty4yf4m3gbi5rrf8ox` FOREIGN KEY (`enrollment_id`) REFERENCES `courses` (`id`),
    CONSTRAINT `FKho8mcicp4196ebpltdn9wl6co` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;



INSERT INTO admins (first_name, last_name, email, password, role, status)
VALUES ('Muhammad', 'Farooq', 'farooq.admin@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');

INSERT INTO users (first_name, last_name, email, password, role, status)
VALUES ('Muhammad', 'Farooq', 'farooq.admin@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');