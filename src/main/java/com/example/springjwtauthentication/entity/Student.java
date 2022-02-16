package com.example.springjwtauthentication.entity;

import lombok.Data;
import java.util.Set;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
@Data
public class Student extends User {

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="student_course"/*,joinColumns = @JoinColumn(name = "date", value = "14-02-2022")*/)
    private Set<Course> courses;

}
