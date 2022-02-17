package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.TeacherRepository;
import com.example.springjwtauthentication.view.response.UserView;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public HttpResponse<String> deactivateTeacher(Long teacherId) {

        HttpResponse<String> response = new HttpResponse<>();
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            teacher.get().setStatus("deactivated");
            teacherRepository.save(teacher.get());
            response.setSuccess(true);
        }
        return response;
    }

    public HttpResponse<Page<UserView>> listAllTeachers(Optional<Integer> page) {

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

    public HttpResponse<List<UserView>> listTeachersByName(String teacherName, String teacherName1) {

        HttpResponse<List<UserView>> response = new HttpResponse<>();
        List<Teacher> teachers = teacherRepository.
                findByFirstNameOrLastName(/*PageRequest.of(page.orElse(0), pageSize.orElse(5)),*/ teacherName, teacherName);
        List<UserView> userViews = new ArrayList<>();
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toUserView(teacher));
        });
        response.setData(userViews);
        response.setSuccess(true);
        return response;

    }

    public HttpResponse<Set<CourseModel>> getCoursesOfTeacher(Long teacherId, Optional<Integer> page, Optional<Integer> pageSize) {

        HttpResponse<Set<CourseModel>> response = new HttpResponse<>();
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);//.findTeacherById(teacherId);
        if (teacher.isPresent()) {
            Set<Course> courses = teacher.get().getCourses();
            Set<CourseModel> courseModels = courses.stream().map(CourseMapper::toModel).collect(Collectors.toSet());
            int fromIndex = page.orElse(0) * pageSize.orElse(5);
            if (courses.size() <= fromIndex) {
                response.setData(Collections.emptySet());
            }
            List<CourseModel> coursesList = new ArrayList<>(courseModels);
            // toIndex exclusive
            response.setData(new HashSet<>(coursesList
                    .subList(fromIndex, Math.min(fromIndex + pageSize.orElse(5), coursesList.size()))));
            response.setSuccess(true);
        }
        return response;
    }

    public Optional<Teacher> findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findTeacherByEmail(email);
    }
}
