package com.example.dashboardapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Transactional
public class Ticket {
    @Id
    @SequenceGenerator(
            name = "ticket_id_seq",
            sequenceName = "ticket_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_id_seq"
    )
    private long id;
    private LocalDateTime dateTime;
    private String title;
    private String content;
    private int priority;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Project project;
}
