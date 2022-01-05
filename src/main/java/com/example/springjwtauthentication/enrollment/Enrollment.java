package com.example.springjwtauthentication.enrollment;

import com.example.springjwtauthentication.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents enrollment id, and it is auto generated", name = "id", required = true, example = "1")
    private Long id;

    @Column
    @ApiModelProperty(notes = "this field represents enrollment date, and it is auto generated", name = "enrollmentDate", required = true, example = "1")
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
