package com.example.dashboardapi.form;

import com.example.dashboardapi.entity.Ticket;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Data
public class FileForm {
    @NotBlank
    private int type;
    @NotBlank
    private String name;
    @NotBlank
    private long ticket_id;
}
