package com.example.dashboardapi.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class  FileFormName {

    @NotNull
    private int type;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotBlank
    private String ticketTitle;
    private String filePlace;

    public FileFormName(int type, String name, String ticketTitle) {
        this.type = type;
        this.name = name;
        this.ticketTitle = ticketTitle;
    }
}
