package com.urgent2k.employeeDRH.Fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.urgent2k.employeeDRH.DAL.CompanyRepository;
import com.urgent2k.employeeDRH.Model.Company;
import com.urgent2k.employeeDRH.Model.Employee;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class CompanyDataFetcher {
    @Autowired
    CompanyRepository companyRepository;

    @DgsData(parentType = "Employee", field = "company")
    public Company company(DataFetchingEnvironment dfe){
        Employee emp=dfe.getSource();
        return companyRepository.findById(emp.getCompany().getId()).orElseThrow(null);
    }
}
