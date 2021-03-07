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

//    @ExceptionHandler(BindException.class)
//    @ResponseBody
//    public AjaxResult handleBindException(BindException e)
//    {
//        log.error(e.getMessage(), e);
//        String message = e.getAllErrors().get(0).getDefaultMessage();
//        return ApiResponse.fail(message);
//    }
//
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public AjaxResult handleRuntimeException(RuntimeException e)
//    {
//        log.error(e.getMessage(), e);
//        return ApiResponse.fail(e.getMessage());
//    }
}
