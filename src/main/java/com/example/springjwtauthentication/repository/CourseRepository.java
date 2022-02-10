package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public Course findCourseById(Long id);


    @Query(value = "SELECT c.id, course_name, description, level,teacher_id " +
            "FROM courses c JOIN enrollments e " +
            "ON c.id = e.course_id WHERE e.enrollment_date > CURDATE()-7 " +
            "GROUP BY course_id Order By count(course_id) DESC Limit 10;", nativeQuery = true)
    List</*Object*/Course> findFirst10ByOrderByTrendingEnrollmentsDesc();


    @Query(value = "SELECT c.id, course_name, description, level,teacher_id " +
            "FROM courses c JOIN enrollments e ON c.id = e.course_id " +
            "GROUP BY course_id ORDER BY count(course_id) DESC LIMIT 10;", nativeQuery = true)
    List</*Object*/Course> findFirst10ByOrderByAllTimeEnrollmentsDesc();


    @Transactional
    @Modifying
    @Query("DELETE From Course c where c.id = :courseId")
    void deleteCourseById(@Param("courseId") Long courseId);

}
