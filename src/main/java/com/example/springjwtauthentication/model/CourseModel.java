
package com.example.springjwtauthentication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.Set;

//@Data
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
//    @JsonIgnore
//    private Set<StudentModel> students;
//    @JsonIgnore
//    private TeacherModel teacher;
//    @JsonIgnore
//    private List<ContentModel> courseContents;
//    @JsonIgnore
//    private List<EnrollmentModel> enrollments;

}

