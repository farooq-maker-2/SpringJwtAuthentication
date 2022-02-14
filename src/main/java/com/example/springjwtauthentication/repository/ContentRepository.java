package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM contents c WHERE c.course_id = :courseId", nativeQuery = true)
    void deleteContentByCourseId(@Param("courseId") Long courseId);

}
