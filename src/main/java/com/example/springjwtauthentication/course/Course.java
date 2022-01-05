
package com.example.springjwtauthentication.course;


import com.example.springjwtauthentication.content.Content;
import com.example.springjwtauthentication.enrollment.Enrollment;
import com.example.springjwtauthentication.student.Student;
import com.example.springjwtauthentication.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "course")
//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents course id, and it is auto generated", name = "id", example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents course name", name = "courseName", required = true, example = "database")
    private String courseName;

    @Column
    @ApiModelProperty(notes = "this field represents course description", name = "courseName", required = true, example = "database")
    private String description;

    @Column
    @ApiModelProperty(notes = "this field represents course difficulty level", name = "level", required = true, example = "500")
    private String level;

    @Column
    @ApiModelProperty(notes = "this field represents enrollments count of a course", name = "allTimeEnrollments", example = "0")
    private Long allTimeEnrollments = 0L;

    @Column
    @ApiModelProperty(notes = "this field represents trendingEnrollments count of a course", name = "allTimeEnrollments", example = "0")
    private Long trendingEnrollments = 0L;

//    @Column
//    @ApiModelProperty(notes = "this field represents trendingEnrollments count of a course", name = "allTimeEnrollments", example = "0")
//    private Blob content;

//    @Column
//    private Long rating;
//
//    @Column
//    private Long trendRating;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id"/*, nullable=false*/)
    private Teacher teacher;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "course_content_id"/*,nullable=false*/)
    @ApiModelProperty(readOnly = true)
    private List<Content> courseContents;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "enrollment_id"/*,nullable=false*/)
    private List<Enrollment> enrollments;

}

