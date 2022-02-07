package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "teachers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Muhammad")
    private String firstName;

    @Column(nullable = false)
    @Schema(example = "Farooq")
    private String lastName;

    @Column(nullable = false)
    @Schema(example = "farooq.teacher@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(example = "password")
    private String password;

    @Column
    @Schema(example = "activated")
    private String status = "activated";

    @Column
    @Schema(example = "teacher")
    private final String role = "teacher";

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses;

}
