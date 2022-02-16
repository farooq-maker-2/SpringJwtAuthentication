package com.example.springjwtauthentication.repository;

import java.util.List;
import com.example.springjwtauthentication.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findByFirstNameOrLastName(String firstName, String lastName);

    Teacher findTeacherByEmail(String email);
}
