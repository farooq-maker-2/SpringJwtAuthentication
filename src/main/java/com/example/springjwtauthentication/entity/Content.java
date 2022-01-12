package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "contents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    //@ApiModelProperty(notes = "this field represents content id, and it is auto generated", name = "id", example = "1")
    private Long id;

    @Column(name = "description")
    //@ApiModelProperty(notes = "this field represents content's description", name = "description", example = "description")
    @Schema(example = "description")
    private String description;

    @Column(name = "filename")
    //@ApiModelProperty(notes = "this field represents content's file name", name = "fileName", example = "content.pdf")
    @Schema(example = "content.pdf")
    private String fileName;

    @Column(name = "content_type")
    //@ApiModelProperty(notes = "this field represents content's type", name = "contentType", example = "pdf")
    @Schema(example = "text/plain")
    private String contentType;

//    @Column(name="created")
//    private Date created;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_id"/*, nullable=false*/)
    //@ApiModelProperty(readOnly = true)
    private Course course;

}
