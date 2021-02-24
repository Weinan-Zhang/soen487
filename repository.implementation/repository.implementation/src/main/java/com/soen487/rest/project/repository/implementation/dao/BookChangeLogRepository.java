package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookChangeLogRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.BookChangeLog, Long> {
    com.soen487.rest.project.repository.core.entity.BookChangeLog save(com.soen487.rest.project.repository.core.entity.BookChangeLog bookChangeLog);
}
