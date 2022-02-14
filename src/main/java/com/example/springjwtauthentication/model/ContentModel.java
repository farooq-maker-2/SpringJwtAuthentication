package com.example.springjwtauthentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentModel {

    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Schema(example = "description")
    private String description;

    @Schema(example = "content.pdf")
    private String fileName;

    @Schema(example = "text/plain")
    private String contentType;

}
