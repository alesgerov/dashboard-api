package com.example.dashboardapi.form;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class TicketForm {
    @NotBlank
    private String content;
    @JsonIgnore
    private LocalDateTime dateTime;
    private int priority=1;//1-low 2-medium 3-high
    @NotBlank
    private String title;
    @NotBlank
    private long company_id;
    @NotBlank
    private long project_id;
    private int status=1;//1-waiting 2-done
}
