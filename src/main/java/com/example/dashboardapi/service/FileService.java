package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.FileFormName;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.repository.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final TicketService ticketService;

    public FileService(FileRepository fileRepository, TicketService ticketService) {
        this.fileRepository = fileRepository;
        this.ticketService = ticketService;
    }

    public Optional<File> getFileById(long id) {
        return fileRepository.findById(id);
    }


    public ResponseEntity<ResponseForm> deleteFile(long id) {
        ResponseForm form = new ResponseForm();
        if (getFileById(id).isPresent()) {
            fileRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            form.setContent("Deleted");
            return ResponseEntity.ok(form);
        }
        form.setMessage("This file does not exists");
        form.setStatus(409);
        form.setContent("This file does not exists");
        return ResponseEntity.status(409).body(form);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }


    public FileFormName saveUtils(FileFormName form, File file) {
        if (form == null) {
            return null;
        }
        Optional<Ticket> optionalTicket = ticketService.getTicketByTitle(form.getTicketTitle());
        if (optionalTicket.isEmpty()) {
            return null;
        }

        file.setName(form.getName());
        file.setTicket(optionalTicket.get());
        file.setType(form.getType());
        file.setFilePlace(form.getFilePlace());
        fileRepository.save(file);
        return form;
    }


    public FileFormName saveFile(FileFormName form) {
        File file = new File();
        return saveUtils(form, file);
    }

    public FileFormName updateFile(FileFormName form, File file) {
        return saveUtils(form, file);
    }


    public List<File> getAllFilesByName(String name) {
        return fileRepository.getFilesByName(name);
    }


    public FileFormName getFileForm(File file) {
        FileFormName form = new FileFormName();
        form.setFilePlace(file.getFilePlace());
        form.setTicketTitle(file.getTicket().getTitle());
        form.setName(file.getName());
        form.setType(file.getType());
        return form;
    }

    public List<FileFormName> mapperToProjectFormName(List<File> files) {
        List<FileFormName> formNames = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            formNames.add(getFileForm(files.get(i)));
        }
        return formNames;
    }
}
