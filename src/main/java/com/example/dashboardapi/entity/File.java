package com.example.dashboardapi.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
public class File {
    @Id
    @SequenceGenerator(
            name = "file_id_seq",
            sequenceName = "file_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_id_seq"
    )
    private long id;
    private int type;
    private String name;
    private String filePlace;
    @ManyToOne
    private Ticket ticket;
}
