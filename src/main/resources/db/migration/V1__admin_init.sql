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

INSERT INTO admins (first_name, last_name, email, password, role, status)
VALUES ('Muhammad', 'Farooq', 'farooq.admin@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');
