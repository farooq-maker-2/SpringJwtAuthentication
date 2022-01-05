package com.example.springjwtauthentication.content;

import com.example.springjwtauthentication.course.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "this field represents content id, and it is auto generated", name = "id", example = "1")
    private Long id;

    @Column(name = "description")
    @ApiModelProperty(notes = "this field represents content's description", name = "description", example = "description")
    private String description;

    @Column(name = "filename")
    @ApiModelProperty(notes = "this field represents content's file name", name = "fileName", example = "content.pdf")
    private String fileName;

    @Column(name = "content")
    @Lob
    @ApiModelProperty(notes = "this field represents content", name = "content", required = true, example = "content.pdf")
    private byte[] content;

    @Column(name = "content_type")
    @ApiModelProperty(notes = "this field represents content's type", name = "contentType", example = "pdf")
    private String contentType;

//    @Column(name="created")
//    private Date created;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id"/*, nullable=false*/)
    @ApiModelProperty(readOnly = true)
    private Course course;

}
