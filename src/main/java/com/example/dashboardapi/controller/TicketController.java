package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.CountFormByStatus;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.form.TicketForm;
import com.example.dashboardapi.form.UtilForm;
import com.example.dashboardapi.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class TicketController extends ApiControllerV1 {

    private final ShortcutUtils utils;
    private final TicketService ticketService;

    public TicketController(ShortcutUtils utils, TicketService ticketService) {
        this.utils = utils;
        this.ticketService = ticketService;
    }

    @GetMapping(value = {"/{company_id}/{project_id}/{status_id}", "/{company_id}/{project_id}/{status_id}/"})
    public ResponseEntity getStatus(@PathVariable("company_id") int company_id,
                                    @PathVariable("project_id") int project_id,
                                    @PathVariable("status_id") int status_id
    ) {
        UtilForm utilForm = utils.getOptionals(company_id, project_id);
        if (utilForm != null) {
            CountFormByStatus form = ticketService.getTicketCountFormByStatus(status_id, utilForm.getProject(), utilForm.getCompany());
            return ResponseEntity.ok().body(form);
        } else {
            ResponseForm errorForm = new ResponseForm();
            errorForm.setStatus(409);
            errorForm.setMessage("Bad inputs");
            return ResponseEntity.status(409).body(errorForm);
        }


    }


    @PostMapping(value = {"/save/ticket", "/save/ticket/"})
    public ResponseEntity saveTicket(@RequestBody TicketForm form, BindingResult result) {
        if (result.hasErrors()) {
            ResponseForm errorForm = new ResponseForm(result.getAllErrors().get(0).getDefaultMessage(), 409);
            return ResponseEntity.status(409).body(errorForm);
        }
        form.setDateTime(LocalDateTime.now());
        TicketForm form1 = ticketService.saveTicket(form);

        if (form1 == null) {
            ResponseForm errorForm = new ResponseForm("Bad inputs", 409);
            return ResponseEntity.status(409).body(errorForm);
        }
        return ResponseEntity.status(201).body(form1);
    }

    @PutMapping(value = {"/update/ticket/{id}", "/update/ticket/{id}/"})
    public ResponseEntity updateTicket(@PathVariable("id") long id,
                                       @RequestBody TicketForm form, BindingResult result) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (result.hasErrors()) {
            ResponseForm errorForm = new ResponseForm(result.getAllErrors().get(0).getDefaultMessage(), 409);
            return ResponseEntity.status(409).body(errorForm);
        } else if (ticket.isEmpty()) {
            ResponseForm errorForm = new ResponseForm("Ticket does not exist.", 409);
            return ResponseEntity.status(409).body(errorForm);
        }
        form.setDateTime(LocalDateTime.now());

        TicketForm form1 = ticketService.updateTicket(form, ticket.get());

        if (form1 == null) {
            ResponseForm errorForm = new ResponseForm("Bad inputs", 409);
            return ResponseEntity.status(409).body(errorForm);
        }
        return ResponseEntity.status(201).body(form1);
    }


    @DeleteMapping(value = {"/delete/ticket/{id}", "/delete/ticket/{id}/"})
    public ResponseEntity<ResponseForm> deleteTicket(@PathVariable("id") long id) {
        return ticketService.deleteTicket(id);
    }

    @GetMapping(value = {"/ticket/{id}", "/ticket/{id}/"})
    public ResponseEntity viewTicket(@PathVariable("id") long id) {
        return ResponseEntity.ok(ticketService.getTicketFormById(id));
    }

    @GetMapping(value = {"/tickets/project/{project_id}/company/{company_id}","/tickets/project/{project_id}/company/{company_id}/"})
    public ResponseEntity getTickets(@PathVariable("project_id") long project,
                                     @PathVariable("company_id") long company) {
        UtilForm utilForm = utils.getOptionals(company, project);
        if (utilForm != null) {
            return ResponseEntity.ok().body(ticketService.getAllTicket(utilForm.getProject(),utilForm.getCompany()));
        }
        ResponseForm errorForm = new ResponseForm("Bad inputs", 409);
        return ResponseEntity.status(409).body(errorForm);
    }

    @GetMapping(value = {"/tickets/","/tickets"})
    public ResponseEntity filterTicket(@RequestParam(required = false,name = "content") String content){
        return ResponseEntity.ok().body(ticketService.filterTitle(content));
    }
}
//@RequestParam(required = false,name = "content") String content