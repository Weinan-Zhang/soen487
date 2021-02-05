package com.soen487.rest.project.repository.implementation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository  extends CrudRepository<com.soen487.rest.project.repository.core.entity.Artist, Long>, ArtistRepositoryCustom {
//    @Query("SELECT ar.nickname, ar.firstname, ar.lastname FROM Artist AS ar")
//    public List<com.soen487.rest.project.repository.core.entity.Artist> getNicknameAndFirstnameAndLastname();
    com.soen487.rest.project.repository.core.entity.Artist deleteById(long id);
    List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> findAllBy();
    com.soen487.rest.project.repository.core.entity.Artist findByNickname(String nickname);
    com.soen487.rest.project.repository.core.entity.Artist findById(long id);
    com.soen487.rest.project.repository.core.entity.Artist findAllByNickname(String nickname);
    com.soen487.rest.project.repository.core.entity.Artist save(com.soen487.rest.project.repository.core.entity.Artist artist);
    boolean existsAllBy();
    boolean existsByNickname(String nickname);
    void deleteById(Long id);
}
