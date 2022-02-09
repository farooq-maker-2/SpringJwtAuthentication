
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