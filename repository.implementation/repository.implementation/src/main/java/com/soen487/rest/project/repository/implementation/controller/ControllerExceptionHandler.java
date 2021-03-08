package com.soen487.rest.project.repository.implementation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(com.soen487.rest.project.repository.core.exception.ServiceException.class)
    @ResponseBody
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse handleServiceException(com.soen487.rest.project.repository.core.exception.ServiceException e){
        log.error(e.getMessage(), e);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.fail(com.soen487.rest.project.repository.core.configuration.ReturnCode.FAILURE);
    }
}
