package com.example.springjwtauthentication.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student{

    private Long id;
    private String firstName;
    private String lastName;
    private String status = "activated";
    private final String role = "student";

}
