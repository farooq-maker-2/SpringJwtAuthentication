package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Data
public class Student extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

}
