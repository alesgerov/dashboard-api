package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query( "select count(t.id) from Ticket t  where t.company=:company" )
    int getCountOfAllTicketsByCompany(@Param("company") Company company);

    @Query( "select count(t.id) from Ticket t  where t.company=:project" )
    int getCountOfAllTicketsByProject(@Param("project") Project project);

}
