package com.soen487.rest.project.repository.implementation.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookChangeLogRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.BookChangeLog, Long>, JpaSpecificationExecutor {
    com.soen487.rest.project.repository.core.entity.BookChangeLog save(com.soen487.rest.project.repository.core.entity.BookChangeLog bookChangeLog);
    List<com.soen487.rest.project.repository.core.entity.BookChangeLog> findAllBy();
    List<com.soen487.rest.project.repository.core.entity.BookChangeLog> findAll(Specification specification);
}
