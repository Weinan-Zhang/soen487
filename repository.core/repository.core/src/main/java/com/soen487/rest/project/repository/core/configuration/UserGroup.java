package com.soen487.rest.project.repository.core.configuration;


public enum UserGroup {
    ADMIN(0, "ADMIN"),
    USER(1, "USER");

    private int code;
    private String name;

    UserGroup(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
