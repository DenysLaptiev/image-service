package com.springboot.image_service.controller.rest;

import com.springboot.image_service.model.Metadata;
import com.springboot.image_service.service.FileService;
import com.springboot.image_service.service.MetadataService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file-rest")
@Slf4j
public class ImageRestController {

    //TODO: add exceptions handlers
    //TODO: handle exceptions - org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException: the request was rejected because its size (27671082) exceeds the configured maximum (10485760)

    private final FileService fileService;
    private final MetadataService metadataService;


    @Autowired
    public ImageRestController(FileService fileService, MetadataService metadataService) {
        this.fileService = fileService;
        this.metadataService = metadataService;
    }

    @SneakyThrows
    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile,
                                        @RequestParam(defaultValue = "File description") String description,
                                        @AuthenticationPrincipal Jwt jwt) {

        String fileName = multipartFile.getOriginalFilename();

        log.info("--> ImageRestController: uploadFile");
        log.info("--> ImageRestController: jwt" + jwt);

        String uploadedBy = (String) jwt.getClaims().get("preferred_username");
        String email = (String) jwt.getClaims().get("email");

        log.info("--> ImageRestController: uploadedBy" + uploadedBy);
        log.info("--> ImageRestController: email" + email);

        Metadata metadata = metadataService.extractMetadata(multipartFile, description, uploadedBy);
        log.info("--> ImageRestController: metadata" + metadata);

        String resultMessage = "";

        try {
            metadataService.validateIfAlreadyExists(metadata);

            //TODO: Instant.now().toString() -> Instant.now()
            //TODO: decide what to do with metadata
            metadataService.save(metadata);

            fileService.upload(fileName, multipartFile.getInputStream());

            resultMessage = "Your file has been uploaded successfully!";
            return new ResponseEntity<>(resultMessage, HttpStatus.OK);
        } catch (IOException e) {
            resultMessage = "Error when uploading file: " + e.getMessage();
            throw new RuntimeException(resultMessage, e);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        log.info("--> ImageRestController: uploadFile");

        //keyName - is a full path to file on AWS S3
        byte[] data = fileService.downloadRest(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        boolean isFileDeleted = fileService.delete(fileName);

        //TODO: decide what to do with metadata
        //TODO: check if the file was realy deleted (existed before and disappear then)
        boolean isMetadataDeleted = metadataService.delete(fileName);
        String result = "";
        if (isFileDeleted) {
            result = "File was deleted successfully!";
        } else {
            result = "File was NOT deleted!";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}



