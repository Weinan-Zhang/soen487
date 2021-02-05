package com.soen487.rest.project.data.service.album.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("repository.implementation")
@RibbonClient(name="repository.implementation")
public interface RepositoryProxy {
    @GetMapping("album/list")
    List<com.soen487.rest.project.repository.core.entity.projection.AlbumDto> retrieveAllAlbums();

    @GetMapping("album/detail/{isrc}")
    com.soen487.rest.project.repository.core.entity.Album retrieveAlbum(@PathVariable("isrc") String isrc);

    @PostMapping("album/add")
    com.soen487.rest.project.repository.core.entity.Album addAlbum(@RequestBody com.soen487.rest.project.repository.core.entity.Album album);

    @PutMapping("album/update")
    com.soen487.rest.project.repository.core.entity.Album updateAlbum(@RequestBody com.soen487.rest.project.repository.core.entity.Album album);

    @DeleteMapping("album/delete/{isrc}")
    long deleteAlbum(@PathVariable("isrc") String isrc);
}
