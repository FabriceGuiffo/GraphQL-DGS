package com.urgent2k.employeeDRH.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name",nullable = false)
    @Size(min=4,max=50)
    private String name;
    @Column(name="ceoname")
    private String ceoname;
    @Column(name="contact")
    private int contact;
    @Column(name="location", nullable = false)
    @Size(max=25,message = "un maximum de 25 caracteres")
    private String location;
}
