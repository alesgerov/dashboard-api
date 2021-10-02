package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<Company> getCompanyById(long id){
        return companyRepository.findById(id);
    }
}
