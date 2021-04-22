package com.soen487.rest.project.service.book.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@FeignClient("service.gateway")
@RibbonClient(name="service.gateway")
public interface GatewayProxy {
    @GetMapping("/repo/list/category")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Category>> getCategoryList();
    @PostMapping("/repo/book/add")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> addBook(@RequestBody String book);
    @PostMapping(value = "/repo//img/uoload")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<String> uploadImg(@RequestBody String encodedImgJson) throws IOException;
    @GetMapping("/repo/book/list")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Book>> listBooks();
    @GetMapping("/repo/book/{bid}")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Book> detailBook(@PathVariable("bid") long bid);
    @PutMapping("/repo/book/update")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> updateBook(@RequestBody String bookStr, @Valid @Pattern(regexp = "\\d+") @RequestParam("bid") long bid);
    @DeleteMapping("/repo/book/delete")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> deleteBook(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
    @GetMapping("/repo/author/{aid}")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Author> authorDetail(@PathVariable("aid") long aid);
    @PostMapping("/repo/book/search")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Book>> searchByTitleOrAuthorName(@RequestParam(name="keyword") String keyword);

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userRegister(@RequestBody com.soen487.rest.project.repository.core.entity.User user) throws Exception;
    @RequestMapping(value="/auth/login", method= RequestMethod.POST)
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.model.JwtResponse> userLogin(@RequestBody com.soen487.rest.project.repository.core.model.JwtRequest authenticationRequest) throws Exception;
    @RequestMapping(value="/auth/user/{username}", method=RequestMethod.GET)
    String getUserByName(@RequestHeader("Authorization") String jwt, @PathVariable("username") String username);
}
