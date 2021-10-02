package com.example.dashboardapi.form;

import lombok.Data;

@Data
public class CountForm {
    private int countOfAllTicketsByCompany;
    private int countOfAllTicketsByProject;
    private int countOfAllTicketsByProjectAndCompany;

}
