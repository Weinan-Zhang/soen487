package com.soen487.rest.project.repository.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.User, Long> {
    com.soen487.rest.project.repository.core.entity.User findByUsername(String username);
    boolean existsByUsername(String username);
    com.soen487.rest.project.repository.core.entity.User save(com.soen487.rest.project.repository.core.entity.User user);
}
