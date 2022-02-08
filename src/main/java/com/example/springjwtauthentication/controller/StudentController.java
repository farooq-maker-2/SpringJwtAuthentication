package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.view.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private StudentRepository studentRepository;

    @Operation(summary = "this api is to list all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/students")
    public Page<UserView> listAllStudents(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        Page<Student> students = studentRepository.findAll(PageRequest.of(page.orElse(0), 5));
        List<UserView> userViews = new ArrayList<>();
        students.stream().forEach(student -> {
            userViews.add(UserView.toStudentView(student));
        });

        return new PageImpl<>(userViews);
    }

    @Operation(summary = "this api is to enroll student to a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public String enrollStudentForCourse(@RequestHeader("AUTHORIZATION") String header,
                                          @PathVariable("studentId") Long studentId,
                                          @PathVariable("courseId") Long courseId) {

        User student = studentRepository.findStudentById(studentId);
        return studentService.enrollStudentForCourse(student, courseId);
    }

    @Operation(summary = "this api is to opt out student from a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PutMapping("/students/{studentId}/courses/{courseId}")
    public String optOutStudentFormCourse(@RequestHeader("AUTHORIZATION") String header,
                                           @PathVariable("studentId") Long studentId,
                                           @PathVariable("courseId") Long courseId) {

        User student = studentRepository.findStudentById(studentId);
        return studentService.optoutStudentFromCourse(student, courseId);
    }

    @Operation(summary = "this api is to get all courses of student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/students/{studentId}/courses")
    public Set<CourseModel> getCoursesOfStudent(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("studentId") Long studentId,
                                                @RequestParam Optional<Integer> page) {

        Student student = studentRepository.findStudentById(studentId);
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
