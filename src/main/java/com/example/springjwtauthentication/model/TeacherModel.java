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
public class TeacherModel {

    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Schema(example = "muhammad")
    private String firstName;

    @Schema(example = "farooq")
    private String lastName;

    @Schema(example = "farooq.student@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;

    @Schema(example = "activated")
    private String status = "activated";

    @Schema(example = "teacher")
    private String role;

}
