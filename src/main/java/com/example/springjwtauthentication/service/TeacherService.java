package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.TeacherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public User saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

}
