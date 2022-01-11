package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Content;
import com.example.springjwtauthentication.entity.Course;
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

    @Autowired
    private CourseContentService courseContentService;

    public Content saveCourseContent(Content content) {
        return courseContentRepository.save(content);
    }

    public Content getSingleContent(Long contentId) {
        return courseContentRepository.findContentById(contentId);
    }

    public ContentModel toModel(Content content) {
        return ContentModel.builder()
                .id(content.getId())
                .contentType(content.getContentType())
                .description(content.getDescription())
                .fileName(content.getFileName())
                .build();
    }

    public Content toEntity(ContentModel content) {
        return Content.builder()
                .contentType(content.getContentType())
                .description(content.getDescription())
                .fileName(content.getFileName())
                .build();
    }

    public List<ContentModel> getCourseContents(Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        List<Content> contents = course.getCourseContents();
        List<ContentModel> contentModels = contents.stream().map(content -> courseContentService.toModel(content)).collect(Collectors.toList());
        return contentModels;
    }
}
