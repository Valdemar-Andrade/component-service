package com.projeto.componentservice.service;

import com.projeto.componentservice.dto.BaseEvent;

public interface EventPublisher {
    void sendMaterialComponentEvent(BaseEvent materialEvent);
}
