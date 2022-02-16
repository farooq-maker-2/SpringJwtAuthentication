package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByEmail(String email);
}
