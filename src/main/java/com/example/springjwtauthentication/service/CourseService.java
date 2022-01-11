package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.*;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.entity.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseContentService contentService;

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(CourseModel course) {
        return courseRepository.save(this.toEntity(course));
    }

    public List<Course> findAllTimeTopTen() {
        return courseRepository.findFirst10ByOrderByAllTimeEnrollmentsDesc();
    }

    public List<Course> findTopTenTrendingCourses() {

        List<Course> allCourses = courseRepository.findAll();
        for (Course course : allCourses) {

            List<Enrollment> enrollments = course.getEnrollments();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            Long count = 0L;
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getEnrollmentDate().after(cal.getTime())) {
                    count++;
                }
            }
            course.setTrendingEnrollments(count);
            courseRepository.save(course);
        }
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
                .allTimeEnrollments(course.getAllTimeEnrollments())
                .trendingEnrollments(course.getTrendingEnrollments())
                /*.students((course.getStudents() != null) ?
                        course.getStudents().stream().map(student -> studentService.toModel(student)).collect(Collectors.toSet()) : new HashSet<>())
                .teacher(teacherService.toModel(course.getTeacher()))
                .courseContents((course.getCourseContents() != null) ?
                        course.getCourseContents().stream().map(content -> contentService.toModel(content)).collect(Collectors.toList()) : new ArrayList<>())*/
                .build();
    }

    public Course toEntity(CourseModel course) {
        return Course.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .level(course.getLevel())
                .allTimeEnrollments(course.getAllTimeEnrollments())
                .trendingEnrollments(course.getTrendingEnrollments())
                .build();
    }
}
