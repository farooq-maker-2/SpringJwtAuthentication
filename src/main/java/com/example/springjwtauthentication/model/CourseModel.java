package com.example.springjwtauthentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseModel {

    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Schema(example = "database")
    private String courseName;

    @Schema(example = "description")
    private String description;

    @Schema(example = "500")
    private String level;

}

