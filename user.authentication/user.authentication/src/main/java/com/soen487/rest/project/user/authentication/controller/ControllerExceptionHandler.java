package com.soen487.rest.project.user.authentication.controller;

import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse handleServiceExcepion(com.soen487.rest.project.repository.core.exception.ServiceException e){
        log.error(e.getMessage(), e);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse handleExcepion(Exception e){
        log.error(e.getMessage(), e);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.fail(ReturnCode.FAILURE.getCode(), ReturnCode.FAILURE.getMessage());
    }
}
