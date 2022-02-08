package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "teachers")
@Data
public class Teacher extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
