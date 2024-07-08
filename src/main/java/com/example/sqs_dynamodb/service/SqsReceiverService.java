package com.example.sqs_dynamodb.service;

import com.example.sqs_dynamodb.entity.Logs;
import com.example.sqs_dynamodb.repository.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SqsReceiverService {
    private final SqsClient sqsClient;
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final String queueUrl = "https://sqs.us-east-2.amazonaws.com/339712836705/MyQueue";

    @Autowired
    public SqsReceiverService(SqsClient sqsClient, LogRepository logRepository, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
    public void receiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .build();
        sqsClient.receiveMessage(receiveMessageRequest).messages().forEach(this::processMessage);
    }


    private void processMessage(Message message) {
        System.out.println(message);
        try {
            Logs logEntry = objectMapper.readValue(message.body(), Logs.class);
            logRepository.save(logEntry);
            deleteMessage(message);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }

}
