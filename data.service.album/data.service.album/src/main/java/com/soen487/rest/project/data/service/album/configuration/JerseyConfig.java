package com.soen487.rest.project.data.service.album.configuration;

import com.soen487.rest.project.data.service.album.controller.AlbumEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(AlbumEndpoint.class);
    }
}
