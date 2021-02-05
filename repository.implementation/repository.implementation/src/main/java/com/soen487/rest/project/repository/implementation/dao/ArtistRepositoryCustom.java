package com.soen487.rest.project.repository.implementation.dao;

import java.util.List;

public interface ArtistRepositoryCustom {
    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> findArtistsCustomized();
}
