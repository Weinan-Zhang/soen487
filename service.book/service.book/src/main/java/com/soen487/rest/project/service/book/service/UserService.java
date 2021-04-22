package com.soen487.rest.project.service.book.service;

public interface UserService {
    com.soen487.rest.project.repository.core.model.JwtResponse userRegister(com.soen487.rest.project.repository.core.entity.User user) throws Exception;
    com.soen487.rest.project.repository.core.model.JwtResponse userLogin(String username, String password) throws Exception;
}
