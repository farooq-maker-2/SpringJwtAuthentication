package com.example.springjwtauthentication.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginCreds {

    @Schema(example = "farooq.teacher@gmail.com")
    private String email;

    @Schema(example = "password")
    private String password;
}
