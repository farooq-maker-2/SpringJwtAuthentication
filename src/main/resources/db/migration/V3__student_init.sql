CREATE TABLE `students` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `email` varchar(255) NOT NULL,
                            `first_name` varchar(255) NOT NULL,
                            `last_name` varchar(255) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            `role` varchar(255) NOT NULL,
                            `status` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;