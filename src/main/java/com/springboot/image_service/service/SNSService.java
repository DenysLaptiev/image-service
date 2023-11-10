//package com.springboot.image_service.service;
//
//import com.amazonaws.services.sns.AmazonSNSClient;
//import com.amazonaws.services.sns.model.PublishRequest;
//import com.amazonaws.services.sns.model.SubscribeRequest;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SNSService {
//
//    public static final String PROTOCOL_NAME_EMAIL = "email" ;
//    public static final String PROTOCOL_NAME_HTTP = "http" ;
//
//    @Value("${cloud.aws.topic.arn}")
//    private String topicArn;
//
//    private final AmazonSNSClient snsClient;
//
//    @Autowired
//    public SNSService(AmazonSNSClient snsClient) {
//        this.snsClient = snsClient;
//    }
//
//    public String addSubscription(String email) {
//        SubscribeRequest request = new SubscribeRequest(topicArn, PROTOCOL_NAME_EMAIL, email);
//        snsClient.subscribe(request);
//        return "Subscription request is pending. To confirm the subscription, check your email: " + email;
//    }
//
//    @SneakyThrows
//    public String addHttpSubscription(String endpoint) {
//        //SubscribeRequest request = new SubscribeRequest(topicArn, PROTOCOL_NAME_HTTP, endpoint);
//
//        SubscribeRequest request = new SubscribeRequest()
//                .withTopicArn(topicArn)
//                .withProtocol("https")
////                .withEndpoint("https://" + InetAddress.getLocalHost().getHostAddress() + ":" + "8081" + "/sns-rest/upload-complete");
////                .withEndpoint("https://" + InetAddress.getLocalHost().getHostAddress() + ":" + "8081" + "/sns-rest/upload-complete");
////                .withEndpoint("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + "8081" + "/sns-rest");
//                .withEndpoint(endpoint);
//
//        snsClient.subscribe(request);
//        return "Subscribed!" ;
//    }
//
//    public String publishMessageToTopic() {
//        PublishRequest request = new PublishRequest(topicArn, message(), subject());
//        snsClient.publish(request);
//        return "Notification sent successfully!" ;
//    }
//
//    private String message() {
//        return
//                "Dear Employee," + "\n" +
//                        " Connection is down." + "\n" +
//                        " All services in Bangalore Data center are not accessible." + "\n" +
//                        " We are working on it!Notification will be sent out as soon as the issue is resolved." ;
//
//
//    }
//
//    private String subject() {
//        return "Notification: Network connection issue" ;
//    }
//}
