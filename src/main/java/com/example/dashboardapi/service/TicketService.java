package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.form.CountForm;
import com.example.dashboardapi.form.CountFormByStatus;
import com.example.dashboardapi.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {
    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public CountForm getTicketCountForm(Project project, Company company){
        CountForm form=new CountForm();
        form.setCountOfAllTicketsByCompany(repository.getCountOfAllTicketsByCompany(company));
        form.setCountOfAllTicketsByProject(repository.getCountOfAllTicketsByProject(project));
        form.setCountOfAllTicketsByProjectAndCompany(repository.getCountOfAllTicketsByProjectAndCompany(project,company));
        return form;
    }


    public CountForm getTicketCountForm( Company company){
        CountForm form=new CountForm();
        form.setCountOfAllTicketsByCompany(repository.getCountOfAllTicketsByCompany(company));
        return form;
    }


    public CountForm getTicketCountForm(Project project){
        CountForm form=new CountForm();
        form.setCountOfAllTicketsByProject(repository.getCountOfAllTicketsByProject(project));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status,Project project, Company company){
        CountFormByStatus form=new CountFormByStatus();
        form.setCountOfAllTicketsByStatus(repository.getCountOfAllTicketsByStatus(project,company,status));
        form.setCountOfAllTicketsByStatusByCompany(repository.getCountOfAllTicketsByStatusByCompany(company,status));
        form.setCountOfAllTicketsByStatusByProject(repository.getCountOfAllTicketsByStatusByProject(project,status));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status,Project project){
        CountFormByStatus form=new CountFormByStatus();
        form.setCountOfAllTicketsByStatusByProject(repository.getCountOfAllTicketsByStatusByProject(project,status));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status,Company company){
        CountFormByStatus form=new CountFormByStatus();
        form.setCountOfAllTicketsByStatusByCompany(repository.getCountOfAllTicketsByStatusByCompany(company,status));
        return form;
    }
}
