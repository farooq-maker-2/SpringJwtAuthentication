package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "enrollments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@ApiModelProperty(notes = "this field represents enrollment id, and it is auto generated", name = "id", required = true, example = "1")
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    //@ApiModelProperty(notes = "this field represents enrollment date, and it is auto generated", name = "enrollmentDate", required = true, example = "1")
    @Schema(example = "23-09-1997")
    private Date enrollmentDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id"/*, nullable=false*/)
    private Course course;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="student_id"/*, nullable=false*/)
//    private Student student;
}
