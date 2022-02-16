package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.view.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    @Operation(summary = "this api is to list all courses")
    @GetMapping(path = "/students", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @RolesAllowed({"ADMIN"})
    public HttpResponse<Page<UserView>> listAllStudents(@RequestHeader("AUTHORIZATION") String header,
                                                        @RequestParam Optional<Integer> page,
                                                        @RequestParam Optional<Integer> pageSize) {

        return studentService.listAllStudents(page, pageSize);
    }

    @RolesAllowed({"STUDENT", "ADMIN"})
    @Operation(summary = "this api is to enroll student to a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PreAuthorize("#studentId.toString().equals(authentication.principal)")
    @PostMapping(path = "/students/{studentId}/courses/{courseId}", produces = "application/json")
    public HttpResponse<String> enrollStudentForCourse(@RequestHeader("AUTHORIZATION") String header,
                                                       @PathVariable("studentId") Long studentId,
                                                       @PathVariable("courseId") Long courseId,
                                                       Authentication authentication) {

        return studentService.enrollStudentForCourse(studentId, courseId);
    }


    @PutMapping(path = "/students/{studentId}/courses/{courseId}", produces = "application/json", consumes = "application/json")
    @Operation(summary = "this api is to opt out student from a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PreAuthorize("#studentId.toString().equals(authentication.principal)")
    @RolesAllowed({"STUDENT", "ADMIN"})
    public HttpResponse<String> optOutStudentFormCourse(@RequestHeader("AUTHORIZATION") String header,
                                                        @PathVariable("studentId") Long studentId,
                                                        @PathVariable("courseId") Long courseId,
                                                        Authentication authentication) {

        return studentService.optOutStudentFromCourse(studentId,courseId);
    }

    @GetMapping(path = "/students/{studentId}/courses", produces = "application/json")
    @Operation(summary = "this api is to get all courses of student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PreAuthorize("#studentId.toString().equals(authentication.principal)")
    @RolesAllowed({"STUDENT", "ADMIN"})
    public HttpResponse<Set<CourseModel>> getCoursesOfStudent(@RequestHeader("AUTHORIZATION") String header,
                                                              @PathVariable("studentId") Long studentId,
                                                              @RequestParam Optional<Integer> page,
                                                              Authentication authentication) {

        return studentService.getCoursesOfStudent(studentId,page);
    }
}
