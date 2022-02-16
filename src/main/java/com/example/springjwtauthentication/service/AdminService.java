package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Admin;
import com.example.springjwtauthentication.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<Admin> findAdminById(Long adminId) {
        return adminRepository.findById(adminId);
    }
}
