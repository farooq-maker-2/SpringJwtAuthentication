package com.example.springjwtauthentication.mapper;

import com.example.springjwtauthentication.entity.Content;
import com.example.springjwtauthentication.model.ContentModel;

public class ContentMapper {

    public static ContentModel toModel(Content content) {
        return ContentModel.builder()
                .id(content.getId())
                .contentType(content.getContentType())
                .description(content.getDescription())
                .fileName(content.getFileName())
                .build();
    }

    public static Content toEntity(ContentModel content) {
        return Content.builder()
                .contentType(content.getContentType())
                .description(content.getDescription())
                .fileName(content.getFileName())
                .build();
    }
}
