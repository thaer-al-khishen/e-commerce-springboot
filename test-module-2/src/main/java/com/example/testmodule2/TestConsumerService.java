package com.example.testmodule2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TestConsumerService {

    @KafkaListener(topics = "testTopic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received message in test-module-2: " + message);
    }
}
