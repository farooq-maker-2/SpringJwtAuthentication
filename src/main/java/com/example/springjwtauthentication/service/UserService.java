package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.mapper.UserMapper;
import com.example.springjwtauthentication.repository.AdminRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.utils.LoginCreds;
import com.example.springjwtauthentication.view.UserView;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {


    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = adminRepository.findAdminByEmail(email);
        if (user == null) {
            user = teacherRepository.findTeacherByEmail(email);
            if (user == null) {
                user = studentRepository.findStudentByEmail(email);
            }
        }
        if (user == null) {
            log.error("user not found in database");
            throw new UsernameNotFoundException("Invalid Username or Password");
        } else {
            log.info("user found in the database: {}", email);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities);

    }

    public HttpResponse<UserView> loginUser(LoginCreds loginCreds) {

        HttpResponse<UserView> userView = new HttpResponse<>();
        userView.setData(UserView.toUserView(teacherRepository.findTeacherByEmail(loginCreds.getEmail())));
        if (userView.getData() == null) {
            userView.setData(UserView.toUserView(studentRepository.findStudentByEmail(loginCreds.getEmail())));
            if (userView.getData() == null) {
                userView.setData(UserView.toUserView(adminRepository.findAdminByEmail(loginCreds.getEmail())));
            }
        }
        userView.setSuccess(userView.getData() != null);
        return userView;
    }

    public HttpResponse<Boolean> registerUser(User user) {
        HttpResponse<Boolean> response = new HttpResponse<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole().equalsIgnoreCase("student")) {
            studentRepository.save(UserMapper.toStudent(user));
            response.setSuccess(true);
        } else if (user.getRole().equalsIgnoreCase("teacher")) {
            teacherRepository.save(UserMapper.toTeacher(user));
            response.setSuccess(true);
        }
        return response;
    }
}
