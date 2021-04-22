package com.soen487.rest.project.user.authentication.service;

import com.soen487.rest.project.user.authentication.proxy.RepositoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    RepositoryProxy repositoryProxy;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.User> userLoadResponse = this.repositoryProxy.getUser(username);
        if(userLoadResponse.getCode()<200 || userLoadResponse.getCode()>299) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(userLoadResponse.getCode(), userLoadResponse.getMessage());
        }
        return new User(userLoadResponse.getPayload().getUsername(), userLoadResponse.getPayload().getPassword(), new ArrayList<>());
    }

    public com.soen487.rest.project.repository.core.entity.User addUser(com.soen487.rest.project.repository.core.entity.User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.User> userAddResponse = this.repositoryProxy.addUser(user);
        if(userAddResponse.getCode()<200 || userAddResponse.getCode()>299) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(userAddResponse.getCode(), userAddResponse.getMessage());
        }
        return userAddResponse.getPayload();
    }
}
