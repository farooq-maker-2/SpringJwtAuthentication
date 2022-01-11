package com.example.springjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentModel {

    private Long id;
    private Date enrollmentDate;
//    @JsonIgnore
//    private CourseModel course;
}
