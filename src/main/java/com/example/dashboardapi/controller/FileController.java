package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.form.FileFormName;
import com.example.dashboardapi.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class FileController extends ApiControllerV1 {

    private final FileService fileService;
    private final ShortcutUtils shortcutUtils;

    public FileController(FileService fileService, ShortcutUtils shortcutUtils) {
        this.fileService = fileService;
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
    public ResponseEntity<?> addFile(@Valid @RequestBody FileFormName form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        }
        FileFormName fileForm = fileService.saveFile(form);
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
                                        @Valid @RequestBody FileFormName form, BindingResult result) {
        Optional<File> optionalFile = fileService.getFileById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        } else if (optionalFile.isEmpty()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This file does not exists.", 409, form));
        }
        FileFormName updated = fileService.updateFile(form, optionalFile.get());
        if (updated == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(200).body(shortcutUtils.successForm(updated));
    }


}
