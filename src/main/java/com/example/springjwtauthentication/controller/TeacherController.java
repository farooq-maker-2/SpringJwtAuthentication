package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/teachers")
    public Page<TeacherModel> listAllTeachers(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        return teacherService.findAll(PageRequest.of(page.orElse(0), 5));
    }

    @GetMapping("/teachers/{name}")
    public List<TeacherModel> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                                 @PathVariable("name") String teacherName,
                                                 @RequestParam Optional<Integer> page) {
        return teacherService.findTeachersByName(PageRequest.of(page.orElse(0), 5), teacherName);
    }

}
