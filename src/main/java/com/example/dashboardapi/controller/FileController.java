package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.form.FileFormName;
import com.example.dashboardapi.service.FileService;
import com.example.dashboardapi.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController extends ApiControllerV1 {

    private final FileService fileService;
    private final UploadService uploadService;
    private final ShortcutUtils shortcutUtils;

    public FileController(FileService fileService, UploadService uploadService, ShortcutUtils shortcutUtils) {
        this.fileService = fileService;
        this.uploadService = uploadService;
        this.shortcutUtils = shortcutUtils;
    }

    @GetMapping(value = {"/files/"})
    public ResponseEntity<?> getFiles() {
        List<File> result = fileService.getAllFiles();
        return ResponseEntity.ok().body(shortcutUtils.successForm(fileService.mapperToProjectFormName(result)));
    }


    @GetMapping(value = {"/files/{id}", "/files/{id}/"})
    public ResponseEntity<?> getFileById(@PathVariable("id") long id) {
        Optional<File> optionalFile = fileService.getFileById(id);
        if (optionalFile.isPresent()) {
            return ResponseEntity.ok().body(shortcutUtils.successForm(fileService.getFileForm(optionalFile.get())));
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found", 409, "This file does not found"));
    }




    @PostMapping(value = {"/add/file", "/add/file/"})
    public ResponseEntity<?> addFile(@RequestParam("type") int type,
                                     @RequestParam("name") String name,
                                     @RequestParam("ticketTitle") String ticketTitle,
                                     @RequestParam("file") MultipartFile file)
            throws IOException {
        FileFormName form=new FileFormName(type,name,ticketTitle);

        FileFormName fileForm = uploadService.saveFile(file, form);
        if (fileForm == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(201).body(shortcutUtils.createdForm(fileForm));

    }


    @DeleteMapping(value = {"/delete/file/{id}", "/delete/file/{id}/"})
    public ResponseEntity<?> deleteFile(@PathVariable("id") long id) {
        return fileService.deleteFile(id);
    }


    @GetMapping(value = {"/files"})
    public ResponseEntity<?> filterFile(@RequestParam(required = false, name = "name") String name) {
        List<File> files = fileService.getAllFilesByName(name);
        return ResponseEntity.ok().body(shortcutUtils.successForm(fileService.mapperToProjectFormName(files)));
    }


    @PutMapping(value = {"/update/file/{id}", "/update/file/{id}/"})
    public ResponseEntity<?> updateFile(@PathVariable("id") long id,
                                        @RequestParam("type") int type,
                                        @RequestParam("name") String name,
                                        @RequestParam("ticketTitle") String ticketTitle,
                                        @RequestParam("file") MultipartFile file
                                        ) {
        Optional<File> optionalFile = fileService.getFileById(id);
        FileFormName form=new FileFormName(type,name,ticketTitle);
        if (optionalFile.isEmpty()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This file does not exists.", 409, form));
        }
        FileFormName updated = uploadService.updateFile(file,form,optionalFile.get());
        if (updated == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(200).body(shortcutUtils.successForm(updated));
    }


}
