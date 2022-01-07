package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Content;
import com.example.springjwtauthentication.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseContentService {

    @Autowired
    private ContentRepository courseContentRepository;

    public Content saveCourseContent(Content content) {
        return courseContentRepository.save(content);
    }

    public Content getSingleContent(Long contentId) {
        return courseContentRepository.findContentById(contentId);
    }

}
