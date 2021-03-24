package com.soen487.rest.project.repository.implementation.service;

import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import com.soen487.rest.project.repository.core.entity.User;
import com.soen487.rest.project.repository.implementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        if(this.userRepository.existsByUsername(user.getUsername())){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.USER_ALREADY_EXISTS);
        }
        User userSaved = this.userRepository.save(user);
        return userSaved;
    }

    @Override
    public User findUserByName(String username) {
        User user = this.userRepository.findByUsername(username);
        return user;
    }
}
