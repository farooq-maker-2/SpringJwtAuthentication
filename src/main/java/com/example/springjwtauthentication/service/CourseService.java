package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAllTimeTopTen() {
        return courseRepository.findFirst10ByOrderByAllTimeEnrollmentsDesc();
    }

    public List<Course> findTopTenTrendingCourses() {
        return courseRepository.findFirst10ByOrderByTrendingEnrollmentsDesc();
    }

    public CourseModel findCourseById(Long courseId) {
        Course course = courseRepository.findCourseById(courseId);
        return CourseMapper.toModel(course);
    }
}
