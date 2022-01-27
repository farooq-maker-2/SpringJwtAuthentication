package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("Details of student")
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@ApiModelProperty(notes = "this field represents student id, and it is auto generated", name = "id", example = "1")
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents student's first name", name = "firstName", required = true, example = "Muhammad")
    @Schema(example = "Muhammad")
    private String firstName;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents student's last name", name = "email", required = true, example = "Farooq")
    @Schema(example = "Farooq")
    private String lastName;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents student's email", name = "email", required = true, example = "farooq.maker@gmail.com")
    @Schema(example = "farooq.maker@gmail.com")
    private String email;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents student's password", name = "password", required = true, example = "password")
    @Schema(example = "password")
    private String password;

    @Column
    //@ApiModelProperty(notes = "this field represents student's status", name = "status", required = true, example = "activated")
    @Schema(example = "activated")
    private String status = "activated";

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();
    @Column
    //@ApiModelProperty(notes = "this field represents student's role", name = "role", required = true, example = "student")
    @Schema(example = "student")
    private final String role = "student";

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    //@ApiModelProperty(readOnly = true)
    private Set<Course> courses;

//    //@JsonIgnore
//    @OneToMany
//    //@JoinColumn(name = "enrollment_id"/*,nullable=false*/)
//    private List<Enrollment> enrollments;

}
