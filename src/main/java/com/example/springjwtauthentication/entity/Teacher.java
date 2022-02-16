package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teachers")
@Data
public class Teacher extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
