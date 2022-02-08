package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.AdminRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.utils.LoginCreds;
import com.example.springjwtauthentication.view.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
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
    public String registerUser(@RequestBody User user) {

        boolean saved = false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole().equalsIgnoreCase("student")) {
            studentRepository.save(toStudent(user));
            saved = true;
        } else if (user.getRole().equalsIgnoreCase("teacher")) {
            teacherRepository.save(toTeacher(user));
            saved = true;
        }
//        if (saved) {
//            userService.saveUser(userService.toModel(user));
//            return "success";
//        }
        return "failure";
    }

    @Operation(summary = "this api is to login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @PostMapping("/users/login")
    public UserView UserLogin(@RequestBody LoginCreds loginCreds) {
        //all authentication is handled in AuthenticationFilter
        UserView userView = UserView.toView(teacherRepository.findTeacherByEmail(loginCreds.getEmail()));
        if(userView == null){
            userView = UserView.toStudentView(studentRepository.findStudentByEmail(loginCreds.getEmail()));
            if(userView == null){
                userView = UserView.toView(adminRepository.findAdminByEmail(loginCreds.getEmail()));
            }
        }
        return userView;
    }

    @Operation(summary = "this api is to deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "failure", content = @Content)})
    @DeleteMapping("/users/{userId}/deactivate/{role}")
    public boolean deactivateUser(@RequestHeader("AUTHORIZATION") String header,
                                 @PathVariable("userId") Long userId,
                                 @PathVariable("userId") String role) {

        boolean success = false;
        if (role.equalsIgnoreCase("student")) {
            success = studentService
                    .optoutAndDeleteStudent(studentRepository.findStudentById(userId));
            success = true;

        } else if (role.equalsIgnoreCase("teacher")) {
            Teacher teacher = teacherRepository.findTeacherById(userId);
            teacher.setStatus("deactivated");
            teacherRepository.save(teacher);
            success = true;
        }
        return success;
    }

    private Student toStudent(User user) {

        Student student = new Student();
        student.setStatus("activated");
        student.setEmail(user.getEmail());
        student.setPassword(user.getPassword());
        student.setFirstName(user.getFirstName());
        student.setLastName(user.getLastName());
        student.setRole("student");
        return student;
    }

    private Teacher toTeacher(User user) {

        Teacher teacher = new Teacher();
        teacher.setStatus("activated");
        teacher.setEmail(user.getEmail());
        teacher.setPassword(user.getPassword());
        teacher.setFirstName(user.getFirstName());
        teacher.setLastName(user.getLastName());
        teacher.setRole("teacher");
        return teacher;
    }

}
