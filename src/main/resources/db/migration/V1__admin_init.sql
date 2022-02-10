CREATE TABLE `admins` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `email` varchar(255) NOT NULL,
                          `first_name` varchar(255) NOT NULL,
                          `last_name` varchar(255) NOT NULL,
                          `password` varchar(255) NOT NULL,
                          `role` varchar(255) NOT NULL,
                          `status` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO admins (first_name, last_name, email, password, role, status)
VALUES ('Muhammad', 'Farooq', 'farooq.admin@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');
