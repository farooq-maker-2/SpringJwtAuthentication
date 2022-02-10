package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
@Data
public class Teacher extends User {

    @JsonIgnore
    //(mappedBy = "teacher") ==> this will populate courses when we call teacher.getCourses();
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
