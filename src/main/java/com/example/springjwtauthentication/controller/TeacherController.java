package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.CourseService;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherRepository teacherRepository;

    @PutMapping("/teachers/{teacherId}/deactivate")
    public String deactivateTeacher(@RequestHeader("AUTHORIZATION") String header,
                                    @PathVariable("teacherId") Long teacherId) {

        Teacher teacher = teacherRepository.findTeacherById(teacherId);
        if (teacher != null) {
            teacher.setStatus("deactivated");
            teacherService.saveTeacher(teacher);
            return "success";
        } else {
            return null;
        }
    }

    @Operation(summary = "this api is to list all teachers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/teachers")
    public Page<UserView> listAllTeachers(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        Page<Teacher> teachers = teacherRepository.findAll(PageRequest.of(page.orElse(0), 5));
        List<UserView> userViews = new ArrayList<>();
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toView(teacher));
        });

        return new PageImpl<>(userViews);
    }

    @Operation(summary = "this api is to list all courses of teacher by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @GetMapping("/teachers/{name}")
    public List<UserView> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                             @PathVariable("name") String teacherName,
                                             @RequestParam Optional<Integer> page) {

        List<Teacher> teachers = teacherRepository.findTeachersByFirstNameAndLastName(PageRequest.of(page.orElse(0), 5), teacherName);

        List<UserView> userViews = new ArrayList<>();
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toView(teacher));
        });
        return userViews;
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public Set<CourseModel> getCoursesOfTeacher(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("teacherId") Long teacherId,
                                                @RequestParam Optional<Integer> page
    ) {

        User teacher = teacherRepository.findTeacherById(teacherId);
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
