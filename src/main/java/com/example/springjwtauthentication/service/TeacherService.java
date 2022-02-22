package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.UserRepository;
import com.example.springjwtauthentication.view.response.HttpResponse;
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

    private final UserRepository userRepository;

    public HttpResponse<String> deactivateTeacher(Long teacherId) {

        HttpResponse<String> response = new HttpResponse<>();
        Optional<User> teacher = userRepository.findById(teacherId);
        if (teacher.isPresent()) {
            teacher.get().setStatus("deactivated");
            userRepository.save(teacher.get());
            response.setSuccess(true);
        }
        return response;
    }

    public HttpResponse<Page<UserView>> listAllTeachers(Optional<Integer> page, Optional<Integer> pageSize) {

        HttpResponse<Page<UserView>> response = new HttpResponse<>();
        List<UserView> userViews = new ArrayList<>();
        Page<User> teachers = userRepository.
                findAllByRole("TEACHER", PageRequest.of(page.orElse(0), pageSize.orElse(20)));
        teachers.stream().forEach(teacher -> {
            userViews.add(UserView.toUserView(teacher));
        });
        response.setData(new PageImpl<>(userViews));
        response.setSuccess(true);
        return response;
    }

    public HttpResponse<List<UserView>> listTeachersByName(String teacherName, String teacherName1) {

        HttpResponse<List<UserView>> response = new HttpResponse<>();
        List<User> teachers = userRepository.
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
        Optional<User> teacher = userRepository.findById(teacherId);
        if (teacher.isPresent()) {
            Set<Course> courses = teacher.get().getTeacherCourses();
            Set<CourseModel> courseModels = courses.stream().map(CourseMapper::toModel).collect(Collectors.toSet());
            int fromIndex = page.orElse(0) * pageSize.orElse(20);
            if (courses.size() <= fromIndex) {
                response.setData(Collections.emptySet());
            }
            List<CourseModel> coursesList = new ArrayList<>(courseModels);
            // toIndex exclusive
            response.setData(new HashSet<>(coursesList
                    .subList(fromIndex, Math.min(fromIndex + pageSize.orElse(20), coursesList.size()))));
            response.setSuccess(true);
        }
        return response;
    }

}
