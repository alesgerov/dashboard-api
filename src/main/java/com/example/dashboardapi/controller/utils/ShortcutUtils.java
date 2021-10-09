package com.example.dashboardapi.controller.utils;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.form.UtilForm;
import com.example.dashboardapi.service.CompanyService;
import com.example.dashboardapi.service.ProjectService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShortcutUtils {

    private final CompanyService companyRepository;
    private final ProjectService projectRepository;

    public ShortcutUtils(CompanyService companyRepository, ProjectService projectRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }


    public static boolean isLogged(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public UtilForm getOptionals(long company, long project){
        UtilForm optionalForm=new UtilForm();
        Optional<Company> optionalCompany=companyRepository.getCompanyById(company);
        Optional<Project> optionalProject=projectRepository.getProjectById(project);
        if (optionalCompany.isPresent() && optionalProject.isPresent()){
            optionalForm.setCompany(optionalCompany.get());
            optionalForm.setProject(optionalProject.get());
            return optionalForm;
        }
        return null;
    }

    public ResponseForm getErrorForm(String message,int status){
        return new ResponseForm(message,status,null);
    }

    public ResponseForm getErrorForm(String message,int status,Object content){
        return new ResponseForm(message,status,content);
    }

    public ResponseForm successForm(Object content){
        return new ResponseForm("Success",200,content);
    }

    public ResponseForm createdForm(Object content){
        return new ResponseForm("Created",201,content);
    }

}
