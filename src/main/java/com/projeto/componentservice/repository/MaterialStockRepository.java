package com.projeto.componentservice.repository;

import com.projeto.componentservice.entity.MaterialStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialStockRepository extends JpaRepository<MaterialStock, String> {
}
