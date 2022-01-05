package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.course.Course;
import com.example.springjwtauthentication.course.CourseRepository;
import com.example.springjwtauthentication.enrollment.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
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

    public Course findCourseById(Long courseId) {
        return courseRepository.findCourseById(courseId);
    }

}
