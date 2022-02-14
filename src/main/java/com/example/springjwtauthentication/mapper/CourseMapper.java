package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class CourseMapper {

    @Autowired
    private static TeacherRepository teacherRepository;

    public static CourseModel toModel(Course course) {

        return CourseModel.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }

    public static Course toEntity(CourseModel course) {
        return Course.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }
}
