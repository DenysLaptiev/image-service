//package com.springboot.image_service.controller.rest;
//
//
//import com.springboot.image_service.service.SNSService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class MySNSRestController {
//
//    private final SNSService service;
//
//    @Autowired
//    public MySNSRestController(SNSService service) {
//        this.service = service;
//    }
//
//    //EMAIL
//    @GetMapping("/add-subscription-email/{email}")
//    public String addSubscribtion(@PathVariable String email) {
//        return service.addSubscription(email);
//    }
//
//    //HTTP
//    @GetMapping("/add-subscription/{path}")
//    public String addHttpSubscribtion(@PathVariable String path) {
////        String endpoint = "http://localhost:8081/sns-rest/upload-complete";
//        String endpoint = "https://ec2-13-38-244-120.eu-west-3.compute.amazonaws.com:9981/"+path;
////        String endpoint = "localhost:8081/sns-rest/upload-complete";
//        return service.addHttpSubscription(endpoint);
//    }
//
//    @GetMapping("/send-notification")
//    public String publishMessageToTopic() {
//        return service.publishMessageToTopic();
//    }
//}
