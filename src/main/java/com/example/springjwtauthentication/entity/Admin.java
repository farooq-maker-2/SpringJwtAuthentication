package com.example.springjwtauthentication.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.persistence.*;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Table(name = "admins")
@Data
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column
    @Schema(example = "Muhammad")
    private String firstName;

    @Column
    @Schema(example = "Farooq")
    private String lastName;

    @Column
    @Schema(example = "farooq.maker@gmail.com")
    private String email;

    @Column
    @Schema(example = "password")
    private String password;

    @Column
    @Schema(example = "activated")
    private String status = "activated";

    @Column
    @Schema(example = "admin")
    private final String role = "admin";

}
