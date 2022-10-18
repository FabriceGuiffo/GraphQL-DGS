package com.urgent2k.employeeDRH.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    //we will store the roles directly in the user and not in a separate table
    @Column(name="role", nullable = false)
    private String role;

    //adding a parameter that I want to experiment on to activate or deactivate a user account
    @Column(name="enabled", columnDefinition = "boolean not null default true")
    private Boolean enabled=true;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
