package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.form.FileForm;
import com.example.dashboardapi.form.ProjectForm;
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

    @GetMapping(value = {"/files/","/files"})
    public ResponseEntity<List<File>> getFiles(){
        return ResponseEntity.ok().body(fileService.getAllFiles());
    }


    @GetMapping(value = {"/files/{id}","/files/{id}/"})
    public ResponseEntity<?> getFileById(@PathVariable("id") long id){
        Optional<File> optionalFile=fileService.getFileById(id);
        if(optionalFile.isPresent()){
            return ResponseEntity.ok().body(optionalFile.get());
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found",409));
    }



    @PostMapping(value = {"/add/file","/add/file/"})
    public ResponseEntity<?> addFile(@Valid @RequestBody FileForm form, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }
        FileForm fileForm=fileService.saveFile(form);
        if (fileForm==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(201).body(fileForm);

    }



    @DeleteMapping(value = {"/delete/file/{id}","/delete/file/{id}/"})
    public ResponseEntity<?> deleteFile(@PathVariable("id") long id){
        return fileService.deleteFile(id);
    }


    @GetMapping(value = {"/files/", "/files"})
    public ResponseEntity<?> filterFile(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(fileService.getAllFilesByName(name));
    }





}
