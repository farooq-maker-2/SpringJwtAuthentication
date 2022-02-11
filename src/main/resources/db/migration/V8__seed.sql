INSERT INTO admins
(first_name, last_name, email, password, role, status)
VALUES ('Muhammad', 'Farooq', 'farooq.admin@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC',
        'admin', 'activated');

INSERT INTO `students`
(`first_name`,
 `last_name`,
 `email`,
 `password`,
 `role`,
 `status`)
VALUES ('s1', 's1', 's1@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC', 'student', 'activated'),
       ('s2', 's2', 's2@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC', 'student', 'activated');


INSERT INTO `teachers`
(`first_name`,
 `last_name`,
 `email`,
 `password`,
 `role`,
 `status`)
VALUES ('M', 'Farooq', 'farooq.teacher@gmail.com', '$2a$10$Tic/wlaslKB5KlEVnPuKW.3/zXG/EKotV9XGDlMfpjjB/M6.UEEkC', 'teacher', 'activated');


INSERT INTO `courses`
(`course_name`,
 `description`,
 `level`,
 `teacher_id`)
VALUES ('C1', 'C1', 101, 1),
       ('C2', 'C2', 102, 1),
       ('C3', 'C3', 102, 1),
       ('C4', 'C4', 102, 1),
       ('C5', 'C5', 102, 1),
       ('C6', 'C6', 102, 1),
       ('C7', 'C7', 102, 1),
       ('C8', 'C8', 102, 1),
       ('C9', 'C9', 102, 1),
       ('C10', 'C10', 102, 1),
       ('C11', 'C11', 102, 1),
       ('C12', 'C12', 102, 1),
       ('C13', 'C13', 102, 1),
       ('C14', 'C14', 102, 1),
       ('C15', 'C15', 102, 1),
       ('C16', 'C16', 102, 1),
       ('C17', 'C17', 102, 1),
       ('C18', 'C18', 102, 1),
       ('C19', 'C19', 102, 1),
       ('C20', 'C20', 102, 1);


INSERT INTO `jwt_spring_application_docker`.`enrollments`
    (`enrollment_date`, `course_id`, `student_id`)
VALUES ('2022-02-7', '1', 1),
       ('2022-02-7', '1', 2),
       ('2022-02-7', '2', 1),
       ('2022-02-7', '3', 1),
       ('2022-02-7', '3', 2),
       ('2022-02-7', '4', 2),
       ('2022-02-7', '5', 1),
       ('2022-02-7', '5', 2),
       ('2022-02-7', '6', 1),
       ('2022-02-7', '7', 1),
       ('2022-02-7', '7', 2),
       ('2022-02-7', '8', 2),
       ('2022-02-7', '9', 1),
       ('2022-02-7', '9', 2),
       ('2022-02-7', '10', 1),
       ('2022-02-7', '11', 1),
       ('2022-02-7', '11', 2),
       ('2022-02-7', '12', 2),
       ('2022-02-7', '13', 1),
       ('2022-02-7', '13', 2),
       ('2022-02-7', '14', 1),
       ('2022-02-7', '15', 1),
       ('2022-02-7', '15', 2),
       ('2022-02-7', '16', 2),
       ('2022-02-7', '17', 1),
       ('2022-02-7', '17', 2),
       ('2022-02-7', '18', 1),
       ('2022-02-7', '19', 1),
       ('2022-02-7', '19', 2),
       ('2022-02-7', '20', 2);