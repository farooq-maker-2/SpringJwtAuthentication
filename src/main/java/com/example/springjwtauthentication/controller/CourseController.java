package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
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
    @PostMapping("/teachers/{teacherId}/courses")
    public Course addCourse(
            @PathVariable("teacherId") Long teacherId,
            @RequestHeader("AUTHORIZATION") String header,
            @RequestBody Course course) {

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher != null) {
            course.setTeacher(teacher);
            courseRepository.save(course);
            return course;
        } else {
            return null;
        }
    }

    @Operation(summary = "this api is to list all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure")})
    @GetMapping("/courses")
    public Page<CourseModel> listAllCourses(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {

        Page<Course> courses = courseRepository.findAll(PageRequest.of(page.orElse(0), 5));
        List<CourseModel> courseModels = courses.stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
        return new PageImpl<>(courseModels);
    }

    @Operation(summary = "this api is to list top 10 courses of all time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/courses/all_time_top_ten")
    public List<CourseModel> listAllTimeTopTenCourses(@RequestHeader("AUTHORIZATION") String header) {
        return courseService.findAllTimeTopTen().stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
    }

    @Operation(summary = "this api is to list top 10 trending courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/courses/top_trending")
    public List<CourseModel> listTopTenTrendingCourses(@RequestHeader("AUTHORIZATION") String header) {
        return courseService.findTopTenTrendingCourses().stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
    }

    @Operation(summary = "this api is to delete a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure")})
    @DeleteMapping("/courses/{courseId}")
    public String DeleteCourse(@RequestHeader("AUTHORIZATION") String header,
                               @PathVariable("courseId") Long courseId) {

        enrollmentService.deleteEnrollments(courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        }
        return "success";
    }

}
