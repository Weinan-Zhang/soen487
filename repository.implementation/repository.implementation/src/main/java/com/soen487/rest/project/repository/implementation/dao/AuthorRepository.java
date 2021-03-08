package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Author, Long> {
    com.soen487.rest.project.repository.core.entity.Author findByFirstnameAndLastnameAndMiddlename(String firstname,
                                                                                                   String lastname,
                                                                                                   String middlename);
    com.soen487.rest.project.repository.core.entity.Author saveAndFlush(com.soen487.rest.project.repository.core.entity.Author author);
    com.soen487.rest.project.repository.core.entity.Author findByAid(long aid);
    List<com.soen487.rest.project.repository.core.entity.Author> findAllByFirstnameIgnoreCaseContainingOrLastnameIgnoreCaseContainingOrMiddlenameIgnoreCaseContaining(String firstname, String lastname, String middlename);
}
