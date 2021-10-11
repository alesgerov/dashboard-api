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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShortcutUtils {

    private final CompanyService companyRepository;
    private final ProjectService projectRepository;

    public ShortcutUtils(CompanyService companyRepository, ProjectService projectRepository) {
        this.companyRepository = companyRepository;
        this.projectRepository = projectRepository;
    }


    public static boolean isLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public UtilForm getOptionals(long company, long project) {
        UtilForm optionalForm = new UtilForm();
        Optional<Company> optionalCompany = companyRepository.getCompanyById(company);
        Optional<Project> optionalProject = projectRepository.getProjectById(project);
        if (optionalCompany.isPresent() && optionalProject.isPresent()) {
            optionalForm.setCompany(optionalCompany.get());
            optionalForm.setProject(optionalProject.get());
            return optionalForm;
        }
        return null;
    }

    public UtilForm getOptionals(String companyName, String projectName) {
        UtilForm utilForm = new UtilForm();
        Optional<Company> optionalCompany = companyRepository.getCompanyByName(companyName);
        Optional<Project> optionalProject = projectRepository.getProjectName(projectName);
        if (optionalCompany.isPresent() && optionalProject.isPresent()) {
            utilForm.setProject(optionalProject.get());
            utilForm.setCompany(optionalCompany.get());
            return utilForm;
        }
        return null;
    }

    public ResponseForm getErrorForm(String message, int status) {
        return new ResponseForm(message, status, null);
    }

    public ResponseForm getErrorForm(String message, int status, Object content) {
        return new ResponseForm(message, status, content);
    }

    public ResponseForm successForm(Object content) {
        return new ResponseForm("Success", 200, content);
    }

    public ResponseForm createdForm(Object content) {
        return new ResponseForm("Created", 201, content);
    }

    public List<ObjectError> reNewErrors(BindingResult result, String fieldName) {
        List<FieldError> errors = result.getFieldErrors();
        List<FieldError> resultList = new ArrayList<>();
        for (int i = 0; i < errors.size(); i++) {
            if (!errors.get(i).getField().equals(fieldName)) {
                resultList.add(errors.get(i));
            }
        }

        List<ObjectError> objectErrors = new ArrayList<>(resultList);
//        for (FieldError fieldError : errors) {
//            result.addError(fieldError);
//        }
        return objectErrors;
    }
}
