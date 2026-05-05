package com.projeto.componentservice.dto;

import java.util.List;

public record ComponentPayload(
        String id,
        String name,
        String type, // "COMPONENT"[cite: 1]
        ProducerDTO producer,
        PurposeDTO purpose,
        List<Object> components // Lista de materiais processados usados
) { }
