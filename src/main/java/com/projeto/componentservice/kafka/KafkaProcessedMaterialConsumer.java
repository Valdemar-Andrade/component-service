package com.projeto.componentservice.kafka;

import com.projeto.componentservice.dto.BaseEvent;
import com.projeto.componentservice.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProcessedMaterialConsumer {

    private final ComponentService componentService;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProcessedMaterialConsumer.class);

    public KafkaProcessedMaterialConsumer(ComponentService componentService) {
        this.componentService = componentService;
    }

    @KafkaListener(id = "component-service", topics = "industria-carro")
    public void consume(BaseEvent event) {
        try {
            if ("MATERIAL_PROCESSED".equals(event.eventType())) {
                logger.info("[COMPONENT-SERVICE] Processando material transformado: {}", event.eventId());
                componentService.handleMaterial(event);
            } else {
                logger.debug("[COMPONENT-SERVICE] Evento ignorado: {}", event.eventType());
            }
        } catch (Exception e) {
            //logger.error("[COMPONENT-SERVICE] Erro ao processar evento: {}", e.getMessage());
        }
    }

}
