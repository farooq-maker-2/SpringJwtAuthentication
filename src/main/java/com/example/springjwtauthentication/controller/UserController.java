package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Admin;
import com.example.springjwtauthentication.repository.AdminRepository;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "register user", response = User.class)
    @PostMapping("/users/register")
    public String registerUser(@RequestBody User user) {

        boolean saved = false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole().equalsIgnoreCase("student")) {
            //user.setRole(ApplicationUserRole.STUDENT.name().toLowerCase());
            studentService.saveStudent(toStudent(user));
            saved = true;
        } else if (user.getRole().equalsIgnoreCase("teacher")) {
            //user.setRole(ApplicationUserRole.TEACHER.name().toLowerCase());
            teacherService.saveTeacher(toTeacher(user));
            saved = true;
        } else if (user.getRole().equalsIgnoreCase("admin")) {
            adminRepository.save(toAdmin(user));
            saved = true;
        }
        if (saved) {
            userService.saveUser(user);
            //JwtHelper.generateJwtCode(request, response, authentication);
            return "User register successfully...!!!";
        }
        return "";
    }

    @PostMapping("/users/login")
    public User UserLogin(@RequestParam String email, @RequestParam String password) {
        //all authentication is handled in AuthenticationFilter
        return userService.findUserByEmail(email);
    }

    @DeleteMapping("/users/{userId}/deactivate/{role}")
    public String deactivateUser(@RequestHeader("AUTHORIZATION") String header,
                                 @PathVariable("userId") Long userId,
                                 @PathVariable("userId") String role) {

        User user = userService.findUserById(userId);
        boolean success = false;
        if (user.getRole().equalsIgnoreCase("student")) {
            Student student = studentService.findStudentByEmail(user.getEmail());
            success = studentService.optoutAndDeleteStudent(student);

        } else if (user.getRole().equalsIgnoreCase("teacher")) {
            Teacher teacher = teacherService.findTeacherByEmail(user.getEmail());
            user.setStatus("deactivated");
            teacher.setStatus("deactivated");
            teacherService.saveTeacher(teacher);
            userService.saveUser(user);
            success = true;
        }

        if (success && user.getRole().equalsIgnoreCase("student")) {
            userService.deleteUser(user);
            return "success";
        }
        return null;
    }

    private Student toStudent(User user) {

        return Student.builder().status("activated")
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).build();
    }

    private Teacher toTeacher(User user) {

        return Teacher.builder()
                .status("activated")
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName()).build();

    }

    private Admin toAdmin(User user) {

        Admin admin = new Admin();
        admin.setStatus("activated");
        admin.setEmail(user.getEmail());
        admin.setPassword(user.getPassword());
        admin.setFirstName(user.getFirstName());
        admin.setLastName(user.getLastName());
        return admin;

    }

}
