INSERT INTO `users`
(`id`,
 `first_name`,
 `last_name`,
 `email`,
 `password`,
 `role`,
 `status`)
VALUES (1, 's1', 's1', 's1@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC', 'student',
        'activated'),
       (2, 's2', 's2', 's2@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC', 'student',
        'activated');

INSERT INTO `users`
(`id`,
 `first_name`,
 `last_name`,
 `email`,
 `password`,
 `role`,
 `status`)
VALUES (3, 'M', 'Farooq', 'farooq.teacher@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'teacher', 'activated');

INSERT INTO `users`
    (`id`, first_name, last_name, email, password, role, status)
VALUES (4, 'Muhammad', 'Farooq', 'farooq.admin@gmail.com',
        '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');

INSERT INTO `courses`
(`id`,
 `course_name`,
 `description`,
 `level`,
 `teacher_id`)
VALUES (1, 'C1', 'C1', 101, 3),
       (2, 'C2', 'C2', 102, 3),
       (3, 'C3', 'C3', 102, 3),
       (4, 'C4', 'C4', 102, 3),
       (5, 'C5', 'C5', 102, 3),
       (6, 'C6', 'C6', 102, 3),
       (7, 'C7', 'C7', 102, 3),
       (8, 'C8', 'C8', 102, 3),
       (9, 'C9', 'C9', 102, 3),
       (10, 'C10', 'C10', 102, 3),
       (11, 'C11', 'C11', 102, 3),
       (12, 'C12', 'C12', 102, 3),
       (13, 'C13', 'C13', 102, 3),
       (14, 'C14', 'C14', 102, 3),
       (15, 'C15', 'C15', 102, 3),
       (16, 'C16', 'C16', 102, 3),
       (17, 'C17', 'C17', 102, 3),
       (18, 'C18', 'C18', 102, 3),
       (19, 'C19', 'C19', 102, 3),
       (20, 'C20', 'C20', 102, 3);


INSERT INTO `user_course`
    (`created_at`, `course_id`, `student_id`)
VALUES (NOW(), 1, 1),
       (NOW(), 1, 2),
       (NOW(), 2, 1),
       (NOW(), 3, 1),
       (NOW(), 3, 2),
       (NOW(), 4, 2),
       (NOW(), 5, 1),
       (NOW(), 5, 2),
       (NOW(), 6, 1),
       (NOW(), 7, 1),
       (NOW(), 7, 2),
       (NOW(), 8, 2),
       (NOW(), 9, 1),
       (NOW(), 9, 2),
       (NOW(), 10, 1),
       (NOW(), 11, 1),
       (NOW(), 11, 2),
       (NOW(), 12, 2),
       (NOW(), 13, 1),
       (NOW(), 13, 2),
       (NOW(), 14, 1),
       (NOW(), 15, 1),
       (NOW(), 15, 2),
       (NOW(), 16, 2),
       (NOW(), 17, 1),
       (NOW(), 17, 2),
       (NOW(), 18, 1),
       (NOW(), 19, 1),
       (NOW(), 19, 2),
       (NOW(), 20, 2);