package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.PriceHistory, Long> {
    com.soen487.rest.project.repository.core.entity.PriceHistory save(com.soen487.rest.project.repository.core.entity.PriceHistory priceHistory);
    long deleteByPrice(com.soen487.rest.project.repository.core.entity.Price price);
}
