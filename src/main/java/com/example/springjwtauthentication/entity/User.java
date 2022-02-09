package com.example.springjwtauthentication.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 *@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 *
 * Entity inheritance means that we can use polymorphic
 * queries for retrieving all the subclass entities when
 * querying for a superclass.Since Hibernate is a
 * JPA implementation, it contains all of the above as well
 * as a few Hibernate-specific features related to inheritance.
 *
 * */
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(example = "muhammad")
    private String firstName;

    @Column(nullable = false)
    @Schema(example = "farooq")
    private String lastName;

    @Column(nullable = false)
    @Schema(example = "farooq.student@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(example = "password")
    private String password;

    @Column
    @Schema(example = "activated")
    private String status = "activated";

    @Column(nullable = false)
    @Schema(example = "student")
    private String role;

}
