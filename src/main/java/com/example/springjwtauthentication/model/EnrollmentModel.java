package com.example.springjwtauthentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentModel {

    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Schema(example = "23-09-1997")
    private Date enrollmentDate;
}
