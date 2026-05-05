package com.projeto.componentservice.kafka;

import com.projeto.componentservice.dto.BaseEvent;
import com.projeto.componentservice.service.ComponentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProcessedMaterialConsumer {

    private final ComponentService componentService;

    public KafkaProcessedMaterialConsumer(ComponentService componentService) {
        this.componentService = componentService;
    }

    @KafkaListener(topics = "industria-carro", groupId = "component-group")
    public void consume(BaseEvent event) {
        if ("MATERIAL_PROCESSED".equals(event.eventType())) {
            componentService.handleMaterial(event);
        }
    }
}
