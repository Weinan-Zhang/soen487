package com.soen487.rest.project.service.book.service;

import com.soen487.rest.project.repository.core.entity.User;
import com.soen487.rest.project.repository.core.model.JwtRequest;
import com.soen487.rest.project.service.book.controller.GatewayProxy;
import com.soen487.rest.project.service.book.controller.RepositoryProxy;
import com.soen487.rest.project.service.book.controller.UserAuthenticationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserAuthenticationProxy userAuthenticationProxy;
    @Autowired
    GatewayProxy gatewayProxy;

    @Override
    public com.soen487.rest.project.repository.core.model.JwtResponse userRegister(User user) throws Exception {
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> jwtResponse = this.gatewayProxy.userRegister(user);
        if(jwtResponse.getCode() < 200 || jwtResponse.getCode() >299) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(jwtResponse.getCode(), jwtResponse.getMessage());
        }
        return jwtResponse.getPayload();
    }

    @Override
    public com.soen487.rest.project.repository.core.model.JwtResponse userLogin(String username, String password) throws Exception {
        com.soen487.rest.project.repository.core.model.JwtRequest jwtRequest = new JwtRequest(username, password);
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> jwtResponse = this.gatewayProxy.userLogin(jwtRequest);
        if(jwtResponse.getCode() < 200 || jwtResponse.getCode() >299) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(jwtResponse.getCode(), jwtResponse.getMessage());
        }
        return jwtResponse.getPayload();
    }
}
