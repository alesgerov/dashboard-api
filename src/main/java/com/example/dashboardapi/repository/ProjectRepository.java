package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findById(long aLong);
}
