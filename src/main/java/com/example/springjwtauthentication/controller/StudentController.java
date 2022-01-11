package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.model.StudentModel;
import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/students")
    public Page<StudentModel> listAllStudents(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        Page<User> students = userRepository.findAllByRole("student",PageRequest.of(page.orElse(0), 5));
        List<StudentModel> studentModels = students.stream().map(student -> studentService.toModel(student)).collect(Collectors.toList());
        return new PageImpl<>(studentModels);
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public String enrollStudentForCourse(@RequestHeader("AUTHORIZATION") String header,
                                          @PathVariable("studentId") Long studentId,
                                          @PathVariable("courseId") Long courseId) {

        UserModel user = userService.findUserById(studentId);
        StudentModel student = studentService.findStudentByEmail(user.getEmail());
        return studentService.enrollStudentForCourse(student, courseId);
    }

    @PutMapping("/students/{studentId}/courses/{courseId}")
    public String optOutStudentFormCourse(@RequestHeader("AUTHORIZATION") String header,
                                           @PathVariable("studentId") Long studentId,
                                           @PathVariable("courseId") Long courseId) {

        UserModel user = userService.findUserById(studentId);
        StudentModel student = studentService.findStudentByEmail(user.getEmail());
        return studentService.optoutStudentFromCourse(student, courseId);
    }

    @GetMapping("/students/{studentId}/courses")
    public Set<CourseModel> getCoursesOfStudent(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("studentId") Long studentId,
                                                @RequestParam Optional<Integer> page) {

        UserModel user = userService.findUserById(studentId);
        Student student = studentRepository.findStudentByEmail(user.getEmail());
        Set<Course> courses = student.getCourses();
        Set<CourseModel> courseModels = courses.stream().map(course -> courseService.toModel(course)).collect(Collectors.toSet());
        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        if (courseModels == null || courseModels.size() <= fromIndex) {
            return Collections.emptySet();
        }
        List<CourseModel> coursesList = new ArrayList<>(courseModels);
        // toIndex exclusive
        return new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size())));

    }
}
