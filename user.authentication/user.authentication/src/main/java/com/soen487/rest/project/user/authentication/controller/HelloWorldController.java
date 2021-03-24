package com.soen487.rest.project.user.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping(value="hello", method= RequestMethod.GET)
    public String helloWorld(){
        return "Hello world";
    }
}
