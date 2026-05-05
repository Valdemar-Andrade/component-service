package com.projeto.componentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineStep {
    private String name; // EXTRACTION, TRANSPORT
    private Long durationMs;
}
