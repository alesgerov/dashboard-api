package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.form.CompanyForm;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.repository.CompanyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public ResponseEntity<ResponseForm> deleteCompany(long id){
        ResponseForm form=new ResponseForm("Deleted",200);
        if (getCompanyById(id).isPresent()){
            companyRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            return ResponseEntity.ok(form);
        }
        form.setMessage("This Company does not exists");
        form.setStatus(409);
        return ResponseEntity.status(409).body(form);
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }


    public CompanyForm saveUtils(CompanyForm form,Company company){
        if (form==null){
            return null;
        }
        company.setEmail(form.getEmail());
        company.setLogo(form.getLogo());
        company.setName(form.getName());
        company.setShortName(form.getShortName());
        company.setNoteText(form.getNoteText());
        company=companyRepository.save(company);
        return form;
    }


    public CompanyForm saveCompany(CompanyForm form){
        Company company=new Company();
        return saveUtils(form,company);
    }

    public CompanyForm updateCompany(CompanyForm form,Company company){
        return saveUtils(form,company);
    }


    public List<Company> getAllCompaniesByName(String name) {
        return companyRepository.getCompaniesByName(name);
    }
}
