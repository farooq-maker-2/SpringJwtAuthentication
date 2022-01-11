package com.example.springjwtauthentication.view;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Details of user")
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String status = "activated";
    private String role;

}
