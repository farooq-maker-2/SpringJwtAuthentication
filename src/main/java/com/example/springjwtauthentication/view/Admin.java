package com.example.springjwtauthentication.view;

import lombok.Data;

@Data
public class Admin {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String status = "activated";
    private final String role = "admin";

}
