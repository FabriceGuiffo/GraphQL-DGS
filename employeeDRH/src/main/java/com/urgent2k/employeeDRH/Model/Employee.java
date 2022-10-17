package com.urgent2k.employeeDRH.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", nullable = false)
    @Size(min=5,max=25,message = "name has between 5-25 characters")
    private String name;
    @Column(name="christianname")
    private String christianname;
    @Column(name="department")
    private String department;
    @Column(name="telephone")
    private int telephone;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="company_id", nullable = false, updatable = false)
    private Company company;
}
