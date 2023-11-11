//package com.springboot.image_service.controller.rest;
//
//import com.amazonaws.services.sns.message.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/pikkisikka")
//@Slf4j
//class SnsRestController {
//
//    private final SnsMessageManager messageParser = new SnsMessageManager();
//
//
//    @RequestMapping(value = "/silli-karvallinenn", method = {RequestMethod.GET, RequestMethod.POST})
//    public void snsProcess(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
//
//
//        messageParser.handleMessage(httpRequest.getInputStream(), new DefaultSnsMessageHandler() {
//
//            @Override
//            public void handle(SnsNotification snsNotification) {
//                //addMessage(snsNotification);
//                // If the subject is "unsubscribe" then unsubscribe from this topic
//                if (snsNotification.getSubject().equalsIgnoreCase("unsubscribe")) {
//                    snsNotification.unsubscribeFromTopic();
//                } else {
//                    // Otherwise process the message
//                    log.info("Received message. Subject= "+ snsNotification.getSubject()
//                                    + "Message = "+snsNotification.getMessage());
//                }
//            }
//
//            @Override
//            public void handle(SnsUnsubscribeConfirmation message) {
//                log.info("Received unsubscribe confirmation.");
//            }
//
//            @Override
//            public void handle(SnsSubscriptionConfirmation message) {
//                super.handle(message);
//                log.info("Received subscription confirmation.");
//
//            }
//
//
//        });
//    }
//
//
//}
