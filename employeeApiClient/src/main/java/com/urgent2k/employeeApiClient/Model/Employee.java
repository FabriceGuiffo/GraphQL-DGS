package com.urgent2k.employeeApiClient.Model;

import lombok.Data;

@Data
public class Employee {

    private Integer id;
    private String name;
    private String christianname;
    private String department;
    private int telephone;
    private Company company;
}
