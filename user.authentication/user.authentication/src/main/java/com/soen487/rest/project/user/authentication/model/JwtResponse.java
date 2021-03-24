package com.soen487.rest.project.user.authentication.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {
    private final String jwttoken;

    public String getJwttoken() {
        return jwttoken;
    }
}
