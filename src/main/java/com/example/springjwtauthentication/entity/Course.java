
package com.example.springjwtauthentication.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "courses")
//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@ApiModelProperty(notes = "this field represents course id, and it is auto generated", name = "id", example = "1")
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column
    //@ApiModelProperty(notes = "this field represents course name", name = "courseName", required = true, example = "database")
    @Schema(example = "database")
    private String courseName;

    @Column
    //@ApiModelProperty(notes = "this field represents course description", name = "courseName", required = true, example = "database")
    @Schema(example = "database")
    private String description;

    @Column
    //@ApiModelProperty(notes = "this field represents course difficulty level", name = "level", required = true, example = "500")
    @Schema(example = "500")
    private String level;

    @Column
    //@ApiModelProperty(notes = "this field represents enrollments count of a course", name = "allTimeEnrollments", example = "0")
    @Schema(example = "0")
    private Long allTimeEnrollments = 0L;

    @Column
    //@ApiModelProperty(notes = "this field represents trendingEnrollments count of a course", name = "allTimeEnrollments", example = "0")
    @Schema(example = "0")
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
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_content_id"/*,nullable=false*/)
    //@ApiModelProperty(readOnly = true)
    private List<Content> courseContents;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "enrollment_id"/*,nullable=false*/)
    private List<Enrollment> enrollments;

}

