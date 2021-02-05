package com.soen487.rest.project.album.client.library;

import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;

public class JerseyHttpClientLibrary {
    private Client client;

    private JerseyHttpClientLibrary(){}

    public static class Singleton { private static final JerseyHttpClientLibrary jerseyHttpClientLibrary = new JerseyHttpClientLibrary();}

    public static JerseyHttpClientLibrary getInstance(){ return Singleton.jerseyHttpClientLibrary; }

    private void setDefaultClient(){ this.client = ClientBuilder.newClient(); }

    private void setConfigClient(ClientConfig config){ this.client = ClientBuilder.newClient(config); }

    public void initConfigClient(ClientConfig config){
        if(config==null){
            this.setDefaultClient();
        }
        else {
            this.setConfigClient(config);
        }
    }

    public String httpGetList(String baseurl, String path){
        WebTarget webTarget = this.client.target(baseurl).path(path);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.get();
        return response.readEntity(String.class);
    }

    public String httpGetDetailByIsrc(String baseurl, String path_detail, String path_isrc){
        WebTarget webTarget = this.client.target(baseurl).path(path_detail).path(path_isrc);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.get();
        return response.readEntity(String.class);
    }


    public String httpPostAlbum(String baseurl, String path, HashMap<String, String> formParams){
        WebTarget webTarget = this.client.target(baseurl).path(path);
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("isrc", formParams.get("isrc"));
        formData.add("title", formParams.get("title"));
        formData.add("description", formParams.get("description"));
        formData.add("released_year", formParams.get("released_year"));
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.post(Entity.form(formData));
        return response.readEntity(String.class);
    }

    public String httpPutAlbum(String baseurl, String path, HashMap<String, String> formParams){
        WebTarget webTarget = this.client.target(baseurl).path(path);
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("isrc", formParams.get("isrc"));
        formData.add("title", formParams.get("title"));
        formData.add("description", formParams.get("description"));
        formData.add("released_year", formParams.get("released_year"));
        formData.add("nickname", formParams.get("nickname"));
        formData.add("firstname", formParams.get("firstname"));
        formData.add("lastname", formParams.get("lastname"));
        formData.add("biography", formParams.get("biography"));
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.put(Entity.form(formData));
        return response.readEntity(String.class);
    }

    public String httpDeleteAlbum(String baseurl, String path_delete, String path_isrc){
        WebTarget webTarget = this.client.target(baseurl).path(path_delete).path(path_isrc);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        Response response = invocationBuilder.delete();
        return response.readEntity(String.class);
    }
}
