package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
public class Student extends User {

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="Student_Course"/*,joinColumns = @JoinColumn(name = "date", value = "14-02-2022")*/)
    private Set<Course> courses;

}
