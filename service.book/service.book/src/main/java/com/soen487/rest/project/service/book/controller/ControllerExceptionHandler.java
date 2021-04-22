package com.soen487.rest.project.service.book.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(com.soen487.rest.project.repository.core.exception.ServiceException.class)
    public String handleServiceException(com.soen487.rest.project.repository.core.exception.ServiceException e){
        log.error(e.getMessage(), e);
//        String  info = e.getMessage();
//        String redirectJspPage = "error";
//        if(e.getCode()== HttpStatus.UNAUTHORIZED.value()){
//            redirectJspPage = "login";
//        }
//        return redirectJspPage;
        return "login";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e, Model model)
    {
        log.error(e.getMessage(), e);
        String  info = e.getMessage();
        String redirectJspPage = "error";
        model.addAttribute("info", info);
        return redirectJspPage;
    }
}
