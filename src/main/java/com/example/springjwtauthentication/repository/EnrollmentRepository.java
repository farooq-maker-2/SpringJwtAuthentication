package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Modifying
    @Transactional
    void deleteEnrollmentByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Transactional
    @Query(value = "DELETE From contents c where c.course_id =?1", nativeQuery = true)
    void deleteCourseContentByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Transactional
    @Query(value = "DELETE From course_student c where c.course_id =?1", nativeQuery = true)
    void deleteCourseStudentByCourseId(@Param("courseId") Long courseId);
}
