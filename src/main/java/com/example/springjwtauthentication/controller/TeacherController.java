package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherRepository teacherRepository;

    @PutMapping("/teachers/{teacherId}/deactivate")
    public String deactivateTeacher(@RequestHeader("AUTHORIZATION") String header,
                                    @PathVariable("teacherId") Long teacherId) {

        UserModel user = userService.findUserById(teacherId);
        TeacherModel teacher = teacherService.findTeacherByEmail(user.getEmail());
        if (teacher != null) {
            teacher.setStatus("deactivated");
            teacherService.saveTeacher(teacher);
            return "success";
        } else {
            //ApiError apiError = new ApiError(NOT_FOUND);
            //apiError.setMessage(ex.getMessage());
            return null;//buildResponseEntity(apiError);
        }
    }

    @Operation(summary = "this api is to list all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/teachers")
    public Page<TeacherModel> listAllTeachers(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        return teacherService.findAll(PageRequest.of(page.orElse(0), 5));
    }

    @Operation(summary = "this api is to list all courses of teacher by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/teachers/{name}")
    public List<TeacherModel> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                                 @PathVariable("name") String teacherName,
                                                 @RequestParam Optional<Integer> page) {

        return teacherService.findTeachersByName(PageRequest.of(page.orElse(0), 5), teacherName);
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public Set<CourseModel> getCoursesOfTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("teacherId") Long teacherId,
                                                @RequestParam Optional<Integer> page
    ) {

        UserModel user = userService.findUserById(teacherId);
        TeacherModel teacher = teacherService.findTeacherByEmail(user.getEmail());
        Set<Course> coursesOfTeacher = teacherRepository.findTeacherById(teacher.getId()).getCourses();
        Set<Course> courses = coursesOfTeacher;
        Set<CourseModel> courseModels = courses.stream().map(course -> courseService.toModel(course)).collect(Collectors.toSet());
        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        if (courses == null || courses.size() <= fromIndex) {
            return Collections.emptySet();
        }
        List<CourseModel> coursesList = new ArrayList<>(courseModels);
        // toIndex exclusive
        return new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size())));
    }

}
