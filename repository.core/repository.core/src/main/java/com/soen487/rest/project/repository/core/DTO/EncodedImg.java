package com.soen487.rest.project.repository.core.DTO;


import java.io.Serializable;


public class EncodedImg implements Serializable {
    String encodedString;
    String originalFilename;

    public EncodedImg(String encodedString, String originalFilename) {
        this.encodedString = encodedString;
        this.originalFilename = originalFilename;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
}
