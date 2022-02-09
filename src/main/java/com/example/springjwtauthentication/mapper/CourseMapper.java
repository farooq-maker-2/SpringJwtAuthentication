package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;

import java.math.BigInteger;

public class CourseMapper {

    public static CourseModel toModel(Course course) {
        return CourseModel.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }

    public static CourseModel toModel(Object course) {
        return CourseModel.builder()
                .id(Long.valueOf(((BigInteger) ((Object[]) course)[0]).intValue()))
                .courseName((((Object[]) course)[1]).toString())
                .description((((Object[]) course)[2]).toString())
                .level((((Object[]) course)[3]).toString())
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
