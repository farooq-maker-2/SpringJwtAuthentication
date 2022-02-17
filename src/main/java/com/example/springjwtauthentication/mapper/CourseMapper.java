package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;
import java.util.Optional;

public class CourseMapper {

    public static CourseModel toModel(Optional<Course> course) {

        if (course.isPresent()) {
            return CourseModel.builder()
                    .id(course.get().getId())
                    .courseName(course.get().getCourseName())
                    .description(course.get().getDescription())
                    .level(course.get().getLevel())
                    .build();
        }
        return null;
    }

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
