package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.ProjectForm;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyService companyService;

    public ProjectService(ProjectRepository projectRepository, CompanyService companyService) {
        this.projectRepository = projectRepository;
        this.companyService = companyService;
    }

    public Optional<Project> getProjectById(long id){
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsByName(String name){
        return projectRepository.getProjectsByName(name);
    }



    public ResponseEntity<ResponseForm> deleteProject(long id){
        ResponseForm form=new ResponseForm();
        if (getProjectById(id).isPresent()){
            projectRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            return ResponseEntity.ok(form);
        }
        form.setMessage("This Company does not exists");
        form.setStatus(409);
        return ResponseEntity.status(409).body(form);
    }



    public ProjectForm saveUtils(ProjectForm form, Project project){
        if (form==null){
            return null;
        }
        Optional<Company> optionalCompany=companyService.getCompanyById(form.getCompany_id());
        if (optionalCompany.isEmpty()){
            return null;
        }
        project.setName(form.getName());
        project.setShortName(form.getShortName());
        project.setCompany(optionalCompany.get());
        projectRepository.save(project);
        return form;
    }

    public ProjectForm saveProject(ProjectForm form){
        Project project=new Project();
        return saveUtils(form,project);
    }

    public ProjectForm updateProject(ProjectForm form,Project project){
        return saveUtils(form,project);
    }



}
