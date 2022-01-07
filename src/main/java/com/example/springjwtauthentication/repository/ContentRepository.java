package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findContentById(Long contentId);
}
