package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.controller.response.HttpResponse;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.mapper.UserMapper;
import com.example.springjwtauthentication.repository.AdminRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.utils.LoginCreds;
import com.example.springjwtauthentication.view.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "this api is to register the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PostMapping("/users/register")
    public HttpResponse<Boolean> registerUser(@RequestBody User user) {

        HttpResponse<Boolean> response = new HttpResponse<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole().equalsIgnoreCase("student")) {
            studentRepository.save(UserMapper.toStudent(user));
            response.setSuccess(true);
        } else if (user.getRole().equalsIgnoreCase("teacher")) {
            teacherRepository.save(UserMapper.toTeacher(user));
            response.setSuccess(true);
        }
        return response;
    }


    @Operation(summary = "this api is to login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PostMapping("/users/login")
    public HttpResponse<UserView> UserLogin(@RequestBody LoginCreds loginCreds) {
        //all authentication is handled in AuthenticationFilter
        HttpResponse<UserView> userView = new HttpResponse<>();
        userView.setData(UserView.toUserView(teacherRepository.findTeacherByEmail(loginCreds.getEmail())));
        if (userView.getData() == null) {
            userView.setData(UserView.toUserView(studentRepository.findStudentByEmail(loginCreds.getEmail())));
            if (userView.getData() == null) {
                userView.setData(UserView.toUserView(adminRepository.findAdminByEmail(loginCreds.getEmail())));
            }
        }
        userView.setSuccess(userView.getData() != null);
        return userView;
    }


    @Operation(summary = "this api is to deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PreAuthorize("#userId.toString().equals(authentication.principal) " +
            "&& #authentication.getAuthorities().toArray()[0].toString().equals(#role)" +
            "|| #authentication.getAuthorities().toArray()[0].toString().equals(\"admin\")")
    @DeleteMapping("/users/{userId}/deactivate/{role}")
    public HttpResponse<Boolean> deactivateUser(@RequestHeader("AUTHORIZATION") String header,
                                                @PathVariable("userId") Long userId,
                                                @PathVariable("role") String role,
                                                Authentication authentication) {

        HttpResponse<Boolean> response = new HttpResponse<>();
        if (role.equalsIgnoreCase("student")) {
            response.setSuccess(studentService
                    .optoutAndDeleteStudent(studentRepository.findStudentById(userId)));

        } else if (role.equalsIgnoreCase("teacher")) {
            Teacher teacher = teacherRepository.findTeacherById(userId);
            teacher.setStatus("deactivated");
            teacherRepository.save(teacher);
            response.setSuccess(true);
        }
        return response;
    }

}
