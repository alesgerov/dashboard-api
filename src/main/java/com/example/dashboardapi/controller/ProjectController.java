package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.ProjectFormName;
import com.example.dashboardapi.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController extends ApiControllerV1 {

    private final ProjectService projectService;
    private final ShortcutUtils shortcutUtils;

    public ProjectController(ProjectService projectService, ShortcutUtils shortcutUtils) {
        this.projectService = projectService;
        this.shortcutUtils = shortcutUtils;
    }

    @GetMapping(value = {"/projects/"})
    public ResponseEntity<?> getProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectFormName> formNames = projectService.mapperToProjectFormName(projects);
        return ResponseEntity.ok().body(shortcutUtils.successForm(formNames));
    }


    @GetMapping(value = {"/projects/{id}", "/projects/{id}/"})
    public ResponseEntity<?> getProjectById(@PathVariable("id") long id) {
        Optional<Project> optionalProject = projectService.getProjectById(id);
        if (optionalProject.isPresent()) {
            return ResponseEntity.ok().body(shortcutUtils.successForm(projectService.getProjectForm(optionalProject.get())));
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This project does not found", 409, "This project does not found"));
    }


    @PostMapping(value = {"/add/project", "/add/project/"})
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectFormName form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        }
        ProjectFormName projectForm = projectService.saveProject(form);
        if (projectForm == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(201).body(shortcutUtils.createdForm(projectForm));

    }

    @DeleteMapping(value = {"/delete/project/{id}", "/delete/project/{id}/"})
    public ResponseEntity<?> deleteProject(@PathVariable("id") long id) {
        return projectService.deleteProject(id);
    }


    @GetMapping(value = {"/projects"})
    public ResponseEntity<?> filterProject(@RequestParam(required = false, name = "name") String name) {
        List<Project> projects = projectService.getAllProjectsByName(name);
        List<ProjectFormName> formNames = projectService.mapperToProjectFormName(projects);
        return ResponseEntity.ok().body(shortcutUtils.successForm(formNames));
    }


    @PutMapping(value = {"/update/project/{id}", "/update/project/{id}/"})
    public ResponseEntity<?> updateProject(@PathVariable("id") long id,
                                           @Valid @RequestBody ProjectFormName form, BindingResult result) {
        Optional<Project> optionalProject = projectService.getProjectById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        } else if (optionalProject.isEmpty()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This project does not exists.", 409, form));
        }
        ProjectFormName updated = projectService.updateProject(form, optionalProject.get());
        if (updated == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(200).body(shortcutUtils.successForm(updated));
    }

}
