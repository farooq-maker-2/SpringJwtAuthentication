package com.example.springjwtauthentication.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentById(Long id);

    Student findStudentByEmail(String email);
}
