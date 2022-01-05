package com.example.springjwtauthentication.student;

import com.example.springjwtauthentication.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "student")
@ApiModel("Details of student")
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents student id, and it is auto generated", name = "id", example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents student's first name", name = "firstName", required = true, example = "Muhammad")
    private String firstName;

    @Column
    @ApiModelProperty(notes = "this field represents student's last name", name = "email", required = true, example = "Farooq")
    private String lastName;

    @Column
    @ApiModelProperty(notes = "this field represents student's email", name = "email", required = true, example = "farooq.maker@gmail.com")
    private String email;

    @Column
    @ApiModelProperty(notes = "this field represents student's password", name = "password", required = true, example = "password")
    private String password;

    @Column
    @ApiModelProperty(notes = "this field represents student's status", name = "status", required = true, example = "activated")
    private String status = "activated";

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();
    @Column
    @ApiModelProperty(notes = "this field represents student's role", name = "role", required = true, example = "student")
    private final String role = "student";

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ApiModelProperty(readOnly = true)
    private Set<Course> courses;

//    //@JsonIgnore
//    @OneToMany
//    //@JoinColumn(name = "enrollment_id"/*,nullable=false*/)
//    private List<Enrollment> enrollments;

}
