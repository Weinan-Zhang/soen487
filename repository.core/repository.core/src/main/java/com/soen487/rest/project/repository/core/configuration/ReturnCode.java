package com.soen487.rest.project.repository.core.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    SUCCESS(HttpStatus.OK.value(), "Success"),
    FAILURE(-1, "Failed due to unknown reason"),
    KEY_ARGS_NOT_PROVIDED(-2, "Key arguments not provided"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "The desired data are not found"),
    CAN_NOT_LOG_AS_DELETED(HttpStatus.NOT_ACCEPTABLE.value(), "Data has not been deleted and can not be logged as deleted"),
    CAN_NOT_LOG_AS_UPDATED(HttpStatus.NOT_ACCEPTABLE.value(), "Data has been deleted and can not be logged as updated"),
    FOUND(HttpStatus.ACCEPTED.value(), "Data required has been found"),
    CREATED(HttpStatus.CREATED.value(), "Data has been successfully created"),
    UPDATED(HttpStatus.CREATED.value(), "Data has been successfully updated"),
    DELETED(HttpStatus.ACCEPTED.value(), "Data has been successfully deleted"),
    USER_ALREADY_EXISTS(HttpStatus.NOT_ACCEPTABLE.value(), "The username already exists"),
    USER_DISABLED(-3, "USER_DISABLED"),
    INVALID_CREDENTIALS(-4, "INVALID_CREDENTIALS"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "unauthorized operation"),
    USER_CREATED_FAILURE(-5, "user creation failed.");

    private int code;
    private String message;
}
