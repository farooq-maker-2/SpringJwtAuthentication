package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findTeacherById(Long teacherId);

    @Query("SELECT t FROM Teacher t WHERE t.firstName = :firstName OR t.lastName = :lastName")
    List<Teacher> findByFirstNameAndLastName(String firstName, String lastName);


    Teacher findTeacherByEmail(String email);
}
