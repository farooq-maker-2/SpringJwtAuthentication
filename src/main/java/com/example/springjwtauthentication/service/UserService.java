package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.UserRepository;
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
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            log.error("Teacher not found in database");
            throw new UsernameNotFoundException("Invalid Username or Password");
        } else {
            log.info("user found in the database: {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(/*user.getEmail()*/user.getId().toString(), user.getPassword(), authorities);

    }

//    public User findUserByEmailAndPassword(String email, String password) throws UsernameNotFoundException {
//        return userRepository.findUserByEmailAndPassword(email, password);
//    }

    public User findUserById(Long userId) throws UsernameNotFoundException {
        return userRepository.findUserById(userId);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User findUserByEmail(String email) {

        return userRepository.findUserByEmail(email);
    }
}
