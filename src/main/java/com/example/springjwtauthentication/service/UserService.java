package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.view.request.LoginCreds;
import com.example.springjwtauthentication.view.response.UserView;
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
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.error("user not found in database");
            throw new UsernameNotFoundException("Invalid Username or Password");
        } else {
            log.info("user found in the database: {}", email);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
//        });

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), authorities);

    }

    public HttpResponse<UserView> loginUser(LoginCreds loginCreds) {

        HttpResponse<UserView> userView = new HttpResponse<>();
        userView.setData(UserView.toUserView(userRepository.findUserByEmail(loginCreds.getEmail())));
        userView.setSuccess(userView.getData() != null);
        return userView;
    }

    public HttpResponse<Boolean> registerUser(User user) {

        HttpResponse<Boolean> response = new HttpResponse<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.getRoles().forEach(role -> {
//            if (role.equalsIgnoreCase("student")
//                    || role.equalsIgnoreCase("teacher")
//                    || role.equalsIgnoreCase("admin")) {
//                userRepository.save(user);
//                response.setSuccess(true);
//            }
//            return;
//        });
        if (user.getRole().equalsIgnoreCase("student")
                || user.getRole().equalsIgnoreCase("teacher")
                || user.getRole().equalsIgnoreCase("admin")) {
            userRepository.save(user);
            response.setSuccess(true);
        }
        return response;
    }

    public HttpResponse<String> deactivateUser(Long userId, String role) {

        HttpResponse<String> response = new HttpResponse<>();
        if (role.equals("teacher")) {
            response = teacherService.deactivateTeacher(userId);
        } else if (role.equals("student")) {
            response = studentService.optOutAndDeleteStudent(userId);
        }
        return response;
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
