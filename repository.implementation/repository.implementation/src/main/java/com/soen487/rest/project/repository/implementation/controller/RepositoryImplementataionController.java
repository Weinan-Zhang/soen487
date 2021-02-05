package com.soen487.rest.project.repository.implementation.controller;

import com.soen487.rest.project.repository.implementation.dao.AlbumRepository;
import com.soen487.rest.project.repository.implementation.dao.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class RepositoryImplementataionController {
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artist/list")
    public List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> retrieveNamesOfAllArtists() {
        if(!this.artistRepository.existsAllBy()){
            return null;
        }
        return this.artistRepository.findAllBy();
    }

    @GetMapping("/artist/detail/{nickname}")
    public com.soen487.rest.project.repository.core.entity.Artist retrieveArtistDetail(@PathVariable String nickname) throws UnsupportedEncodingException {
        if(!this.artistRepository.existsByNickname(nickname)){
            return null;
        }
        return this.artistRepository.findByNickname(nickname);
    }

    @Transactional
    @PostMapping("/artist/add")
    public com.soen487.rest.project.repository.core.entity.Artist addArtist(@RequestBody com.soen487.rest.project.repository.core.entity.Artist artist){
        if(this.artistRepository.existsByNickname(artist.getNickname())){
            return null;
        }
        com.soen487.rest.project.repository.core.entity.Artist savedArtist = this.artistRepository.save(artist);
        return savedArtist;
    }

    @Transactional
    @PutMapping("/artist/update")
    public com.soen487.rest.project.repository.core.entity.Artist updateArtist(@RequestBody com.soen487.rest.project.repository.core.entity.Artist artist){
        if(!this.artistRepository.existsByNickname(artist.getNickname())){
            return null;
        }
        com.soen487.rest.project.repository.core.entity.Artist existingArtist = this.artistRepository.findByNickname(artist.getNickname());
        existingArtist.setFirstname(artist.getFirstname());
        existingArtist.setLastname(artist.getLastname());
        existingArtist.setBiography(artist.getBiography());
        this.artistRepository.save(existingArtist);
        return existingArtist;
    }

    @Transactional
    @DeleteMapping("artist/delete/{nickname}")
    public long deleteArtist(@PathVariable String nickname){
        long id = -1l;
        if(!this.artistRepository.existsByNickname(nickname)){
            return id;
        }
        com.soen487.rest.project.repository.core.entity.Artist artist = this.artistRepository.findByNickname(nickname);
        id = artist.getId();
        this.artistRepository.deleteById(id);
        return id;
    }

    @GetMapping("album/list")
    public List<com.soen487.rest.project.repository.core.entity.projection.AlbumDto> retrieveAllAlbums(){
        if(!this.albumRepository.existsAllBy()){
            return null;
        }
        return this.albumRepository.findAllBy();
    }

    @GetMapping("album/detail/{isrc}")
    public com.soen487.rest.project.repository.core.entity.Album retrieveAlbum(@PathVariable("isrc") String isrc){
        if(!this.albumRepository.existsByIsrc(isrc)){
            return null;
        }
        return this.albumRepository.findByIsrc(isrc);
    }

    @Transactional
    @PostMapping("album/add")
    public com.soen487.rest.project.repository.core.entity.Album addAlbum(@RequestBody com.soen487.rest.project.repository.core.entity.Album album){
        if(this.albumRepository.existsByIsrc(album.getISRC())){
            return null;
        }
        com.soen487.rest.project.repository.core.entity.Artist artist = artistRepository.findByNickname("unknown artist");
        if(album.getArtist()!=null){
            if(!artistRepository.existsByNickname(artist.getNickname())){
                artist = album.getArtist();
            }
        }

        long id = this.artistRepository.findAllByNickname(artist.getNickname()).getId();
        artist.setId(id);
        album.setArtist(artist);
        return this.albumRepository.save(album);
    }

    @Transactional
    @PutMapping("album/update")
    public com.soen487.rest.project.repository.core.entity.Album updateAlbum(@RequestBody com.soen487.rest.project.repository.core.entity.Album album){
        if(!this.albumRepository.existsByIsrc(album.getISRC())){
            return null;
        }
        com.soen487.rest.project.repository.core.entity.Album existingAlbum = this.albumRepository.findByIsrc(album.getISRC());

        if(album.getArtist()!=null){
            com.soen487.rest.project.repository.core.entity.Artist artist;
            if(!this.artistRepository.existsByNickname(album.getArtist().getNickname())){
                artist = this.artistRepository.save(album.getArtist());
            }
            else {
                artist = this.artistRepository.findAllByNickname(album.getArtist().getNickname());
            }
            existingAlbum.setArtist(artist);
        }
        else {
            existingAlbum.setArtist(null);
        }
        existingAlbum.setDescription(album.getDescription());
        existingAlbum.setReleased_year(album.getReleased_year());
        existingAlbum.setTitle(album.getTitle());
        this.albumRepository.save(existingAlbum);

        return existingAlbum;
    }

    @Transactional
    @DeleteMapping("album/delete/{isrc}")
    public long deleteAlbum(@PathVariable("isrc") String isrc){
        long id=-1l;
        if(!this.albumRepository.existsByIsrc(isrc)){
            return id;
        }
        com.soen487.rest.project.repository.core.entity.Album album = this.albumRepository.findByIsrc(isrc);
        id = album.getId();
        this.albumRepository.deleteById(id);
        return id;
    }
}
