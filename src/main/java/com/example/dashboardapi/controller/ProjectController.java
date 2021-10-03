package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.ProjectForm;
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
    public ResponseEntity<List<Project>> getProjects(){
        return ResponseEntity.ok().body(projectService.getAllProjects());
    }


    @GetMapping(value = {"/projects/{id}","/projects/{id}/"})
    public ResponseEntity<?> getProjectById(@PathVariable("id") long id){
        Optional<Project> optionalProject=projectService.getProjectById(id);
        if(optionalProject.isPresent()){
            return ResponseEntity.ok().body(optionalProject.get());
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found",409));
    }


    @PostMapping(value = {"/add/project","/add/project/"})
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectForm form, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }
        ProjectForm projectForm=projectService.saveProject(form);
        if (projectForm==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(201).body(projectForm);

    }

    @DeleteMapping(value = {"/delete/project/{id}","/delete/project/{id}/"})
    public ResponseEntity<?> deleteProject(@PathVariable("id") long id){
        return projectService.deleteProject(id);
    }


    @GetMapping(value = {"/projects"})
    public ResponseEntity<?> filterProject(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(projectService.getAllProjectsByName(name));
    }



    @PutMapping(value = {"/update/project/{id}","/update/project/{id}/"})
    public ResponseEntity<?> updateProject(@PathVariable("id") long id,
                                           @Valid @RequestBody ProjectForm form, BindingResult result){
        Optional<Project> optionalProject=projectService.getProjectById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }else  if (optionalProject.isEmpty()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This project does not exists.",409));
        }
        ProjectForm updated=projectService.updateProject(form,optionalProject.get());
        if (updated==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(200).body(updated);
    }



}
