package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseById(Long id);


    @Query(value = "SELECT c.* " +
            "FROM courses c JOIN enrollments e " +
            "ON c.id = e.course_id WHERE e.enrollment_date > CURDATE()-7 " +
            "GROUP BY course_id ORDER BY count(course_id) DESC LIMIT 10;", nativeQuery = true)
    List<Course> findFirst10ByOrderByTrendingEnrollmentsDesc();


    @Query(value = "SELECT c.* " +
            "FROM courses c JOIN enrollments e ON c.id = e.course_id " +
            "GROUP BY course_id ORDER BY count(course_id) DESC LIMIT 10;", nativeQuery = true)
    List<Course> findFirst10ByOrderByAllTimeEnrollmentsDesc();


    @Transactional
    @Modifying
    @Query("DELETE FROM Course c WHERE c.id = :courseId")
    void deleteCourseById(@Param("courseId") Long courseId);

}
