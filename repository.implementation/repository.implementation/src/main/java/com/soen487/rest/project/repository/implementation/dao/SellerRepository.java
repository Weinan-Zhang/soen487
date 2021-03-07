package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Seller, Long> {
    com.soen487.rest.project.repository.core.entity.Seller findByName(String name);
    com.soen487.rest.project.repository.core.entity.Seller saveAndFlush(com.soen487.rest.project.repository.core.entity.Seller seller);
}
