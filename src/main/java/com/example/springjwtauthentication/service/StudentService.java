package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.entity.Enrollment;
import com.example.springjwtauthentication.repository.EnrollmentRepository;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

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

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
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

    public Student enrollStudentForCourse(/*Long studentId,*/Student student, Long courseId) {

        //Student student = studentRepository.findStudentById(studentId);
        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
//            Enrollment enrollment = new Enrollment();
//            enrollment.setCourse(course);
//            //enrollment.setStudent(student);
//            enrollment.setEnrollmentDate(new Date());

            Enrollment enrollment = Enrollment
                    .builder()
                    .course(course)
                    .enrollmentDate(new Date())
                    .build();

            enrollmentRepository.save(enrollment);

            course.getEnrollments().add(enrollment);
            course.setAllTimeEnrollments(course.getAllTimeEnrollments() + 1);
            courseRepository.save(course);

            student.getCourses().add(course);
            studentRepository.save(student);
        }
        return student;
    }

    public Student optoutStudentFromCourse(Long studentId, Long courseId) {

        Student student = studentRepository.findStudentById(studentId);
        //Course course = enrollmentRepository.findEnrollmentByStudentIdAndCourseId(studentId, courseId);
        Course course = courseRepository.findCourseById(courseId);
        student.getCourses().remove(course);
        studentRepository.save(student);
        return student;
    }

    public Student findStudentById(Long studentId) {
        return studentRepository.findStudentById(studentId);
    }

    public void deleteEnrollments(Course course) {
        studentRepository.findAll().stream().forEach(student -> {
            student.getCourses().remove(course);
            studentRepository.save(student);
        });
    }

    public Student findStudentByEmail(String email) {
        return studentRepository.findStudentByEmail(email);
    }

    public boolean optoutAndDeleteStudent(Student student) {
        student.setCourses(new HashSet<>());
        studentRepository.delete(student);
        return true;
    }

    public Page<User> findAll(PageRequest of) {
        return userRepository.findAByRoleContaining("student",of);
    }
}