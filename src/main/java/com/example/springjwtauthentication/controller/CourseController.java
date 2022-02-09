package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Operation(summary = "this api is to add a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#teacherId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"teacher\")")
    @PostMapping("/teachers/{teacherId}/courses")
    public HttpResponse<Course> addCourse(
            @PathVariable("teacherId") Long teacherId,
            @RequestHeader("AUTHORIZATION") String header,
            @RequestBody Course course,
            Authentication authentication) {

        HttpResponse<Course> response = new HttpResponse<>();
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher != null) {
            course.setTeacher(teacher);
            courseRepository.save(course);
            response.setData(course);
        }
        response.setSuccess(response.getData() != null);
        return response;
    }

    @Operation(summary = "this api is to list all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure")})
    @GetMapping("/courses")
    public HttpResponse<Page<CourseModel>> listAllCourses(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {

        HttpResponse<Page<CourseModel>> response = new HttpResponse<>();
        Page<Course> courses = courseRepository.findAll(PageRequest.of(page.orElse(0), 5));
        List<CourseModel> courseModels = courses.stream().map(course -> CourseMapper.toModel(course)).collect(Collectors.toList());
        response.setData(new PageImpl<>(courseModels));
        response.setSuccess(true);
        return response;
    }

    @Operation(summary = "this api is to list top 10 courses of all time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/courses/all_time_top_ten")
    public HttpResponse<List<CourseModel>> listAllTimeTopTenCourses(@RequestHeader("AUTHORIZATION") String header) {

        HttpResponse<List<CourseModel>> response = new HttpResponse<>();
        response.setData(courseService.findAllTimeTopTen().stream().map(course -> CourseMapper.toModel(course)).collect(Collectors.toList()));
        response.setSuccess(true);
        return response;
    }


    @Operation(summary = "this api is to list top 10 trending courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/courses/top_trending")
    public HttpResponse<List<CourseModel>> listTopTenTrendingCourses(@RequestHeader("AUTHORIZATION") String header) {

        HttpResponse<List<CourseModel>> response = new HttpResponse<>();
        response.setData(courseService.findTopTenTrendingCourses().stream().map(course -> CourseMapper.toModel(course)).collect(Collectors.toList()));
        response.setSuccess(true);
        return response;
    }


    @Operation(summary = "this api is to delete a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure")})
    @PreAuthorize("#authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @DeleteMapping("/courses/{courseId}")
    public HttpResponse<String> DeleteCourse(@RequestHeader("AUTHORIZATION") String header,
                                             @PathVariable("courseId") Long courseId,
                                             Authentication authentication) {

        HttpResponse<String> response = new HttpResponse<>();
        enrollmentService.deleteEnrollments(courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
            response.setSuccess(true);
        }
        return response;
    }

}
