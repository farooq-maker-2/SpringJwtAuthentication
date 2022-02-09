package com.example.springjwtauthentication.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@Data
public class Admin extends User {
}
