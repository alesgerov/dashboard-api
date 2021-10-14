package com.example.dashboardapi.service;

import com.example.dashboardapi.configration.FileConfiguration;
import com.example.dashboardapi.entity.File;
import com.example.dashboardapi.entity.Ticket;
import com.example.dashboardapi.form.FileFormName;
import com.example.dashboardapi.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class UploadService {
    private final Path fileLocation;

    @Autowired
    private FileRepository repository;

    @Autowired
    private TicketService ticketService;

    private final FileConfiguration configuration;


    public UploadService(FileConfiguration configuration) throws Exception {
        this.configuration = configuration;
        this.fileLocation = Paths.get(this.configuration.getLocation())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }

    }

    private FileFormName storeFile(MultipartFile multipartFile, FileFormName formName, File file) throws Exception {

        String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileName = "";

        try {
            if (originalFileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";

            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch (Exception e) {
                fileExtension = "";
            }
            fileName = formName.getName() + "_" + formName.getType() + "_" + formName.getTicketTitle() + fileExtension;
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Optional<Ticket> optionalTicket = ticketService.getTicketByTitle(formName.getTicketTitle());
            if (optionalTicket.isEmpty()) return null;


            file.setTicket(optionalTicket.get());
            file.setDocumentFormat(multipartFile.getContentType());
            file.setName(fileName);
            file.setType(formName.getType());
            file.setFilePlace(configuration.getLocation() + "/" + fileName);
            File file1 = repository.save(file);

            if (file1 == null) return null;

            formName.setFilePlace(configuration.getLocation() + "/" + fileName);

            return formName;

        } catch (Exception ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public FileFormName saveFile(MultipartFile multipartFile,FileFormName formName){
        File file=new File();
        try {
            return storeFile(multipartFile,formName,file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileFormName updateFile(MultipartFile multipartFile,FileFormName formName,File file){
        try {
            return storeFile(multipartFile,formName,file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
