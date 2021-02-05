package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<com.soen487.rest.project.repository.core.entity.Album, Long> {
    boolean existsAllBy();
    boolean existsByIsrc(String isrc);
    com.soen487.rest.project.repository.core.entity.Album findByIsrc(String isrc);
    List<com.soen487.rest.project.repository.core.entity.projection.AlbumDto> findAllBy();
    com.soen487.rest.project.repository.core.entity.Album save(com.soen487.rest.project.repository.core.entity.Album album);
    com.soen487.rest.project.repository.core.entity.Album deleteById(long id);
}
