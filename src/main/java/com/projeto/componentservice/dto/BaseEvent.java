package com.projeto.componentservice.dto;

import java.util.UUID;

public record BaseEvent(
        String eventId,
        String eventType,
        Long timestamp,
        String sourceService,
        String targetService,
        Object payload
) {
    public static BaseEvent create(String type, String source, String target, Object payload) {
        return new BaseEvent(
                UUID.randomUUID().toString(),
                type,
                System.currentTimeMillis(),
                source,
                target,
                payload
        );
    }
}
