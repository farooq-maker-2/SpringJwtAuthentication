package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.AdminRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = adminRepository.findAdminByEmail(email);
        if(user == null){
        user = teacherRepository.findTeacherByEmail(email);
            if(user == null){
                user = studentRepository.findStudentByEmail(email);
            }
        }

        if (user == null) {
            log.error("user not found in database");
            throw new UsernameNotFoundException("Invalid Username or Password");
        } else {
            log.info("user found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities);

    }
}
