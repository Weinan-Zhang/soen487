package com.soen487.rest.project.repository.core.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private String jwttoken;

    public String getJwttoken() {
        return jwttoken;
    }
}
