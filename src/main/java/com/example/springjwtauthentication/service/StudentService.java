package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Log4j2
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String enrollStudentForCourse(Student student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {

            AtomicBoolean alreadyEnrolled = new AtomicBoolean(false);
            student.getCourses().stream().forEach(c -> {
                if (c.getId().equals(courseId)) {
                    alreadyEnrolled.set(true);
                    return;
                }
            });
            if (!alreadyEnrolled.get()) {
                student.getCourses().add(course);
                studentRepository.save(student);
                return "success";
            }
        }
        return "failure";
    }

    public String optOutStudentFromCourse(Student student, Long courseId) {

        boolean success = false;
        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
            Set<Course> courses = student.getCourses();
            for (Course c : courses) {
                if (c.getId() == courseId) {
                    success = true;
                    break;
                }
            }
            if (success) {
                student.getCourses().remove(course);
                studentRepository.save(student);
                return "success";
            }
        }
        return "failure";
    }

    public boolean optoutAndDeleteStudent(Student student) {
//        student.getCourses().stream().forEach(c -> {
//            enrollmentRepository.deleteById(enrollment.getId());
//        });
        studentRepository.delete(student);
        return true;
    }
}