package com.example.springjwtauthentication.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents admin id, and it is auto generated", name = "id", required = true, example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents admin's first name", name = "firstName", required = true, example = "Muhammad")
    private String firstName;

    @Column
    @ApiModelProperty(notes = "this field represents admin's last name", name = "email", required = true, example = "Farooq")
    private String lastName;

    @Column
    @ApiModelProperty(notes = "this field represents admin's email", name = "email", required = true, example = "farooq.maker@gmail.com")
    private String email;

    @Column
    @ApiModelProperty(notes = "this field represents admin's password", name = "password", required = true, example = "password")
    private String password;

    @Column
    @ApiModelProperty(notes = "this field represents admin's status", name = "status", required = true, example = "activated")
    private String status = "activated";

    @Column
    private final String role = "admin";

}
