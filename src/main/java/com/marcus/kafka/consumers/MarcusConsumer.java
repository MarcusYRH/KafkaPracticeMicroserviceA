package com.marcus.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MarcusConsumer {

    @KafkaListener(topics = {"marcus"}, groupId = "foo")
    public void receive(@Payload String message) {
        System.out.println("COSNUMER -> message received: " + message);
    }
}
