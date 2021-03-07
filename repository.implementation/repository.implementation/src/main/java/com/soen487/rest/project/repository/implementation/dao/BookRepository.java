package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Book,Long> {
    boolean existsByIsbn13(String isbn13);
    boolean existsByBid(long bid);
    List<com.soen487.rest.project.repository.core.entity.Book> findAllBy();
    com.soen487.rest.project.repository.core.entity.Book findByIsbn13(String isbn13);
    com.soen487.rest.project.repository.core.entity.Book findByBid(long bid);
    com.soen487.rest.project.repository.core.entity.Book saveAndFlush(com.soen487.rest.project.repository.core.entity.Book book);
    long deleteByBid(long bid);

}
