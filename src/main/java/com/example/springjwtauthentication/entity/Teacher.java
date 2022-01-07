package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents Teacher's first name", name = "firstName", required = true, example = "Muhammad")
    private String firstName;

    @Column
    @ApiModelProperty(notes = "this field represents Teacher's last name", name = "lastName", required = true, example = "Farooq")
    private String lastName;

    @Column
    @ApiModelProperty(notes = "this field represents Teacher's email", name = "email", required = true, example = "farooq.teacher@gmail.com")
    private String email;

    @Column
    @ApiModelProperty(notes = "this field represents Teacher's password", name = "password", required = true, example = "password")
    private String password;

    @Column
    @ApiModelProperty(notes = "this field represents Teacher's status", name = "status", required = true, example = "activated")
    private String status = "activated";

    //    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();
    @Column
    @ApiModelProperty(notes = "this field represents Teacher's role", name = "role", required = true, example = "teacher")
    private final String role = "teacher";

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
