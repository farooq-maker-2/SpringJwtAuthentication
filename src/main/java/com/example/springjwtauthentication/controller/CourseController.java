package com.example.springjwtauthentication.controller;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.model.TeacherModel;
import com.example.springjwtauthentication.model.UserModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.service.*;
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
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/teachers/{teacherId}/courses")
    public Course addCourse(
            @PathVariable("teacherId") Long teacherId,
            @RequestHeader("AUTHORIZATION") String header,
            @RequestBody Course course) {

        UserModel user = userService.findUserById(teacherId);
        Teacher teacher = teacherRepository.findTeacherByEmail(user.getEmail());
        if (teacher != null) {
            course.setTeacher(teacher);
            courseRepository.save(course);
            return course;
        } else {
            return null;
        }
    }

    @GetMapping("/courses")
    public Page<CourseModel> listAllCourses(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {

        Page<Course> courses = courseRepository.findAll(PageRequest.of(page.orElse(0), 5));
        List<CourseModel> courseModels = courses.stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
        return new PageImpl<>(courseModels);
    }

    @GetMapping("/courses/all_time_top_ten")
    public List<CourseModel> listAllTimeTopTenCourses(@RequestHeader("AUTHORIZATION") String header) {
        return courseService.findAllTimeTopTen().stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
    }

    @GetMapping("/courses/top_trending")
    public List<CourseModel> listTopTenTrendingCourses(@RequestHeader("AUTHORIZATION") String header) {
        return courseService.findTopTenTrendingCourses().stream().map(course -> courseService.toModel(course)).collect(Collectors.toList());
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

    @DeleteMapping("/courses/{courseId}")
    public String DeleteCourse(@RequestHeader("AUTHORIZATION") String header,
                               @PathVariable("courseId") Long courseId) {

        studentService.deleteEnrollments(courseId);
        enrollmentService.deleteEnrollments(courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        }
        return "success";
    }

}
