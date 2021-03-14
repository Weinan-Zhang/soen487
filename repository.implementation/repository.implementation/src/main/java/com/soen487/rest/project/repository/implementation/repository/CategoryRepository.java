package com.soen487.rest.project.repository.implementation.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Category, Long>, JpaSpecificationExecutor {
    com.soen487.rest.project.repository.core.entity.Category findByName(String name);
    com.soen487.rest.project.repository.core.entity.Category saveAndFlush(com.soen487.rest.project.repository.core.entity.Category category);
    List<com.soen487.rest.project.repository.core.entity.Category> findAll(Specification specification);
    boolean existsByName(String name);
}
