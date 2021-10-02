package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findById(Long aLong);
}
