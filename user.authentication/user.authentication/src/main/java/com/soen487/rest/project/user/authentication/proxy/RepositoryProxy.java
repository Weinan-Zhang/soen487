package com.soen487.rest.project.user.authentication.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("repository.implementation")
@RibbonClient(name="repository.implementation")
public interface RepositoryProxy {
    @GetMapping("user/get/{username}")
    @ResponseBody
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.User> getUser(@PathVariable(name="username") String username);

    @PostMapping("user/add")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.User> addUser(@RequestBody com.soen487.rest.project.repository.core.entity.User user);
}
