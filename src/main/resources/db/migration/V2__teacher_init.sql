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
