package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findById(long aLong);

    @Query("select t from Project  t where t.name like %:name% ")
    List<Project> getProjectsByName(String name);
}
