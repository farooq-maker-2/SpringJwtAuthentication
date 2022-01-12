
package com.example.springjwtauthentication.view;


import lombok.*;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {

    private Long id;
    private String courseName;
    private String description;
    private String level;
    private Long allTimeEnrollments = 0L;
    private Long trendingEnrollments = 0L;

}

