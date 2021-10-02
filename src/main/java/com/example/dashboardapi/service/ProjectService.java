package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> getProjectById(long id){
        return projectRepository.findById(id);
    }
}
