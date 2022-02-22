package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String status = "activated";

    @Column(nullable = false)
    //private Set<String> roles;
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> teacherCourses;

    @JsonIgnore
    @ManyToMany/*(mappedBy = "student")*/
    @JoinTable(name = "user_course", joinColumns = {
            @JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id")
            })
    @JoinColumn(insertable = false, name = "created_at",
            columnDefinition = "DATE DEFAULT CURRENT_DATE default '22-02-2022'")
    private Set<Course> studentCourses;

}
