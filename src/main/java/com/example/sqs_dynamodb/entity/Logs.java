package com.example.sqs_dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "Logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logs {

    @DynamoDBHashKey(attributeName = "requestId")
    private String requestId;

    @DynamoDBAttribute(attributeName = "request")
    private String request;

    @DynamoDBAttribute(attributeName = "response")
    private String response;

}
