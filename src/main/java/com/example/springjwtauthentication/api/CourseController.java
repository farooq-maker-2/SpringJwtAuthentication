package com.example.springjwtauthentication.api;

import com.example.springjwtauthentication.course.Course;
import com.example.springjwtauthentication.course.CourseRepository;
import com.example.springjwtauthentication.service.CourseService;
import com.example.springjwtauthentication.service.EnrollmentService;
import com.example.springjwtauthentication.service.StudentService;
import com.example.springjwtauthentication.service.UserService;
import com.example.springjwtauthentication.student.Student;
import com.example.springjwtauthentication.teacher.Teacher;
import com.example.springjwtauthentication.service.TeacherService;
import com.example.springjwtauthentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/teachers/{teacherId}/courses")
    public Course addCourse(
            @PathVariable("teacherId") Long teacherId,
            @RequestHeader("AUTHORIZATION") String header,
            @RequestBody Course course/*,
            @RequestParam("username") String email*/) {

        User user = userService.findUserById(teacherId);
        Teacher teacher = teacherService.findTeacherByEmail(user.getEmail());
        if (teacher != null) {
            course.setTeacher(teacher);
            courseRepository.save(course);
            teacher.getCourses().add(course);
            teacherService.saveTeacher(teacher);
            return course;
        } else {
            return null;
        }
    }

    @GetMapping("/students/{studentId}/courses")
    public Set<Course> getCoursesOfStudent(@RequestHeader("AUTHORIZATION") String header,
                                           @PathVariable("studentId") Long studentId,
                                           @RequestParam Optional<Integer> page) {

        User user = userService.findUserById(studentId);
        Student student = studentService.findStudentByEmail(user.getEmail());
        Set<Course> courses = studentService.findStudentById(student.getId()).getCourses();

        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        if (courses == null || courses.size() <= fromIndex) {
            return Collections.emptySet();
        }
        List<Course> coursesList = new ArrayList<>(courses);
        // toIndex exclusive
        return new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size())));

    }

    @GetMapping("/courses")
    public Page<Course> listAllCourses(@RequestHeader("AUTHORIZATION") String header
            , @RequestParam Optional<Integer> page) {

        return courseRepository.findAll(PageRequest.of(page.orElse(0), 5));
    }

    @GetMapping("/courses/all_time_top_ten")
    public List<Course> listAllTimeTopTenCourses(@RequestHeader("AUTHORIZATION") String header/*, @RequestParam("username") String username*/) {
        return courseService.findAllTimeTopTen();
    }

    @GetMapping("/courses/top_trending")
    public List<Course> listTopTenTrendingCourses(@RequestHeader("AUTHORIZATION") String header/*, @RequestParam("username") String username*/) {
        return courseService.findTopTenTrendingCourses();
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public Set<Course> getCoursesOfTeacher(@RequestHeader("AUTHORIZATION") String header,
                                           @PathVariable("teacherId") Long teacherId,
                                           @RequestParam Optional<Integer> page
    ) {

        User user = userService.findUserById(teacherId);
        Teacher teacher = teacherService.findTeacherByEmail(user.getEmail());
        Set<Course> courses = teacher.getCourses();

        int pageSize = 5;
        int fromIndex = page.get() * pageSize;
        if (courses == null || courses.size() <= fromIndex) {
            return Collections.emptySet();
        }
        List<Course> coursesList = new ArrayList<>(courses);
        // toIndex exclusive
        return new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize, coursesList.size())));
    }

    @DeleteMapping("/courses/{courseId}")
    public String DeleteCourse(@RequestHeader("AUTHORIZATION") String header,
                               @PathVariable("courseId") Long courseId) {

        studentService.deleteEnrollments(courseRepository.findCourseById(courseId));
        enrollmentService.deleteEnrollments(courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        }
        return "success";
    }

}
