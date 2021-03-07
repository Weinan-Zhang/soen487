package com.soen487.rest.project.service.book.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class EndpointBookWsFaultHandler {
    @ExceptionHandler(com.soen487.rest.project.repository.core.exception.ServiceException.class)
    public String handleSoapFault(Model model, com.soen487.rest.project.repository.core.exception.ServiceException e){
        String info = e.getMessage();
        model.addAttribute("info", info);
        return "error";
    }
}
