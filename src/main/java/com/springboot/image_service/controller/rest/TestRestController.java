package com.springboot.image_service.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestRestController {

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam String fileName){
        log.info("---> TestRestController: test: fileName=" + fileName);

        return ResponseEntity.ok(fileName);
    }
}
