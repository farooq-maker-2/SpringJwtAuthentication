package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.StudentRepository;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Operation(summary = "this api is to list all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @GetMapping("/students")
    public HttpResponse<Page<UserView>> listAllStudents(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page, @RequestParam int pageSize, Authentication authentication) {

        HttpResponse<Page<UserView>> response = new HttpResponse<>();
        Page<Student> students = studentRepository.findAll(PageRequest.of(page.orElse(0), pageSize));
        List<UserView> userViews = new ArrayList<>();
        students.stream().forEach(student -> {
            userViews.add(UserView.toUserView(student));
        });
        response.setData(new PageImpl<>(userViews));
        response.setSuccess(true);
        return response;
    }

    @Operation(summary = "this api is to enroll student to a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#studentId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"student\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public HttpResponse<String> enrollStudentForCourse(@RequestHeader("AUTHORIZATION") String header,
                                                       @PathVariable("studentId") Long studentId,
                                                       @PathVariable("courseId") Long courseId,
                                                       Authentication authentication) {

        HttpResponse<String> response = new HttpResponse<>();
        Student student = studentRepository.findStudentById(studentId);
        response.setMessage(studentService.enrollStudentForCourse(student, courseId));
        response.setSuccess(true);
        return response;
    }


    @Operation(summary = "this api is to opt out student from a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#studentId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"student\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @PutMapping("/students/{studentId}/courses/{courseId}")
    public HttpResponse<String> optOutStudentFormCourse(@RequestHeader("AUTHORIZATION") String header,
                                                        @PathVariable("studentId") Long studentId,
                                                        @PathVariable("courseId") Long courseId,
                                                        Authentication authentication) {

        HttpResponse<String> response = new HttpResponse<>();
        Student student = studentRepository.findStudentById(studentId);
        response.setMessage(studentService.optoutStudentFromCourse(student, courseId));
        response.setSuccess(true);
        return response;
    }

    @Operation(summary = "this api is to get all courses of student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#studentId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"student\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @GetMapping("/students/{studentId}/courses")
    public HttpResponse<Set<CourseModel>> getCoursesOfStudent(@RequestHeader("AUTHORIZATION") String header,
                                                              @PathVariable("studentId") Long studentId,
                                                              @RequestParam Optional<Integer> page,
                                                              Authentication authentication) {

        HttpResponse<Set<CourseModel>> response = new HttpResponse<>();
        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        Set<Course> courses = new HashSet<>();
        Student student = studentRepository.findStudentById(studentId);

        if (student != null) {
            courses = student.getCourses();
            Set<CourseModel> courseModels = courses.stream().map(course -> CourseMapper.toModel(course)).collect(Collectors.toSet());
            if (courseModels == null || courseModels.size() <= fromIndex) {
                response.setData(Collections.emptySet());
            }
            List<CourseModel> coursesList = new ArrayList<>(courseModels);
            // toIndex exclusive
            response.setData(new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size()))));
            response.setSuccess(true);
        }
        return response;
    }
}
