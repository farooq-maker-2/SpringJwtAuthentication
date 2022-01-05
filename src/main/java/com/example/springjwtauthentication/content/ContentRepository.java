package com.example.springjwtauthentication.content;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findContentById(Long contentId);
}
