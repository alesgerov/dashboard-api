package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.CountFormByStatus;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.form.TicketFormName;
import com.example.dashboardapi.form.UtilForm;
import com.example.dashboardapi.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class TicketController extends ApiControllerV1 {

    private final ShortcutUtils utils;
    private final TicketService ticketService;

    public TicketController(ShortcutUtils utils, TicketService ticketService) {
        this.utils = utils;
        this.ticketService = ticketService;
    }

    @GetMapping(value = {"/{company}/{project}/{status_id}", "/{company}/{project}/{status_id}/"})
    public ResponseEntity<?> getStatus(@PathVariable("company") String company_id,
                                       @PathVariable("project") String project_id,
                                       @PathVariable("status_id") int status_id
    ) {
        UtilForm utilForm = utils.getOptionals(company_id, project_id);
        if (utilForm != null) {
            CountFormByStatus form = ticketService.getTicketCountFormByStatus(status_id, utilForm.getProject(), utilForm.getCompany());
            return ResponseEntity.ok().body(utils.successForm(form));
        } else {
            return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, "Bad inputs"));
        }
    }


    @PostMapping(value = {"/add/ticket", "/add/ticket/"})
    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketFormName form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        }
        form.setDateTime(LocalDateTime.now());
        TicketFormName form1 = ticketService.saveTicket(form);

        if (form1 == null) {
            return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(201).body(utils.createdForm(form1));
    }


    @PutMapping(value = {"/update/ticket/{id}", "/update/ticket/{id}/"})
    public ResponseEntity<?> updateTicket(@PathVariable("id") long id,
                                          @Valid @RequestBody TicketFormName form, BindingResult result) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        if (ticket.isEmpty()) {
            return ResponseEntity.status(409).body(utils.getErrorForm("This company does not exists.", 409, form));
        }
        if (result.hasErrors()) {
            if (!ticket.get().getTitle().equals(form.getTitle())) {
                return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
            }else if (ticket.get().getTitle().equals(form.getTitle()) && result.getErrorCount()>1){
                List<ObjectError> errors=utils.reNewErrors(result,"name");
                return ResponseEntity.status(409).body(utils.getErrorForm(errors.get(0).getDefaultMessage(),409,form));
            }
        }
        form.setDateTime(LocalDateTime.now());

        TicketFormName form1 = ticketService.updateTicket(form, ticket.get());

        if (form1 == null) {
            return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(200).body(utils.successForm(form1));
    }


    @DeleteMapping(value = {"/delete/ticket/{id}", "/delete/ticket/{id}/"})
    public ResponseEntity<ResponseForm> deleteTicket(@PathVariable("id") long id) {
        return ticketService.deleteTicket(id);
    }

    @GetMapping(value = {"/ticket/{id}", "/ticket/{id}/"})
    public ResponseEntity<?> viewTicket(@PathVariable("id") long id) {
        TicketFormName form = ticketService.getTicketFormById(id);
        if (form != null) {
            return ResponseEntity.ok(utils.successForm(ticketService.getTicketFormById(id)));
        }
        return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, "This ticket does not exists"));
    }


    @GetMapping(value = {"/tickets/project/{project}/company/{company}", "/tickets/project/{project}/company/{company}/"})
    public ResponseEntity<?> getTickets(@PathVariable("project") String project,
                                        @PathVariable("company") String company) {
        UtilForm utilForm = utils.getOptionals(company, project);
        if (utilForm != null) {
            List<Ticket> tickets = ticketService.getAllTicket(utilForm.getProject(), utilForm.getCompany());
            List<TicketFormName> result = ticketService.mapperToTicketFormName(tickets);
            return ResponseEntity.ok().body(utils.successForm(result));
        }
        return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, "Tickets for the company or project not found."));
    }


    @GetMapping(value = {"/tickets"})
    public ResponseEntity<?> filterTicket(@RequestParam(required = false, name = "title") String content) {
        List<Ticket> tickets = ticketService.filterTitle(content);
        List<TicketFormName> ticketFormNames = ticketService.mapperToTicketFormName(tickets);
        return ResponseEntity.ok().body(utils.successForm(ticketFormNames));
    }

    @GetMapping(value = {"/tickets/"})
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTicket();
        List<TicketFormName> ticketFormNames = ticketService.mapperToTicketFormName(tickets);
        return ResponseEntity.ok(utils.successForm(ticketFormNames));
    }
}
