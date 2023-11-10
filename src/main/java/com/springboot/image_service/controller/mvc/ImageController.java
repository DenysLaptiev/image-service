package com.springboot.image_service.controller.mvc;

import com.springboot.image_service.model.Metadata;
import com.springboot.image_service.service.FileService;
import com.springboot.image_service.service.MetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Controller
@RequestMapping("/file")
@Slf4j
public class ImageController {

    //TODO: remove this @Controller after testing
    //TODO: remove templates messagePage.html, uploadPage.html and jquery js from this microservice

    //TODO: add exceptions handlers
    //TODO: handle exceptions - org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException: the request was rejected because its size (27671082) exceeds the configured maximum (10485760)

    private final FileService fileService;
    private final MetadataService metadataService;

    @Autowired
    public ImageController(FileService fileService, MetadataService metadataService) {
        this.fileService = fileService;
        this.metadataService = metadataService;
    }

    @GetMapping
    public String showUploadPage() {
        log.info("--> ImageController: showUploadPage");
        return "uploadPage";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                             String description,
                             Model model) {

        log.info("--> ImageController: uploadFile");

        String fileName = multipartFile.getOriginalFilename();

        log.info("File name: " + fileName);
        log.info("Description: " + description);

        String resultMessage = "";

        try {
            fileService.upload(fileName, multipartFile.getInputStream());
            //TODO: Instant.now().toString() -> Instant.now()
            //TODO: decide what to do with metadata
            Metadata metadata = Metadata.builder()
                    .fileName(fileName)
                    .fileDescription(description)
                    //.createdBy(createdBy)
                    //.createdAt(createdAt)
                    .uploadedBy(null)
                    .uploadedAt(Instant.now())
                    .build();
            metadataService.save(metadata);
            resultMessage = "Your file has been uploaded successfully!";
        } catch (IOException e) {
            resultMessage = "Error when uploading file: " + e.getMessage();
        }

        model.addAttribute("resultMessage", resultMessage);
        return "messagePage";
    }
}
