package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Price, Long> {
    com.soen487.rest.project.repository.core.entity.Price findByBookAndSeller(com.soen487.rest.project.repository.core.entity.Book book,
                                                                              com.soen487.rest.project.repository.core.entity.Seller seller);
    com.soen487.rest.project.repository.core.entity.Price saveAndFlush(com.soen487.rest.project.repository.core.entity.Price price);
    com.soen487.rest.project.repository.core.entity.Price save(com.soen487.rest.project.repository.core.entity.Price price);
    List<com.soen487.rest.project.repository.core.entity.Price> findAllByBook(com.soen487.rest.project.repository.core.entity.Book book);
    long deleteByPid(long pid);
}
