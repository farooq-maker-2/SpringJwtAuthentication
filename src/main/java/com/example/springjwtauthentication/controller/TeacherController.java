package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.annotations.IsValidTeacher;
import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.view.UserView;
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
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TeacherController {

    private final TeacherService teacherService;

    @RolesAllowed({"TEACHER", "ADMIN"})
    @IsValidTeacher
    @PutMapping(path = "/teachers/{teacherId}/deactivate", produces = "application/json")
    public HttpResponse<String> deactivateTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                  @PathVariable("teacherId") Long teacherId,
                                                  Authentication authentication) {

        return teacherService.deactivateTeacher(teacherId);
    }

    @RolesAllowed({"ADMIN"})
    @Operation(summary = "this api is to list all teachers")
    @GetMapping(path = "/teachers", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    public HttpResponse<Page<UserView>> listAllTeachers(@RequestHeader("AUTHORIZATION") String header,
                                                        @RequestParam Optional<Integer> page,
                                                        Authentication authentication) {

        return teacherService.listAllTeachers(page);
    }

    @RolesAllowed({"STUDENT", "TEACHER", "ADMIN"})
    @GetMapping(path = "/teachers/{name}", produces = "application/json")
    @Operation(summary = "this api is to list all courses of teacher by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "failure")})
    public HttpResponse<List<UserView>> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                                           @PathVariable("name") String teacherName,
                                                           @RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> pageSize,
                                                           Authentication authentication) {

        return teacherService.listTeachersByName(teacherName, teacherName);
    }

    @RolesAllowed({"STUDENT", "Teacher", "ADMIN"})
    @IsValidTeacher
    @GetMapping(path = "/teachers/{teacherId}/courses", produces = "application/json")
    public HttpResponse<Set<CourseModel>> getCoursesOfTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                              @PathVariable("teacherId") Long teacherId,
                                                              @RequestParam Optional<Integer> page,
                                                              @RequestParam Optional<Integer> pageSize,
                                                              Authentication authentication) {

        return teacherService.getCoursesOfTeacher(teacherId, page, pageSize);
    }

}
