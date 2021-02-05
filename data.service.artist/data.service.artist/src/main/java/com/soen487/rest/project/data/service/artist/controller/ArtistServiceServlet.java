package com.soen487.rest.project.data.service.artist.controller;

import com.soen487.rest.project.data.service.artist.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class ArtistServiceServlet extends HttpServlet {
    @Autowired
    private RepositoryProxy repositoryProxy;

    private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        this.springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameterMap().containsKey("target") && request.getParameter("target").equals("artist") &&
           request.getParameterMap().containsKey("operation") && request.getParameter("operation").equals("list")){
            String artistListResponse = "";
            List<com.soen487.rest.project.repository.core.entity.projection.ArtistDto> artists = this.repositoryProxy.retrieveNamesOfAllArtists();
            if(artists==null){
                ServletUtils.output(response, HttpServletResponse.SC_NOT_FOUND, "no artists found!");
                return;
            }
            for(com.soen487.rest.project.repository.core.entity.projection.ArtistDto artist : artists){
                artistListResponse += artist.toString() + "\n";
            }
            ServletUtils.output(response, HttpServletResponse.SC_OK, artistListResponse);
        }
        else if(request.getParameterMap().containsKey("target") && request.getParameter("target").equals("artist") &&
                request.getParameterMap().containsKey("operation") && request.getParameter("operation").equals("detail") &&
                request.getParameterMap().containsKey("nickname") && !request.getParameter("nickname").equals("")){
                com.soen487.rest.project.repository.core.entity.Artist artist =
                        this.repositoryProxy.retrieveArtistDetail(request.getParameter("nickname"));
                if(artist==null){
                    ServletUtils.output(response, HttpServletResponse.SC_NOT_FOUND,"no artist found!");
                    return;
                }
                ServletUtils.output(response, HttpServletResponse.SC_OK, artist.toString());
        }
        else {
            ServletUtils.output(response, HttpServletResponse.SC_BAD_REQUEST,"invalid operation");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameterMap().containsKey("target") && request.getParameter("target").equals("artist") &&
                request.getParameterMap().containsKey("operation") && request.getParameter("operation").equals("add") &&
                request.getParameterMap().containsKey("nickname") && !request.getParameter("nickname").equals("")){
            String nickname = request.getParameter("nickname");
            String firstname = request.getParameterMap().containsKey("firstname") ? request.getParameter("firstname") : "";
            String lastname = request.getParameterMap().containsKey("lastname") ? request.getParameter("lastname") : "";
            String biography = request.getParameterMap().containsKey("biography") ? request.getParameter("biography") : "";


            com.soen487.rest.project.repository.core.entity.Artist artist =
                    this.repositoryProxy.addArtist(new com.soen487.rest.project.repository.core.entity.Artist(nickname, firstname, lastname, biography));
            if(artist==null){
                ServletUtils.output(response, HttpServletResponse.SC_CONFLICT, "artist creation failed, the artist's nickname might already exists.");
                return;
            }
            ServletUtils.output(response, HttpServletResponse.SC_CREATED, artist.toString());
        }
        else {
            ServletUtils.output(response, HttpServletResponse.SC_BAD_REQUEST,"invalid operation");
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameterMap().containsKey("target") && request.getParameter("target").equals("artist") &&
                request.getParameterMap().containsKey("operation") && request.getParameter("operation").equals("update") &&
                request.getParameterMap().containsKey("nickname") && !request.getParameter("nickname").equals("")){
            String nickname = request.getParameter("nickname");
            String firstname = request.getParameterMap().containsKey("firstname") ? request.getParameter("firstname") : "";
            String lastname = request.getParameterMap().containsKey("lastname") ? request.getParameter("lastname") : "";
            String biography = request.getParameterMap().containsKey("biography") ? request.getParameter("biography") : "";

            com.soen487.rest.project.repository.core.entity.Artist artist =
                    this.repositoryProxy.updateArtist(new com.soen487.rest.project.repository.core.entity.Artist(nickname, firstname, lastname, biography));
            if(artist==null){
                ServletUtils.output(response, HttpServletResponse.SC_NOT_FOUND, "artist update failed, no artist found in the repository.");
                return;
            }
            ServletUtils.output(response, HttpServletResponse.SC_CREATED, artist.toString());
        }
        else {
            ServletUtils.output(response, HttpServletResponse.SC_BAD_REQUEST ,"invalid operation");
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameterMap().containsKey("target") && request.getParameter("target").equals("artist") &&
                request.getParameterMap().containsKey("operation") && request.getParameter("operation").equals("delete") &&
                request.getParameterMap().containsKey("nickname") && !request.getParameter("nickname").equals("")){
            long id = this.repositoryProxy.deleteArtist(request.getParameter("nickname"));
            if(id==-1l) {
                ServletUtils.output(response, HttpServletResponse.SC_NOT_FOUND ,"artist delete failed, no artist found in the repository.");
                return;
            }
            ServletUtils.output(response, HttpServletResponse.SC_OK ,"record with id (" + id + ") has been deleted.");
        }
        else {
            ServletUtils.output(response, HttpServletResponse.SC_BAD_REQUEST ,"invalid operation");
        }
    }
}
