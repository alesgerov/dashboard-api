package com.example.dashboardapi.form;

import com.example.dashboardapi.entity.Ticket;
import lombok.Data;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FileForm {
    @NotNull
    private int type;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotNull
    private long ticket_id;
}
