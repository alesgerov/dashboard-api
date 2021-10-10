package com.example.dashboardapi.form;


import com.example.dashboardapi.validator.UniqueTicketName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class TicketFormName {
    @NotBlank
    private String content;
    @JsonIgnore
    private LocalDateTime dateTime;
    private int priority = 1;//1-low 2-medium 3-high
    @NotBlank
    @UniqueTicketName
    private String title;
    @NotBlank
    private String company;
    @NotBlank
    private String project;
    private int status = 1;//1-waiting 2-done
}
