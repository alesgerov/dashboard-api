package com.example.dashboardapi.form;

import lombok.Data;

@Data
public class CountFormByStatus {

    private int countOfAllTicketsByStatus;
    private int countOfAllTicketsByStatusByProject;
    private int countOfAllTicketsByStatusByCompany;
}
