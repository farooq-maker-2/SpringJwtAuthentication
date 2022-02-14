package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
public class Student extends User {

//    @JsonIgnore
//    @OneToMany(mappedBy = "student")
//    private List<Enrollment> enrollments;

    @JsonIgnore
    @ManyToMany//(mappedBy = "student")
    @JoinTable(name="Student_Course"/*,joinColumns = @JoinColumn(name = "date", value = "14-02-2022")*/)
    private Set<Course> courses;

}
