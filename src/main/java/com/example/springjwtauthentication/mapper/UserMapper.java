package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.Teacher;

public class UserMapper {

    public static Student toStudent(User user) {

        Student student = new Student();
        student.setStatus("activated");
        student.setEmail(user.getEmail());
        student.setPassword(user.getPassword());
        student.setFirstName(user.getFirstName());
        student.setLastName(user.getLastName());
        student.setRole("student");
        return student;
    }

    public static Teacher toTeacher(User user) {

        Teacher teacher = new Teacher();
        teacher.setStatus("activated");
        teacher.setEmail(user.getEmail());
        teacher.setPassword(user.getPassword());
        teacher.setFirstName(user.getFirstName());
        teacher.setLastName(user.getLastName());
        teacher.setRole("teacher");
        return teacher;
    }
}
