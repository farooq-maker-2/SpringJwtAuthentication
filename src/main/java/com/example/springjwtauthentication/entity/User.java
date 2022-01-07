package com.example.springjwtauthentication.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ApiModel("Details of user")
/**
 *@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 *
 * Entity inheritance means that we can use polymorphic
 * queries for retrieving all the subclass entities when
 * querying for a superclass.Since Hibernate is a
 * JPA implementation, it contains all of the above as well
 * as a few Hibernate-specific features related to inheritance.
 *
 * */

public class User {

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
