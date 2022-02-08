package com.example.springjwtauthentication.model;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseModel {

    private Long id;
    private String courseName;
    private String description;
    private String level;
    private Long allTimeEnrollments = 0L;
    private Long trendingEnrollments = 0L;

}

