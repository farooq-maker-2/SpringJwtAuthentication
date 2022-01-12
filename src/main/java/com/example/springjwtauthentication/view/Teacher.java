package com.example.springjwtauthentication.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@Setter
//@Getter
@AllArgsConstructor
//@ToString
public class Teacher{

    private Long id;
    private String firstName;
    private String lastName;
    private String status = "activated";
    private final String role = "teacher";

}
