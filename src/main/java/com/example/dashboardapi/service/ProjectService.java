package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.ProjectFormName;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Optional<Project> getProjectById(long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsByName(String name) {
        return projectRepository.getProjectsByName(name);
    }


    public ResponseEntity<ResponseForm> deleteProject(long id) {
        ResponseForm form = new ResponseForm();
        if (getProjectById(id).isPresent()) {
            projectRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            form.setContent("Deleted");
            return ResponseEntity.ok(form);
        }
        form.setMessage("This Company does not exists");
        form.setStatus(409);
        form.setContent("This Company does not exists");
        return ResponseEntity.status(409).body(form);
    }


    public ProjectFormName saveUtils(ProjectFormName form, Project project) {
        if (form == null) {
            return null;
        }
        Optional<Company> optionalCompany = companyService.getCompanyByName(form.getCompany());
        if (optionalCompany.isEmpty()) {
            return null;
        }
        project.setName(form.getName());
        project.setShortName(form.getShortName());
        project.setCompany(optionalCompany.get());
        projectRepository.save(project);
        return form;
    }

    public ProjectFormName saveProject(ProjectFormName form) {
        Project project = new Project();
        return saveUtils(form, project);
    }

    public ProjectFormName updateProject(ProjectFormName form, Project project) {
        return saveUtils(form, project);
    }

    public Optional<Project> getProjectName(String name) {
        return projectRepository.findByName(name);
    }


    public ProjectFormName getProjectForm(Project project) {
        ProjectFormName form = new ProjectFormName();
        form.setCompany(project.getCompany().getName());
        form.setShortName(project.getShortName());
        form.setName(project.getName());
        return form;
    }

    public List<ProjectFormName> mapperToProjectFormName(List<Project> projects) {
        List<ProjectFormName> formNames = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            formNames.add(getProjectForm(projects.get(i)));
        }
        return formNames;
    }
}
