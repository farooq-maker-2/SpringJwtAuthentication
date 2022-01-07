package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public void deleteEnrollments(Long courseId) {

        enrollmentRepository.findAll().stream().forEach(enrollment -> {
            if (enrollment.getCourse().getId() == courseId) {
                enrollmentRepository.delete(enrollment);
            }
        });

    }
}
