package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public static final String AllTimeBest = "SELECT Distinct course_id, course_name, description, level,\n" +
            "count(course_id) AS enrollments\n" +
            "FROM jwt_spring_application_docker.courses c\n" +
            "JOIN jwt_spring_application_docker.enrollments e \n" +
            "ON c.id = e.course_id \n" +
            "GROUP BY 1 Limit 10;";


    public static final String TopTrending = "SELECT course_id, course_name, description, level,\n" +
            "count(course_id) AS enrollments\n" +
            "FROM jwt_spring_application_docker.courses c\n" +
            "JOIN jwt_spring_application_docker.enrollments e \n" +
            "ON c.id = e.course_id \n" +
            "WHERE e.enrollment_date > CURDATE()-7\n" +
            "GROUP BY 1 Limit 10;";

    public Course findCourseById(Long id);

    public List<Course> findAllCoursesByTeacherId(Long id);

    @Query(value = TopTrending, nativeQuery = true)
    List<Object> findFirst10ByOrderByTrendingEnrollmentsDesc();

    @Query(value = AllTimeBest, nativeQuery = true)
    List<Object> findFirst10ByOrderByAllTimeEnrollmentsDesc();
}
