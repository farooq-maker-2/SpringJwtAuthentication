package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByEmail(String email);
}
