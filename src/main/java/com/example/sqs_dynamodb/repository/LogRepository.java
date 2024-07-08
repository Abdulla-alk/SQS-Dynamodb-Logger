package com.example.sqs_dynamodb.repository;

import com.example.sqs_dynamodb.entity.Logs;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface LogRepository extends CrudRepository<Logs, String> {
}
