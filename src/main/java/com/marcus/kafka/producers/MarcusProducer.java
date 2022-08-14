package com.marcus.kafka.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class MarcusProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        System.out.println("PRODUCER -> message sent: " + message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("marcus", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendTestMessage() {
        send("Hello world this is Marcus");
    }

}
