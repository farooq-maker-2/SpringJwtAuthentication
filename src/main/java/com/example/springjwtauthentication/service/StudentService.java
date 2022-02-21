package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import com.example.springjwtauthentication.view.response.UserView;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public HttpResponse<String> enrollStudentForCourse(Long studentId, Long courseId) {

        HttpResponse<String> response = new HttpResponse<>();
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent() && student.isPresent()) {

            AtomicBoolean alreadyEnrolled = new AtomicBoolean(false);
            student.get().getCourses().forEach(c -> {
                if (c.getId().equals(courseId)) {
                    alreadyEnrolled.set(true);
                    response.setSuccess(true);
                    response.setMessage("already enrolled");
                }
            });
            if (!alreadyEnrolled.get()) {
                student.get().getCourses().add(course.get());
                studentRepository.save(student.get());
                response.setSuccess(true);
                response.setMessage("success");
                return response;
            }
        }
        return response;
    }

    public HttpResponse<String> optOutStudentFromCourse(Long studentId, Long courseId) {

        HttpResponse<String> response = new HttpResponse<>();
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        boolean success = false;
        if (course.isPresent() && student.isPresent()) {
            Set<Course> courses = student.get().getCourses();
            for (Course c : courses) {
                if (c.getId() == courseId) {
                    success = true;
                    break;
                }
            }
            if (success) {
                student.get().getCourses().remove(course.get());
                studentRepository.save(student.get());
                response.setSuccess(true);
            }
        }
        return response;
    }

    public HttpResponse<String> optOutAndDeleteStudent(Long studentId) {

        HttpResponse<String> response = new HttpResponse<>();
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            response.setSuccess(true);
        }
        return response;
    }

    public HttpResponse<Page<UserView>> listAllStudents(Optional<Integer> page, Optional<Integer> pageSize) {

        HttpResponse<Page<UserView>> response = new HttpResponse<>();
        Page<Student> students = studentRepository.
                findAll(PageRequest.of(page.orElse(0), pageSize.orElse(20)));
        List<UserView> userViews = new ArrayList<>();
        students.stream().forEach(student -> {
            userViews.add(UserView.toUserView(student));
        });
        response.setData(new PageImpl<>(userViews));
        response.setSuccess(true);
        return response;
    }

    public HttpResponse<Set<CourseModel>> getCoursesOfStudent(Long studentId, Optional<Integer> page, Optional<Integer> pageSize) {

        HttpResponse<Set<CourseModel>> response = new HttpResponse<>();
        int fromIndex = page.orElse(0) * pageSize.orElse(20);
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Set<Course> courses = student.get().getCourses();
            Set<CourseModel> courseModels = courses.stream().map(CourseMapper::toModel).collect(Collectors.toSet());
            if (courseModels.size() <= fromIndex) {
                response.setData(Collections.emptySet());
            }
            List<CourseModel> coursesList = new ArrayList<>(courseModels);
            // toIndex exclusive
            response.setData(new HashSet<>(coursesList.subList(fromIndex, Math.min(fromIndex + pageSize.orElse(20), coursesList.size()))));
            response.setSuccess(true);
        }
        return response;
    }

    public Optional<Student> findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }
}