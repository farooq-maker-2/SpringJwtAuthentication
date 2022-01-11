package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {


    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    User findUserById(Long userId);

    Page<User> findAllByRole(String user, Pageable of);

    Page<User> findAByRoleContaining(String teacher, Pageable of);

    List<User> findTeachersByLastNameAndRole(String name, String teacher);

    List<User> findTeachersByFirstNameAndRole(String name, String teacher);
}
