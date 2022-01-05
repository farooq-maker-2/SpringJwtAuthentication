package com.example.springjwtauthentication.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findTeacherById(Long teacherId);

    List<Teacher> findTeachersByLastName(String name);

    List<Teacher> findTeachersByFirstName(String name);

    Teacher findTeacherByEmail(String email);
}
