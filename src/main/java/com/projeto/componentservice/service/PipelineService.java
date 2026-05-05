package com.projeto.componentservice.service;

import com.projeto.componentservice.dto.BaseEvent;
import com.projeto.componentservice.dto.ComponentPayload;
import com.projeto.componentservice.dto.ProducerDTO;
import com.projeto.componentservice.dto.PurposeDTO;
import com.projeto.componentservice.entity.ComponentItem;
import com.projeto.componentservice.model.PipelineStep;
import com.projeto.componentservice.model.ProductionPipeline;
import com.projeto.componentservice.repository.ComponentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PipelineService {

    private final EventPublisher eventPublisher;
    private final ComponentRepository repository;

    public PipelineService(EventPublisher eventPublisher, ComponentRepository repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @Async
    public void execute(ProductionPipeline pipeline, ComponentItem entity, Map<String, Object> materialPayload) {
        // Logs claros conforme exigido
        System.out.println("[COMPONENTE] Iniciando produção de: " + entity.getName());

        for (PipelineStep step : pipeline.getSteps()) {
            try {
                Thread.sleep(step.getDurationMs()); // Latência real
                System.out.println("Etapa concluída: " + step.getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Monta o payload respeitando a árvore: Component -> Material
        ComponentPayload payload = new ComponentPayload(
                entity.getId().toString(),
                entity.getName(),
                "COMPONENT",
                new ProducerDTO("component-service", "Main-Factory"),
                new PurposeDTO("CAR", determineTarget(entity.getName()), "Peça validada"),
                List.of(materialPayload)
        );

        BaseEvent event = BaseEvent.create(
                "COMPONENT_CREATED", // Evento obrigatório
                "component-service",
                "assembly-service",
                payload
        );

        eventPublisher.sendMaterialComponentEvent(event);

        entity.setStatus("FINISHED");
        repository.save(entity);
    }

    private String determineTarget(String name) {
        if (name.contains("PISTON")) return "MOTOR";
        if (name.contains("TIRE")) return "WHEEL_SYSTEM";
        return "GENERAL_ASSEMBLY";
    }
}
