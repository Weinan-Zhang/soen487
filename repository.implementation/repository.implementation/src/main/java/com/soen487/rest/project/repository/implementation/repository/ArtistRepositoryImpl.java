package com.soen487.rest.project.repository.implementation.repository;

import com.soen487.rest.project.repository.core.entity.projection.ArtistDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class ArtistRepositoryImpl {

    EntityManager entityManager;

    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> findArtistsCustomized() {
        Query query = entityManager.createNativeQuery("SELECT ar.nickname, ar.firstname, ar.lastname FROM artist AS ar");
        List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> resArtistList = (List<ArtistDto>) query.getResultList();

        return resArtistList;
    }
}
