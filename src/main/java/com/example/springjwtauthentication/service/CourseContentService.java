package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Content;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.mapper.ContentMapper;
import com.example.springjwtauthentication.model.ContentModel;
import com.example.springjwtauthentication.repository.ContentRepository;
import com.example.springjwtauthentication.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseContentService {

    @Autowired
    private ContentRepository courseContentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Content saveCourseContent(Content content) {
        return courseContentRepository.save(content);
    }

    public List<ContentModel> getCourseContents(Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        List<Content> contents = course.getCourseContents();
        return contents.stream().map(content -> ContentMapper.toModel(content)).collect(Collectors.toList());
    }
}
