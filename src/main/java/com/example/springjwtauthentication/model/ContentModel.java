package com.example.springjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentModel {

    private Long id;
    private String description;
    private String fileName;
    private byte[] content;
    private String contentType;

}
