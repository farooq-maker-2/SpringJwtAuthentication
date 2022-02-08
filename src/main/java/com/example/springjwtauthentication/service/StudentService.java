package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Enrollment;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.StudentModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.EnrollmentRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

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

    public String enrollStudentForCourse(User student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {

            Enrollment enrollment = Enrollment
                    .builder()
                    .course(course)
                    .enrollmentDate(new Date())
                    .build();
            enrollmentRepository.save(enrollment);

            course.getEnrollments().add(enrollment);
            courseRepository.save(course);

            Student student_ =  studentRepository.findStudentById(student.getId());
            student_.getCourses().add(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

    public String optoutStudentFromCourse(User student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
            Student student_ = studentRepository.findStudentById(student.getId());
            student_.getCourses().remove(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

//    public void deleteEnrollments(Long courseId) {
//        studentRepository.findAll().stream().forEach(student -> {
//
//            Set<Course> updatedCourses = new HashSet<>();
//            student.getCourses().stream().forEach(c -> {
//                if (c.getId() != courseId) {
//                    updatedCourses.add(c);
//                }
//            });
//            student.setCourses(updatedCourses);
//            studentRepository.save(student);
//        });
//    }

    public boolean optoutAndDeleteStudent(Student student) {
        Student student_ = studentRepository.findStudentById(student.getId());
        student_.setCourses(new HashSet<>());
        studentRepository.delete(student_);
        return true;
    }

    public StudentModel toModel(Student student) {
        return StudentModel.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }

    public StudentModel toModel(User student) {
        return StudentModel.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }

    public User toEntity(StudentModel student) {
        return User.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }
}