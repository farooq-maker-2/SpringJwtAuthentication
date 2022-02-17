package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.view.response.HttpResponse;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Teacher;
import com.example.springjwtauthentication.mapper.CourseMapper;
import com.example.springjwtauthentication.model.CourseModel;
import com.example.springjwtauthentication.repository.ContentRepository;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ContentRepository contentRepository;
    private final TeacherRepository teacherRepository;

    public HttpResponse<List<CourseModel>> findAllTimeTopTen() {

        HttpResponse<List<CourseModel>> response = new HttpResponse<>();
        response.setData(courseRepository
                .findFirst10ByOrderByAllTimeEnrollmentsDesc()
                .stream().map(CourseMapper::toModel).collect(Collectors.toList()));
        response.setSuccess(true);
        return response;
    }

    public HttpResponse<List<CourseModel>> findTopTenTrendingCourses() {

        HttpResponse<List<CourseModel>> response = new HttpResponse<>();
        response.setData(courseRepository
                .findFirst10ByOrderByTrendingEnrollmentsDesc()
                .stream().map(CourseMapper::toModel).collect(Collectors.toList()));
        response.setSuccess(true);
        return response;

    }

    public Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }

    public HttpResponse<Course> addCourse(Course course, Long teacherId) {

        HttpResponse<Course> response = new HttpResponse<>();
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if (teacher.isPresent()) {
            course.setTeacher(teacher.get());
            courseRepository.save(course);
            response.setData(course);
        }
        response.setSuccess(response.getData() != null);
        return response;
    }

    public HttpResponse<Page<CourseModel>> listAllCourses(Optional<Integer> page, Optional<Integer> pageSize) {

        HttpResponse<Page<CourseModel>> response = new HttpResponse<>();
        Page<Course> courses = courseRepository.findAll(PageRequest.of(page.orElse(0), pageSize.orElse(5)));
        List<CourseModel> courseModels = courses.stream().map(CourseMapper::toModel).collect(Collectors.toList());
        response.setData(new PageImpl<>(courseModels));
        response.setSuccess(true);
        return response;

    }

    public HttpResponse<String> deleteCourse(Long courseId) {

        HttpResponse<String> response = new HttpResponse<>();
        contentRepository.deleteContentByCourseId(courseId);
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteEnrollmentsById(courseId);
            courseRepository.deleteById(courseId);
            response.setSuccess(true);
        }
        return response;

    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
}
