package com.projeto.componentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComponentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name; // Ex: Pistão, Pneu, Bateria
    private String type; // "COMPONENT"
    private String status; // CREATED, IN_PROGRESS, FINISHED

    // Lista de IDs dos materiais processados que foram usados
    @ElementCollection
    private List<String> sourceMaterialIds;
}
