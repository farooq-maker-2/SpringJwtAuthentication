package com.example.springjwtauthentication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String status = "activated";

    @Column(nullable = false)
    private String role;

}
