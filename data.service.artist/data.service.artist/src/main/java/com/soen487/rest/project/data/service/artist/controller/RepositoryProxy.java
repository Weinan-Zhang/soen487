package com.soen487.rest.project.data.service.artist.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("repository.implementation")
@RibbonClient(name="repository.implementation")
public interface RepositoryProxy {
    @GetMapping("/artist/list")
    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> retrieveNamesOfAllArtists();

    @GetMapping("/artist/detail/{nickname}")
    public com.soen487.rest.project.repository.core.entity.Artist retrieveArtistDetail(@PathVariable String nickname);

    @PostMapping("/artist/add")
    public com.soen487.rest.project.repository.core.entity.Artist addArtist(@RequestBody com.soen487.rest.project.repository.core.entity.Artist artist);

    @PutMapping("/artist/update")
    public com.soen487.rest.project.repository.core.entity.Artist updateArtist(@RequestBody com.soen487.rest.project.repository.core.entity.Artist artist);

    @DeleteMapping("artist/delete/{nickname}")
    public long deleteArtist(@PathVariable String nickname);
}
