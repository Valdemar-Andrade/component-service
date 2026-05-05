package com.projeto.componentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionPipeline {

    private String name; // ENGINE_PRODUCTION, TIRE_PRODUCTION, SCREEN_PRODUCTION
    private List<PipelineStep> steps;
}
