package com.example.springjwtauthentication.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {


    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    User findUserById(Long userId);

    Page<User> findAllByRole(String teacher, PageRequest of);

    Page<User> findAByRoleContaining(String teacher, Pageable of);
}
