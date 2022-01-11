package com.example.springjwtauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String status = "activated";
    private String role;

}
