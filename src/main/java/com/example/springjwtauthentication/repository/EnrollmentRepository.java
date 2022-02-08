package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    public static final String deleteEnrollment = "DELETE From enrollments e where e.course_id =?1";

    public static final String deleteCourseStudent = "DELETE From course_student c where c.course_id =?1";

    @Modifying
    @Transactional
    @Query(value = deleteEnrollment, nativeQuery = true)
    void deleteEnrollmentByCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Transactional
    @Query(value = deleteCourseStudent, nativeQuery = true)
    void deleteCourseStudentByCourseId(@Param("courseId") Long courseId);
}
