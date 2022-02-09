package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.TeacherService;
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
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @PreAuthorize("#teacherId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"teacher\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @PutMapping("/teachers/{teacherId}/deactivate")
    public HttpResponse<String> deactivateTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                  @PathVariable("teacherId") Long teacherId,
                                                  Authentication authentication) {

        HttpResponse<String> response = new HttpResponse<>();
        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher != null) {
            teacher.setStatus("deactivated");
            teacherService.saveTeacher(teacher);
            response.setSuccess(true);
        }
        return response;
    }

    @Operation(summary = "this api is to list all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @GetMapping("/teachers")
    public HttpResponse<Page<UserView>> listAllTeachers(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page
            , Authentication authentication) {

        HttpResponse<Page<UserView>> response = new HttpResponse<>();
        List<UserView> userViews = new ArrayList<>();
        Page<Teacher> teachers = teacherRepository.findAll(PageRequest.of(page.orElse(0), 5));
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toUserView(teacher));
        });
        response.setData(new PageImpl<>(userViews));
        response.setSuccess(true);
        return response;
    }

    @Operation(summary = "this api is to list all courses of teacher by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#authentication.getAuthorities().toArray()[0].toString().equals(\"student\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @GetMapping("/teachers/{name}")
    public HttpResponse<List<UserView>> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                                           @PathVariable("name") String teacherName,
                                                           @RequestParam Optional<Integer> page,
                                                           Authentication authentication) {

        HttpResponse<List<UserView>> response = new HttpResponse<>();
        List<Teacher> teachers = teacherRepository.findTeachersByFirstNameAndLastName(PageRequest.of(page.orElse(0), 5), teacherName);
        List<UserView> userViews = new ArrayList<>();
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toUserView(teacher));
        });
        response.setData(userViews);
        response.setSuccess(true);
        return response;
    }

    @PreAuthorize("#teacherId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(\"teacher\")" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @GetMapping("/teachers/{teacherId}/courses")
    public HttpResponse<Set<CourseModel>> getCoursesOfTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                              @PathVariable("teacherId") Long teacherId,
                                                              @RequestParam Optional<Integer> page,
                                                              Authentication authentication) {

        HttpResponse<Set<CourseModel>> response = new HttpResponse<>();
        User teacher = teacherRepository.findTeacherById(teacherId);
        Set<Course> coursesOfTeacher = teacherRepository.findTeacherById(teacher.getId()).getCourses();
        Set<Course> courses = coursesOfTeacher;
        Set<CourseModel> courseModels = courses.stream().map(course -> CourseMapper.toModel(course)).collect(Collectors.toSet());
        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        if (courses == null || courses.size() <= fromIndex) {
            response.setData(Collections.emptySet());
        }
        List<CourseModel> coursesList = new ArrayList<>(courseModels);
        // toIndex exclusive
        response.setData(new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size()))));
        response.setSuccess(true);
        return response;
    }

}
