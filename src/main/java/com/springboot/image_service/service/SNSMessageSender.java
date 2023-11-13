package com.springboot.image_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SNSMessageSender {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public SNSMessageSender(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    public void send(String topicName, Object message, String subject) {
        log.info("---> SNSMessageSender: send: message=" + message);

        notificationMessagingTemplate.sendNotification(topicName, message, subject);
    }
}