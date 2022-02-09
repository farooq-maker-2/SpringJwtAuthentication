package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.TeacherModel;

public class TeacherMapper {

    private static TeacherModel toModel(User teacher) {
        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();
    }

    public static TeacherModel toModel(Teacher teacher) {

        return TeacherModel.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();
    }

    public static User toEntity(User teacher) {
        return User.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacher.getPassword())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .status(teacher.getStatus())
                .build();

    }
}
