package com.example.dashboardapi.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_id_seq",
            sequenceName = "employee_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_id_seq"
    )
    private long id;
    @OneToOne
    private Company company;
    @OneToOne
    private Project project;
    @OneToOne
    private UserClass userId;

    private String name;
    private String surname;
    private String fatherName;
    private String phone;
    private String noteText;
}
