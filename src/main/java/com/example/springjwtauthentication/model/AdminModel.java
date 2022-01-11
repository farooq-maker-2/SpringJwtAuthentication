package com.example.springjwtauthentication.model;

import lombok.Data;

@Data
public class AdminModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String status = "activated";
    private final String role = "admin";

}
