package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
