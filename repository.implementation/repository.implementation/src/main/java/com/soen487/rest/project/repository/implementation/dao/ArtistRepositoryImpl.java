package com.soen487.rest.project.repository.implementation.dao;

import com.soen487.rest.project.repository.core.entity.projection.ArtistDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ArtistRepositoryImpl implements ArtistRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    @Transactional(readOnly=true)
    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> findArtistsCustomized() {
        Query query = entityManager.createNativeQuery("SELECT ar.nickname, ar.firstname, ar.lastname FROM artist AS ar");
        List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> resArtistList = (List<ArtistDto>) query.getResultList();

        return resArtistList;
    }
}
