package com.projeto.componentservice.repository;

import com.projeto.componentservice.entity.ComponentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentItem, UUID> {
}
