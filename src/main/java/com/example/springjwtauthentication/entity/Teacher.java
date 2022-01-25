package com.example.springjwtauthentication.entity;

import com.example.springjwtauthentication.model.TeacherModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "teachers")
@Data
@Builder
@NoArgsConstructor
//@Setter
//@Getter
@AllArgsConstructor
//@ToString
public class Teacher{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents Teacher's first name", name = "firstName", required = true, example = "Muhammad")
    @Schema(example = "Muhammad")
    private String firstName;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents Teacher's last name", name = "lastName", required = true, example = "Farooq")
    @Schema(example = "Farooq")
    private String lastName;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents Teacher's email", name = "email", required = true, example = "farooq.teacher@gmail.com")
    @Schema(example = "farooq.teacher@gmail.com")
    private String email;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents Teacher's password", name = "password", required = true, example = "password")
    @Schema(example = "password")
    private String password;

    @Column
    //@ApiModelProperty(notes = "this field represents Teacher's status", name = "status", required = true, example = "activated")
    @Schema(example = "activated")
    private String status = "activated";

    //    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();
    @Column
    //@ApiModelProperty(notes = "this field represents Teacher's role", name = "role", required = true, example = "teacher")
    @Schema(example = "teacher")
    private final String role = "teacher";

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
