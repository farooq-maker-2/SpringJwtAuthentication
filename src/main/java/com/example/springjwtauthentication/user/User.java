package com.example.springjwtauthentication.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ApiModel("Details of user")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public /*abstract*/ class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents user id, and it is auto generated", name = "id", example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents user's first name", name = "firstName", required = true, example = "Muhammad")
    private String firstName;

    @Column
    @ApiModelProperty(notes = "this field represents user's last name", name = "lastName", required = true, example = "Farooq")
    private String lastName;

    @Column
    @ApiModelProperty(notes = "this field represents user's email", name = "email", required = true, example = "farooq.user@gmail.com")
    private String email;

    @Column
    @ApiModelProperty(notes = "this field represents user's password", name = "password", required = true, example = "password")
    private String password;

    @Column
    @ApiModelProperty(notes = "this field represents user's status", name = "status", example = "activated")
    private String status = "activated";

    @Column(nullable = false)
    @ApiModelProperty(notes = "this field represents user's role", name = "role", required = true, example = "student")
    private String role;

}
