package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.entity.Project;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.FileForm;
import com.example.dashboardapi.form.ProjectForm;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.repository.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public Optional<File> getFileById(long id){
        return fileRepository.findById(id);
    }


    public ResponseEntity<ResponseForm> deleteFile(long id){
        ResponseForm form=new ResponseForm();
        if (getFileById(id).isPresent()){
            fileRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            return ResponseEntity.ok(form);
        }
        form.setMessage("This file does not exists");
        form.setStatus(409);
        return ResponseEntity.status(409).body(form);
    }

    public List<File> getAllFiles(){
        return fileRepository.findAll();
    }


    public FileForm saveUtils(FileForm form, File file){
        if (form==null){
            return null;
        }
        Optional<Ticket> optionalTicket=ticketService.getTicketById(form.getTicket_id());
        if (optionalTicket.isEmpty()){
            return null;
        }

        file.setName(form.getName());
        file.setTicket(optionalTicket.get());
        file.setType(form.getType());
        fileRepository.save(file);
        return form;
    }


    public FileForm saveFile(FileForm form){
        File file=new File();
        return saveUtils(form,file);
    }

    public FileForm updateProject(FileForm form,File file){
        return saveUtils(form,file);
    }


    public List<File> getAllFilesByName(String name) {
        return fileRepository.getFilesByName(name);
    }

}
