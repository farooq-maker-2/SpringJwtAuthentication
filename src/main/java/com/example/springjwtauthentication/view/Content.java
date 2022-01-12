package com.example.springjwtauthentication.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    private Long id;
    private String description;
    private String fileName;
    private byte[] content;
    private String contentType;

}
