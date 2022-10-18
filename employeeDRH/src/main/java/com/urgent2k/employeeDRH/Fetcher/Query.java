package com.urgent2k.employeeDRH.Fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.urgent2k.employeeDRH.DAL.CompanyRepository;
import com.urgent2k.employeeDRH.DAL.EmployeeRepository;
import com.urgent2k.employeeDRH.Exception.CompanyNotFoundException;
import com.urgent2k.employeeDRH.Exception.EmployeeNotFoundException;
import com.urgent2k.employeeDRH.Model.Company;
import com.urgent2k.employeeDRH.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class Query {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @DgsQuery
    @Secured({"ROLE_VIEWER","ROLE_ADMIN"})
    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    @DgsQuery
    @Secured({"ROLE_VIEWER","ROLE_ADMIN"})
    public Optional<Employee> findParticularEmployee(Integer id) throws EmployeeNotFoundException{
        Optional<Employee> emp=employeeRepository.findById(id);
        if (emp.isPresent()){
            return emp;
        }
        //else the optional is empty
        throw new EmployeeNotFoundException("the demanded employee wasn't found");
    }

    @DgsQuery
    @Secured("ROLE_ADMIN")
    public int countEmployees(){
        return (int)employeeRepository.count();
    }

    @DgsQuery
    @Secured({"ROLE_VIEWER","ROLE_ADMIN"})
    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    @DgsQuery
    @Secured("ROLE_ADMIN")
    public Optional<Company> findCompany(Integer id) throws  CompanyNotFoundException{
        Optional<Company> cmp=companyRepository.findById(id);
        if (cmp.isPresent()){
            return cmp;
        }
        throw new CompanyNotFoundException("the company demanded is inexistent");
    }

    @DgsQuery
    @Secured({"ROLE_VIEWER","ROLE_ADMIN"})
    public int countCompany(){
        return (int)companyRepository.count();
    }

}
