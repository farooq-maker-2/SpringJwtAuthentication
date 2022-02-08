package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.TeacherModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findTeacherById(Long teacherId);

    List<Teacher> findTeachersByLastName(String name);

    List<Teacher> findTeachersByFirstNameAndLastName(PageRequest of, String name);

    Teacher findTeacherByEmail(String email);

    List<User> findTeachersByLastNameAndRole(String name, String teacher);
}
