package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File,Long> {
    Optional<File> findById(Long id);

    @Query("select t from File  t where t.name like %:name% ")
    List<File> getFilesByName(String name);

    Optional<File> findFileByName(String name);

    List<File> findFilesByType(int type);

    List<File> findFilesByTicket(Ticket ticket);

}
