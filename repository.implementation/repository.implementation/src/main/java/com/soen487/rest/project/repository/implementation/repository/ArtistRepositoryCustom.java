package com.soen487.rest.project.repository.implementation.repository;

import java.util.List;

public interface ArtistRepositoryCustom {
    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> findArtistsCustomized();
}
