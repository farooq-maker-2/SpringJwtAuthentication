package com.example.springjwtauthentication.model;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
public class AdminModel {

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

    @Schema(example = "admin")
    private String role;

}
