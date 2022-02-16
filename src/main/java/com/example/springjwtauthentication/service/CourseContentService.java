package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Content;
import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.mapper.ContentMapper;
import com.example.springjwtauthentication.model.ContentModel;
import com.example.springjwtauthentication.repository.ContentRepository;
import com.example.springjwtauthentication.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseContentService {

    private final ContentRepository courseContentRepository;
    private final CourseRepository courseRepository;

    public void saveCourseContent(Content content) {
        courseContentRepository.save(content);
    }

    public List<ContentModel> getCourseContents(Long courseId) {

        Optional<Course> course = courseRepository.findById(courseId);
        List<Content> contents = course.get().getCourseContents();
        return contents.stream().map(ContentMapper::toModel).collect(Collectors.toList());
    }
}
