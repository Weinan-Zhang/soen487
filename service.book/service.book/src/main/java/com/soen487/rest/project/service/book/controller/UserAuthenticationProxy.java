package com.soen487.rest.project.service.book.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@FeignClient("user.authentication")
@RibbonClient(name="user.authentication")
public interface UserAuthenticationProxy {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userRegister(@RequestBody com.soen487.rest.project.repository.core.entity.User user) throws Exception;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userLogin(@RequestBody com.soen487.rest.project.repository.core.model.JwtRequest authenticationRequest) throws Exception;

    @RequestMapping(value="/user/{username}", method=RequestMethod.GET)
    String getUserByName(@RequestHeader("Authorization") String jwt, @PathVariable("username") String username);
}
