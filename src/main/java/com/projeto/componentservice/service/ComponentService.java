package com.projeto.componentservice.service;

import com.projeto.componentservice.dto.BaseEvent;
import com.projeto.componentservice.entity.ComponentItem;
import com.projeto.componentservice.entity.MaterialStock;
import com.projeto.componentservice.model.PipelineStep;
import com.projeto.componentservice.model.ProductionPipeline;
import com.projeto.componentservice.repository.ComponentRepository;
import com.projeto.componentservice.repository.MaterialStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComponentService {

    private final MaterialStockRepository stockRepository;
    private final ComponentRepository componentRepository;
    private final PipelineService pipelineService;

    public ComponentService(MaterialStockRepository stockRepository, ComponentRepository componentRepository, PipelineService pipelineService) {
        this.stockRepository = stockRepository;
        this.componentRepository = componentRepository;
        this.pipelineService = pipelineService;
    }

    @Transactional
    public void handleMaterial(BaseEvent event) {
        Map<String, Object> materialData = (Map<String, Object>) event.payload();
        String materialName = materialData.get("name").toString().toUpperCase();

        MaterialStock stock = stockRepository.findById(materialName)
                .orElse(new MaterialStock(materialName, 0));
        stock.setAvailableQuantity(stock.getAvailableQuantity() + 1);
        stockRepository.save(stock);

        checkAndProduce(materialName, materialData);
    }

    private void checkAndProduce(String materialName, Map<String, Object> lastMaterialPayload) {
        if (materialName.equals("STEEL") && hasStock("STEEL", 1)) {
            consumeAndStartPipeline("PISTON", "STEEL", 1, lastMaterialPayload);
        }
        else if (materialName.equals("RUBBER") && hasStock("RUBBER", 1)) {
            consumeAndStartPipeline("TIRE", "RUBBER", 1, lastMaterialPayload);
        }
    }

    private void consumeAndStartPipeline(String componentName, String materialUsed, int qty, Map<String, Object> payload) {
        MaterialStock stock = stockRepository.findById(materialUsed).get();
        stock.setAvailableQuantity(stock.getAvailableQuantity() - qty);
        stockRepository.save(stock);

        ComponentItem item = ComponentItem.builder()
                .name(componentName)
                .type("COMPONENT")
                .status("IN_PROGRESS")
                .build();
        componentRepository.save(item);

        ProductionPipeline pipeline = ProductionPipeline.builder()
                .name("PIPELINE_" + componentName)
                .steps(List.of(
                        new PipelineStep("PART_PREPARATION", 6000L),
                        new PipelineStep("ASSEMBLY", 10000L),
                        new PipelineStep("QUALITY_CHECK", 4000L)
                )).build();

        pipelineService.execute(pipeline, item, payload);
    }

    private boolean hasStock(String material, int required) {
        return stockRepository.findById(material)
                .map(s -> s.getAvailableQuantity() >= required)
                .orElse(false);
    }
}
