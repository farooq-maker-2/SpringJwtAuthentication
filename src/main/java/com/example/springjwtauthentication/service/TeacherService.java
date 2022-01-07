package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TeacherService/* implements UserDetailsService*/ {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Teacher teacher = teacherRepository.findTeacherByEmail(email);
//        if (teacher == null) {
//            log.error("Teacher not found in database");
//            throw new UsernameNotFoundException("Invalid Username or Password");
//        } else {
//            log.info("user found in the database: {}", email);
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        teacher.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        });
//
//        return new org.springframework.security.core.userdetails.User(teacher.getEmail(), teacher.getPassword(), authorities);
//    }

//    public Teacher findTeacherByEmail(String email) {
//        return teacherRepository.findTeacherByEmail(email);
//    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findTeacherById(teacherId);
    }

    public Page<User> findAll(PageRequest of) {
        return userRepository.findAByRoleContaining("teacher",of);
    }

    public List<Teacher> findTeachersByName(PageRequest pageRequest,String name) {
        List<Teacher> teachersByLastName = teacherRepository.findTeachersByLastName(/*pageRequest,*/ name);
        List<Teacher> teachersByFirstName = teacherRepository.findTeachersByFirstName(/*pageRequest,*/ name);
        return Stream.concat(teachersByLastName.stream(), teachersByFirstName.stream())
                .collect(Collectors.toList());
    }

    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findTeacherByEmail(email);
    }

}
