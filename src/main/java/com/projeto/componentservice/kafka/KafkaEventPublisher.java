package com.projeto.componentservice.kafka;

import com.projeto.componentservice.dto.BaseEvent;
import com.projeto.componentservice.service.EventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public KafkaEventPublisher(
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void sendMaterialComponentEvent(BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
