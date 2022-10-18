package com.urgent2k.employeeDRH.Fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.urgent2k.employeeDRH.DAL.CompanyRepository;
import com.urgent2k.employeeDRH.DAL.EmployeeRepository;
import com.urgent2k.employeeDRH.Exception.CompanyNotFoundException;
import com.urgent2k.employeeDRH.Exception.EmployeeNotFoundException;
import com.urgent2k.employeeDRH.Model.Company;
import com.urgent2k.employeeDRH.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Optional;

@DgsComponent
public class Mutation {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @DgsMutation
    @Secured("ROLE_ADMIN")
    public Employee createEmployee(@InputArgument String name, @InputArgument String christianname, @InputArgument String department, @InputArgument int telephone, @InputArgument Integer company_id) throws CompanyNotFoundException{
        Employee employee=new Employee();
        employee.setName(name);
        employee.setChristianname(christianname);
        employee.setDepartment(department);
        employee.setTelephone(telephone);
        //on va verifier s'il existe effectivement une company avec l'identifiant definit
        //on veut s'assurer que l'entreprise existe avant d'y ajouter des employees si ce
        //n'est pas le cas on leve une exception
        Optional<Company> fcompany=companyRepository.findById(company_id);
        if(fcompany.isPresent()){
            //employeeRepository.save(employee);
            employee.setCompany(fcompany.get());
            //vue que l'entreprise existe on peut maintenant persister tout cela et retourner l'employee(avec ID)
            employeeRepository.save(employee);
            return employee;
        }
        else{
            throw new CompanyNotFoundException("There is no company with the provided Id to add the client to");
        }
    }

    @DgsMutation
    @Secured("ROLE_ADMIN")
    public Employee updateEmployee(@InputArgument Integer id, @InputArgument String name, @InputArgument String christianname, @InputArgument String department, @InputArgument int telephone) throws EmployeeNotFoundException {
        //we start by retrieving the corresponding elem in the BDD
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
        if(employeeOptional.isPresent()){
            Employee employee=employeeOptional.get();
            //on ne mettra a jour que les champs non-null
            if(name!=null){
                employee.setName(name);
            }
            if(christianname!=null){
                employee.setChristianname(christianname);
            }
            if(department!=null){
                employee.setDepartment(department);
            }
            if(telephone!=0){
                employee.setTelephone(telephone);
            }
            return employeeRepository.save(employee);
        }
        throw new EmployeeNotFoundException("We couldn't find any employee with the provided ID");
    }

    @DgsMutation
    @Secured("ROLE_ADMIN")
    public Boolean deleteEmployee(@InputArgument Integer id){
        employeeRepository.deleteById(id);
        return true;
    }

    @DgsMutation
    @Secured({"ROLE_VIEWER","ROLE_ADMIN"})
    public Company createCompany(@InputArgument String name, @InputArgument String ceoname, @InputArgument int contact, @InputArgument String location){
        Company company= new Company();
        company.setName(name);
        company.setCeoname(ceoname);
        company.setContact(contact);
        company.setLocation(location);

        return companyRepository.save(company);
    }

    @DgsMutation
    @Secured("ROLE_ADMIN")
    public Company updateCompany(@InputArgument Integer id, @InputArgument String name, @InputArgument String ceoname, @InputArgument int contact, @InputArgument String location){
        Optional<Company> fcompany=companyRepository.findById(id);

        if(fcompany.isPresent()){
            Company company=fcompany.get();

            if(name!=null){
                company.setName(name);
            }
            if(ceoname!=null){
                company.setCeoname(ceoname);
            }
            if(contact!=0){
                company.setContact(contact);
            }
            if(location!=null){
                company.setLocation(location);
            }

            return companyRepository.save(company);
        }
        throw new CompanyNotFoundException("there was no company with the provided id");
    }

    @DgsMutation
    @Secured("ROLE_ADMIN")
    public Boolean deleteCompany(@InputArgument Integer id){
        companyRepository.deleteById(id);
        return true;
    }

}
