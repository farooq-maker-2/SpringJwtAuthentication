package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/students")
    public Page<User> listAllStudents(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        return studentService.findAll(PageRequest.of(page.orElse(0), 5));
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public Student enrollStudentForCourse(@RequestHeader("AUTHORIZATION") String header,
                                          @PathVariable("studentId") Long studentId,
                                          @PathVariable("courseId") Long courseId) {

        User user = userService.findUserById(studentId);
        Student student = studentService.findStudentByEmail(user.getEmail());
        return studentService.enrollStudentForCourse(student, courseId);
    }

    @PutMapping("/students/{studentId}/courses/{courseId}")
    public Student optoutStudentFormCourse(@RequestHeader("AUTHORIZATION") String header,
                                           @PathVariable("studentId") Long studentId,
                                           @PathVariable("courseId") Long courseId) {

        User user = userService.findUserById(studentId);
        Student student = studentService.findStudentByEmail(user.getEmail());
        return studentService.optoutStudentFromCourse(student.getId(), courseId);
    }
}
