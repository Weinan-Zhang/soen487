package com.soen487.rest.project.repository.core.configuration;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Builder
@Slf4j
public class ServiceResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T payload;

    public static <T> ServiceResponse success(T payload) {
        ServiceResponse response = ServiceResponse.builder().
                code(ReturnCode.SUCCESS.getCode()).
                message(ReturnCode.SUCCESS.getMessage()).
                payload(payload).build();
        log.info("Success API Response: {}", response.toString());
        return response;
    }

    public static<T> ServiceResponse success(com.soen487.rest.project.repository.core.configuration.ReturnCode returnCode, T payload) {
        ServiceResponse response = ServiceResponse.builder().
                code(returnCode.getCode()).
                message(returnCode.getMessage()).
                payload(payload).build();
        log.info("Success API Response: {}", response.toString());
        return response;
    }

    public static <T> ServiceResponse success(){
        return success(null);
    }

    public static <T> ServiceResponse fail(int code, String message){
        ServiceResponse response = ServiceResponse.builder().code(code).message(message).build();
        log.error("Failed API Response: {}", response.toString());
        return response;
    }

    public static <T> ServiceResponse fail(com.soen487.rest.project.repository.core.configuration.ReturnCode returnCode){
        ServiceResponse response = ServiceResponse.builder().code(returnCode.getCode()).message(returnCode.getMessage()).build();
        log.error("Failed API Response: {}", response.toString());
        return response;
    }

    public static <T> ServiceResponse fail(){
        return fail(ReturnCode.FAILURE);
    }

    public static <T> ServiceResponse fail(String message){
        return fail(ReturnCode.FAILURE.getCode(), message);
    }

    public int getCode() { return code; }

    public String getMessage() { return message; }

    public T getPayload() { return payload; }
}
