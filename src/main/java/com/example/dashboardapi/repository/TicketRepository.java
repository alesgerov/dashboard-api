package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.CountForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query( "select count(t.id) from Ticket t  where t.company=:company" )
    int getCountOfAllTicketsByCompany(@Param("company") Company company);

    @Query( "select count(t.id) from Ticket t  where t.project=:project" )
    int getCountOfAllTicketsByProject(@Param("project") Project project);

    @Query( "select count(t.id) from Ticket t  where t.project=:project and t.company=:company" )
    int getCountOfAllTicketsByProjectAndCompany(@Param("project") Project project,
                                                  @Param("company") Company company);


    @Query( "select count(t.id) from Ticket t  where t.project=:project and t.company=:company and" +
            " t.status=:status" )
    int getCountOfAllTicketsByStatus(@Param("project") Project project,
                                     @Param("company") Company company,
                                     @Param("status") int status);

    @Query( "select count(t.id) from Ticket t  where t.project=:project  and" +
            " t.status=:status" )
    int getCountOfAllTicketsByStatusByProject(@Param("project") Project project,
                                     @Param("status") int status);


    @Query( "select count(t.id) from Ticket t  where t.company=:company  and" +
            " t.status=:status" )
    int getCountOfAllTicketsByStatusByCompany(@Param("company") Company company,
                                     @Param("status") int status);

}
