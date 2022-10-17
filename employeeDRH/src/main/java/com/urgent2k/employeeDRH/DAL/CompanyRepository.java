package com.urgent2k.employeeDRH.DAL;

import com.urgent2k.employeeDRH.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
