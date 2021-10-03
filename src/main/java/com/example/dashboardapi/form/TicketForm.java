package com.example.dashboardapi.form;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private long company_id;
    @NotNull
    private long project_id;
    private int status=1;//1-waiting 2-done
}
