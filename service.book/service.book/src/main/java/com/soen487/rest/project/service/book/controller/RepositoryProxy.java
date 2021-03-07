package com.soen487.rest.project.service.book.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@FeignClient("repository.implementation")
@RibbonClient(name="repository.implementation")
public interface RepositoryProxy {
    @PostMapping("book/add")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> addBook(@RequestBody String book);
    @PostMapping(value = "/img/uoload")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<String> uploadImg(@RequestBody String encodedImgJson) throws IOException;
    @GetMapping("book/list")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Book>> listBooks();
    @GetMapping("book/{bid}")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Book> detailBook(@PathVariable("bid") long bid);
    @PutMapping("book/update")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> updateBook(@RequestBody String bookStr, @Valid @Pattern(regexp = "\\d+") @RequestParam("bid") long bid);
    @DeleteMapping("book/delete")
    com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> deleteBook(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
}
