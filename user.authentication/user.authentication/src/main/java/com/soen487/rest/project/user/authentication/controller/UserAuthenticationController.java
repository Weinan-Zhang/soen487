package com.soen487.rest.project.user.authentication.controller;


import com.google.gson.Gson;
import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import com.soen487.rest.project.user.authentication.service.JwtUserDetailsService;
import com.soen487.rest.project.user.authentication.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailService;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userLogin(@RequestBody com.soen487.rest.project.repository.core.model.JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, new com.soen487.rest.project.repository.core.model.JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userRegister(@RequestBody com.soen487.rest.project.repository.core.entity.User user) throws Exception {
        com.soen487.rest.project.repository.core.entity.User userCreated = this.userDetailService.addUser(user);
        if(userCreated==null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.USER_CREATED_FAILURE);
        }
        final UserDetails userDetails = this.userDetailService.loadUserByUsername(userCreated.getUsername());
        final String token = this.jwtTokenUtil.generateToken(userDetails);

        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, new com.soen487.rest.project.repository.core.model.JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.USER_DISABLED);
        }
        catch (BadCredentialsException e) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.INVALID_CREDENTIALS);
        }
    }

    @RequestMapping(value="/user/{username}", method=RequestMethod.GET)
    public String getUserByName(@RequestHeader("Authorization") String jwt, @PathVariable("username") String username) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if(userDetails == null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<User> serviceResponseUserFound = com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, userDetails);
        Gson gsonMapper = new Gson();
        String serviceResponseUserStr = gsonMapper.toJson(serviceResponseUserFound);
        return serviceResponseUserStr;
    }
}
