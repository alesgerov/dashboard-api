package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findById(Long aLong);

    Optional<Company> findByName(String name);

    @Query("select t from Company  t where t.name like %:name% ")
    List<Company> getCompaniesByName(String name);

}
