package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    Optional<Ticket> findById(Long aLong);


    @Query("select t from Ticket t  where t.company=:company ")
    List<Ticket> findAllByCompany(@Param("company") Company company);

    @Query("select t from Ticket t  where t.project=:project")
    List<Ticket> findAllByProject(@Param("project") Project project);

    @Query("select t from Ticket t  where t.project=:project and t.company=:company ")
    List<Ticket> findAll(@Param("project") Project project,
                                  @Param("company") Company company);


    @Query("select t from Ticket  t where t.title like %:name% ")
    List<Ticket> findByTitle(@Param("name")String name);

    List<Ticket> findAllByStatus(int status);
}
