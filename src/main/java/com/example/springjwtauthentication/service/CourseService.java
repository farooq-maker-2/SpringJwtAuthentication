package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Object> findAllTimeTopTen() {
        return courseRepository.findFirst10ByOrderByAllTimeEnrollmentsDesc();
    }

    public List<Object> findTopTenTrendingCourses() {
        return courseRepository.findFirst10ByOrderByTrendingEnrollmentsDesc();
    }

    public CourseModel findCourseById(Long courseId) {
        Course course = courseRepository.findCourseById(courseId);
        return this.toModel(course);
    }

    public CourseModel toModel(Course course) {
        return CourseModel.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }

    public CourseModel toModel(Object course) {
        return CourseModel.builder()
                .id(Long.valueOf(((BigInteger) ((Object[]) course)[0]).intValue()))
                .courseName((((Object[]) course)[1]).toString())
                .description((((Object[]) course)[2]).toString())
                .level((((Object[]) course)[3]).toString())
                .build();
    }

    public Course toEntity(CourseModel course) {
        return Course.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .build();
    }
}
