package com.springboot.image_service.service;

import com.springboot.image_service.model.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SNSServiceImpl implements SNSService {

    private final SNSMessageSender snsMessageSender;

    @Autowired
    public SNSServiceImpl(SNSMessageSender snsMessageSender) {
        this.snsMessageSender = snsMessageSender;
    }

    @Override
    public void sendSNS(NotificationRequest notificationRequest) {
        log.info("---> SNSServiceImpl: sendSNS");

        log.info("topicName=" + notificationRequest.getTopicName());
        log.info("message=" + notificationRequest.getMessage());
        log.info("subject=" + notificationRequest.getSubject());

        snsMessageSender.send(notificationRequest.getTopicName(),
                notificationRequest.getMessage(),
                notificationRequest.getSubject());
    }
}
