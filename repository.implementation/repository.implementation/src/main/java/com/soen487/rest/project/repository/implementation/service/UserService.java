package com.soen487.rest.project.repository.implementation.service;


public interface UserService {
    com.soen487.rest.project.repository.core.entity.User addUser(com.soen487.rest.project.repository.core.entity.User user);

    com.soen487.rest.project.repository.core.entity.User findUserByName(String username);
}
