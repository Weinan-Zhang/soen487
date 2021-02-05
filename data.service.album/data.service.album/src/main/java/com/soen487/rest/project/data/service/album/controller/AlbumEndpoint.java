package com.soen487.rest.project.data.service.album.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Component
@Path("/album")
public class AlbumEndpoint {
    @Autowired
    private RepositoryProxy repositoryProxy;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public Response listAlbum() {
        String albumListStr = "";
        List<com.soen487.rest.project.repository.core.entity.projection.AlbumDto> albums = this.repositoryProxy.retrieveAllAlbums();
        if(albums==null){
            return Response.
                    status(Response.Status.NOT_FOUND).
                    entity("no album in the repository.").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        for(com.soen487.rest.project.repository.core.entity.projection.AlbumDto album : albums){
            albumListStr += album.toString() + "\n";
        }
        return Response.
                status(Response.Status.OK).
                entity(albumListStr).
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @GET
    @Path("/detail")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDetailMissingIsrc(){
        return Response.
                status(Response.Status.BAD_REQUEST).
                entity("isrc part is missied.").
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @GET
    @Path("/detail/{isrc}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDetail(@PathParam("isrc") String isrc){
        if(isrc==null || isrc.equals("")){
            return Response.
                    status(Response.Status.BAD_REQUEST).
                    entity("isrc passed in is empty.").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        com.soen487.rest.project.repository.core.entity.Album album = this.repositoryProxy.retrieveAlbum(isrc);
        if(album==null){
            return Response.
                    status(Response.Status.NOT_FOUND).
                    entity("no album with isrc(" + isrc + ") found in the repository.").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        return Response.
                status(Response.Status.OK).
                entity(album.toString()).
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addAlbum(@FormParam("isrc") String isrc,
                           @FormParam("title") String title,
                           @FormParam("description") String description,
                           @FormParam("released_year") @Pattern(regexp = "\\d+") String released_year){
        if(isrc==null || title==null ||
           isrc.equals("") || title.equals("") ||
           description==null || released_year==null ||
           description.equals("") || released_year.equals("")){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("parameter(s) passed in is(are) not enough, isrc, title, description and released year must not be empty!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        com.soen487.rest.project.repository.core.entity.Album album =
                this.repositoryProxy.addAlbum(new com.soen487.rest.project.repository.core.entity.Album(isrc, title, description, Integer.parseInt(released_year)));
        if(album==null){
            return Response.status(Response.Status.CONFLICT).
                    entity("album persistence failed, album isrc conflicts!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        return Response.status(Response.Status.CREATED).
                entity(album.toString()).
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAlbumMissingIsrc(){
        return Response.
                status(Response.Status.BAD_REQUEST).
                entity("isrc part is missied.").
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @DELETE
    @Path("/delete/{isrc}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAlbum(@PathParam("isrc") String isrc){
        if(isrc==null || isrc.equals("")){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("isrc is required but not provided!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        long id = this.repositoryProxy.deleteAlbum(isrc);
        if(id==-1l){
            return Response.status(Response.Status.NOT_FOUND).
                    entity("artist delete failed, no album found in the repository!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        return Response.status(Response.Status.OK).
                entity("album with id " + id + " has been deleted!").
                type(MediaType.TEXT_PLAIN).
                build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAlbum(@FormParam("isrc") String isrc,
                              @FormParam("title") String title,
                              @FormParam("description") String description,
                              @FormParam("released_year") @Pattern(regexp="\\d+") String released_year,
                              @FormParam("nickname") String nickname,
                              @FormParam("firstname") String firstname,
                              @FormParam("lastname") String lastname,
                              @FormParam("biography") String biography){
        if(isrc==null || title==null || description==null || released_year==null ||
           isrc.equals("") || title.equals("") || description.equals("") || released_year.equals("")){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("parameter(s) passed in is(are) not enough, isrc, title, description and released year must not be empty!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        if(nickname==null && (firstname!=null || lastname!=null || biography!=null) ||
           nickname.equals("") && (!firstname.equals("") || !lastname.equals("") || !biography.equals(""))){
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("nickname of artist passed in can not be empty!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }

        com.soen487.rest.project.repository.core.entity.Album album =
                new com.soen487.rest.project.repository.core.entity.Album(isrc, title, description, Integer.parseInt(released_year));
        if((nickname!=null && firstname!=null && lastname!=null && biography!=null) ||
           (!nickname.equals("") && !firstname.equals("") && !lastname.equals("") && !biography.equals(""))){
            com.soen487.rest.project.repository.core.entity.Artist artist =
                    new com.soen487.rest.project.repository.core.entity.Artist(nickname, firstname, lastname, biography);
            album.setArtist(artist);
        }

        com.soen487.rest.project.repository.core.entity.Album album_updated = this.repositoryProxy.updateAlbum(album);
        if(album_updated==null){
            return Response.status(Response.Status.NOT_FOUND).
                    entity("album update failed, no album found in the repository!").
                    type(MediaType.TEXT_PLAIN).
                    build();
        }
        return Response.status(Response.Status.OK).
                entity("album updated successfully, here is the info of newly updated album:\n" + album_updated.toString()).
                type(MediaType.TEXT_PLAIN).
                build();
    }
}
