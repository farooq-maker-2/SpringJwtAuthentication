package com.example.springjwtauthentication.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public Course findCourseById(Long id);

    public List<Course> findAllCoursesByTeacherId(Long id);

//    public List<Course> findFirst10ByOrderByRatingDesc();

//    List<Course> findFirst10ByOrderByTrendRatingDesc();

    List<Course> findFirst10ByOrderByTrendingEnrollmentsDesc();

    List<Course> findFirst10ByOrderByAllTimeEnrollmentsDesc();
}
