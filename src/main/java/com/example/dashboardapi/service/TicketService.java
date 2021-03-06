package com.example.dashboardapi.service;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.*;
import com.example.dashboardapi.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    private final TicketRepository repository;
    private final ShortcutUtils shortcutUtils;


    public TicketService(TicketRepository repository, ShortcutUtils shortcutUtils) {
        this.repository = repository;

        this.shortcutUtils = shortcutUtils;
    }

    public CountForm getTicketCountForm(Project project, Company company) {
        CountForm form = new CountForm();
        form.setCountOfAllTicketsByCompany(repository.getCountOfAllTicketsByCompany(company));
        form.setCountOfAllTicketsByProject(repository.getCountOfAllTicketsByProject(project));
        form.setCountOfAllTicketsByProjectAndCompany(repository.getCountOfAllTicketsByProjectAndCompany(project, company));
        return form;
    }


    public CountForm getTicketCountForm(Company company) {
        CountForm form = new CountForm();
        form.setCountOfAllTicketsByCompany(repository.getCountOfAllTicketsByCompany(company));
        return form;
    }


    public CountForm getTicketCountForm(Project project) {
        CountForm form = new CountForm();
        form.setCountOfAllTicketsByProject(repository.getCountOfAllTicketsByProject(project));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status, Project project, Company company) {
        CountFormByStatus form = new CountFormByStatus();
        form.setCountOfAllTicketsByStatus(repository.getCountOfAllTicketsByStatus(project, company, status));
        form.setCountOfAllTicketsByStatusByCompany(repository.getCountOfAllTicketsByStatusByCompany(company, status));
        form.setCountOfAllTicketsByStatusByProject(repository.getCountOfAllTicketsByStatusByProject(project, status));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status, Project project) {
        CountFormByStatus form = new CountFormByStatus();
        form.setCountOfAllTicketsByStatusByProject(repository.getCountOfAllTicketsByStatusByProject(project, status));
        return form;
    }

    public CountFormByStatus getTicketCountFormByStatus(int status, Company company) {
        CountFormByStatus form = new CountFormByStatus();
        form.setCountOfAllTicketsByStatusByCompany(repository.getCountOfAllTicketsByStatusByCompany(company, status));
        return form;
    }

    public TicketFormName saveTicket(TicketFormName ticketForm) {
        Ticket ticket = new Ticket();
        return saveUtil(ticket, ticketForm);
    }

    public TicketFormName updateTicket(TicketFormName ticketForm, Ticket ticket) {
        return saveUtil(ticket, ticketForm);
    }

    public Optional<Ticket> getTicketById(long id) {
        return repository.findById(id);
    }

    private TicketFormName saveUtil(Ticket ticket, TicketFormName ticketForm) {
        UtilForm utilForm = shortcutUtils.getOptionals(ticketForm.getCompany(), ticketForm.getProject());
        if (utilForm == null) {
            return null;
        }
        ticket.setCompany(utilForm.getCompany());
        ticket.setProject(utilForm.getProject());
        ticket.setContent(ticketForm.getContent());
        ticket.setDateTime(ticketForm.getDateTime());
        ticket.setPriority(ticketForm.getPriority());
        ticket.setStatus(ticketForm.getStatus());
        ticket.setTitle(ticketForm.getTitle());
        repository.save(ticket);
        return ticketForm;
    }

    public ResponseEntity<ResponseForm> deleteTicket(long id) {
        ResponseForm form = new ResponseForm("Deleted", 200, null);
        if (getTicketById(id).isPresent()) {
            repository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            form.setContent("Deleted");
            return ResponseEntity.ok(form);
        }
        form.setMessage("This ticket does not exists");
        form.setStatus(409);
        form.setContent("This ticket does not exists");
        return ResponseEntity.status(409).body(form);
    }

    public TicketFormName getTicketFormById(long id) {
        Optional<Ticket> optional = getTicketById(id);
        if (optional.isEmpty()) {
            return null;
        }
        TicketFormName form = new TicketFormName();
        Ticket ticket = optional.get();
        form.setPriority(ticket.getPriority());
        form.setContent(ticket.getContent());
        form.setTitle(ticket.getTitle());
        form.setDateTime(ticket.getDateTime());
        form.setStatus(ticket.getStatus());
        form.setProject(ticket.getProject().getName());
        form.setCompany(ticket.getCompany().getName());
        return form;
    }

    public List<Ticket> getAllTicketsByCompany(Company company) {
        return repository.findAllByCompany(company);
    }

    public List<Ticket> getAllTicketsByProject(Project project) {
        return repository.findAllByProject(project);
    }

    public List<Ticket> filterTitle(String name) {
        return repository.findTicketsByTitle(name);
    }

    public List<Ticket> getAllTicket(Project project, Company company) {
        return repository.findAll(project, company);
    }

    public List<Ticket> getAllTicket() {
        return repository.findAll();
    }

    public List<Ticket> getAllByStatus(int i) {
        return repository.findAllByStatus(i);
    }

    public TicketFormName getTicketForm(Ticket ticket) {
        TicketFormName form = new TicketFormName();
        form.setCompany(ticket.getCompany().getName());
        form.setContent(ticket.getContent());
        form.setTitle(ticket.getTitle());
        form.setProject(ticket.getProject().getName());
        form.setStatus(ticket.getStatus());
        form.setDateTime(ticket.getDateTime());
        form.setPriority(ticket.getPriority());
        return form;
    }

    public List<TicketFormName> mapperToTicketFormName(List<Ticket> tickets) {
        List<TicketFormName> formNames = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            formNames.add(getTicketForm(tickets.get(i)));
        }
        return formNames;
    }

    public Optional<Ticket> getTicketByTitle(String title) {
        return repository.findByTitle(title);
    }
}
