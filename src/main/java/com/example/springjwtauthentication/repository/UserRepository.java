package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameOrLastName(String firstName, String lastName);

    User findUserByEmail(String email);
}
