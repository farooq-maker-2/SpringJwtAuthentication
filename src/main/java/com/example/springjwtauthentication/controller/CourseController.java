package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.annotations.IsValidTeacher;
import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "this api is to add a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @IsValidTeacher
    @RolesAllowed({"TEACHER"})
    @PostMapping(path = "/teachers/{teacherId}/courses", produces = "application/json", consumes = "application/json")
    public HttpResponse<Course> addCourse(@PathVariable("teacherId") Long teacherId,
                                          @RequestHeader("AUTHORIZATION") String header,
                                          @RequestBody Course course,
                                          Authentication authentication) {

        return courseService.addCourse(course, teacherId);
    }

    @Operation(summary = "this api is to list all courses")
    @GetMapping(path = "/courses", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    public HttpResponse<Page<CourseModel>> listAllCourses(@RequestHeader("AUTHORIZATION") String header,
                                                          @RequestParam Optional<Integer> page,
                                                          @RequestParam Optional<Integer> pageSize) {

        return courseService.listAllCourses(page, pageSize);
    }

    @Operation(summary = "this api is to list top 10 courses of all time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @GetMapping(path = "/courses/all_time_top_ten", produces = "application/json")
    public HttpResponse<List<CourseModel>> listAllTimeTopTenCourses(@RequestHeader("AUTHORIZATION") String header) {

        return courseService.findAllTimeTopTen();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @Operation(summary = "this api is to list top 10 trending courses")
    @GetMapping(path = "/courses/top_trending", produces = "application/json")
    public HttpResponse<List<CourseModel>> listTopTenTrendingCourses(@RequestHeader("AUTHORIZATION") String header) {

        return courseService.findTopTenTrendingCourses();
    }


    @RolesAllowed({"TEACHER", "ADMIN"})
    @Operation(summary = "this api is to delete a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    @DeleteMapping(path = "/courses/{courseId}", produces = "application/json")
    public HttpResponse<String> DeleteCourse(@RequestHeader("AUTHORIZATION") String header,
                                             @PathVariable("courseId") Long courseId,
                                             Authentication authentication) {

        return courseService.deleteCourse(courseId);
    }

}
