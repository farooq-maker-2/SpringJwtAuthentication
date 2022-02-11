package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Enrollment;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.EnrollmentRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Log4j2
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public String enrollStudentForCourse(Student student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {

            AtomicBoolean alreadyEnrolled = new AtomicBoolean(false);
            student.getEnrollments().stream().forEach(enrollment -> {
                if (enrollment.getCourse().getId().equals(courseId)) {
                    alreadyEnrolled.set(true);
                    return;
                }
            });
            if (!alreadyEnrolled.get()) {
                Enrollment enrollment = Enrollment
                        .builder()
                        .course(course).student(student)
                        .enrollmentDate(new Date())
                        .build();
                enrollmentRepository.save(enrollment);
                studentRepository.save(student);
                return "success";
            }
        }
        return "failure";
    }

    public String optoutStudentFromCourse(Student student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
            if (course != null && student != null) {
                List<Enrollment> enrollments = student.getEnrollments();
                for(Enrollment enrollment: enrollments){
                    if(enrollment.getCourse().getId() == courseId && enrollment.getStudent().getId() == student.getId()){
                        enrollmentRepository.deleteById(enrollment.getId());
                    }
                }
            }
            return "success";
        }
        return "failure";
    }

    public boolean optoutAndDeleteStudent(Student student) {
        student.getEnrollments().stream().forEach(enrollment -> {
            enrollmentRepository.deleteById(enrollment.getId());
        });
        studentRepository.delete(student);
        return true;
    }
}