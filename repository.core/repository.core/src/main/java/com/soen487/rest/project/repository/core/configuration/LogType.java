package com.soen487.rest.project.repository.core.configuration;

public enum LogType {
    CREATE(0, "CREATE"),
    DELETE(1, "DELETE"),
    MODIFY(2, "MODIFY"),
    ALL(3, "ALL");

    private int code;
    private String type;

    LogType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
