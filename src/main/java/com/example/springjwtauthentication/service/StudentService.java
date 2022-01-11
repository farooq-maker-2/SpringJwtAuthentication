package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.*;
import com.example.springjwtauthentication.model.StudentModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.EnrollmentRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
public class StudentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Student saveStudent(StudentModel student) {
        return studentRepository.save(this.toEntity(student));
    }

//    public Student addRoleToStudent(String email, Long roleId) {
//
//        Student student = studentRepository.findStudentByEmail(email);
//        Role role = roleRepository.findRoleById(roleId);
//        student.getRoles().add(role);
//        studentRepository.save(student);
//        return student;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Student student = studentRepository.findStudentByEmail(email);
//        if (student == null) {
//            log.error("student not found in database");
//            throw new UsernameNotFoundException("Invalid Username or Password");
//        } else {
//            log.info("user found in the database: {}", email);
//        }
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        student.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        });
//
//        return new org.springframework.security.core.userdetails.User(student.getEmail(), student.getPassword(), authorities);
//    }

    public String enrollStudentForCourse(StudentModel student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {

            Enrollment enrollment = Enrollment
                    .builder()
                    .course(course)
                    .enrollmentDate(new Date())
                    .build();
            enrollmentRepository.save(enrollment);

            course.getEnrollments().add(enrollment);
            course.setAllTimeEnrollments(course.getAllTimeEnrollments() + 1);
            courseRepository.save(course);

            Student student_ =  studentRepository.findStudentById(student.getId());
            student_.getCourses().add(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

    public String optoutStudentFromCourse(StudentModel student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
            Student student_ = studentRepository.findStudentById(student.getId());
            student_.getCourses().remove(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

    public Student findStudentById(Long studentId) {
        return studentRepository.findStudentById(studentId);
    }

    public void deleteEnrollments(Long courseId) {
        studentRepository.findAll().stream().forEach(student -> {

            Set<Course> updatedCourses = new HashSet<>();
            student.getCourses().stream().forEach(c -> {
                if (c.getId() != courseId) {
                    updatedCourses.add(c);
                }
            });
            student.setCourses(updatedCourses);
            studentRepository.save(student);
        });
    }

    public StudentModel findStudentByEmail(String email) {
        return this.toModel(studentRepository.findStudentByEmail(email));
    }

    public boolean optoutAndDeleteStudent(StudentModel student) {
        Student student_ = studentRepository.findStudentById(student.getId());
        student_.setCourses(new HashSet<>());
        studentRepository.delete(student_);
        return true;
    }

    public Page<User> findAll(PageRequest of) {
        return userRepository.findAByRoleContaining("student", of);
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

    public Student toEntity(StudentModel student) {
        return Student.builder()
                .id(student.getId())
                .email(student.getEmail())
                .password(student.getPassword())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .status(student.getStatus())
                .build();

    }
}