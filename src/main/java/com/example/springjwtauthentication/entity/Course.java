package com.example.springjwtauthentication.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "courses")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(example = "database")
    private String courseName;

    @Column(nullable = false)
    @Schema(example = "description")
    private String description;

    @Column(nullable = false)
    @Schema(example = "500")
    private String level;

    @Column
    @Schema(example = "0")
    private Long allTimeEnrollments = 0L;

    @Column
    @Schema(example = "0")
    private Long trendingEnrollments = 0L;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "course_content_id")
    private List<Content> courseContents;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "enrollment_id")
    private List<Enrollment> enrollments;

}

