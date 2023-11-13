package com.springboot.image_service.service;

import com.springboot.image_service.model.NotificationRequest;

public interface SNSService {

    void sendSNS(NotificationRequest notificationRequest);
}
