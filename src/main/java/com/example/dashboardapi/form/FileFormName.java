package com.example.dashboardapi.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FileFormName {
    @NotNull
    private int type;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotBlank
    private String ticketTitle;
    private String filePlace;

}
