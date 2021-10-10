package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(long aLong);


    @Query("select t from Employee  t where t.name like %:name% or t.surname like %:name%")
    List<Employee> getEmployeesByName(String name);
}
