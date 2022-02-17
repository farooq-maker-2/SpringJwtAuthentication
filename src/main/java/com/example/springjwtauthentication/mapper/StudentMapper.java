package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.StudentModel;

public class StudentMapper {

    public static StudentModel toModel(Student student) {

        return StudentModel.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }

    public static StudentModel toModel(User student) {

        return StudentModel.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }

    public static User toEntity(StudentModel student) {

        return User.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }
}
