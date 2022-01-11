package com.example.springjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
//@Setter
//@Getter
@AllArgsConstructor
//@ToString
public class TeacherModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String status = "activated";
    private final String role = "teacher";
//    @JsonIgnore
//    private Set<CourseModel> courses;

}
