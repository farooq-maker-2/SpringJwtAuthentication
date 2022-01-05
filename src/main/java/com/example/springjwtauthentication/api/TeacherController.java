package com.example.springjwtauthentication.api;

import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.teacher.Teacher;
import com.example.springjwtauthentication.teacher.api.TeacherService;
import com.example.springjwtauthentication.user.User;
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
    public Teacher deactivateTeacher(@RequestHeader("AUTHORIZATION") String header,
                                     @PathVariable("teacherId") Long teacherId) {

        User user = userService.findUserById(teacherId);
        Teacher teacher = teacherService.findTeacherByEmail(user.getEmail());
        if (teacher != null) {
            teacher.setStatus("deactivated");
            return teacherService.saveTeacher(teacher);
        } else {
            //ApiError apiError = new ApiError(NOT_FOUND);
            //apiError.setMessage(ex.getMessage());
            return null;//buildResponseEntity(apiError);
        }
    }

    @GetMapping("/teachers")
    public Page<Teacher> listAllTeachers(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {
        return teacherService.findAll(PageRequest.of(page.orElse(0), 2));
    }

    @GetMapping("/teachers/{name}")
    public List<Teacher> listTeachersByName(@RequestHeader("AUTHORIZATION") String header,
                                            @PathVariable("name") String teacherName,
                                            @RequestParam Optional<Integer> page) {
        return teacherService.findTeachersByName(PageRequest.of(page.orElse(0), 2), teacherName);
    }

}
