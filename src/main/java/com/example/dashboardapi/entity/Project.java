package com.example.dashboardapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
public class Project {
    @Id
    @SequenceGenerator(
            name = "project_id_seq",
            sequenceName = "project_id_seq",
            allocationSize = 1,
            initialValue = 4
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_id_seq"
    )
    private long id;
    @ManyToOne
    private Company company;
    private String name;
    private String shortName;
}
