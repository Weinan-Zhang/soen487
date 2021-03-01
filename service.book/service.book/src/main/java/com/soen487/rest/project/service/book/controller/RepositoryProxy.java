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
    ResponseEntity<Long> addBook(@RequestBody String book);
    @PostMapping(value = "/img/uoload")
    String uploadImg(@RequestBody String base64String, @RequestParam String originalFileName) throws IOException;
    @RequestMapping("book/list")
    ResponseEntity<List<com.soen487.rest.project.repository.core.entity.Book>> listBooks();
    @RequestMapping("book/{bid}")
    ResponseEntity<com.soen487.rest.project.repository.core.entity.Book> detailBook(@PathVariable("bid") long bid);
    @PutMapping("book/update")
    ResponseEntity<Long> updateBook(@RequestBody String bookStr, @Valid @Pattern(regexp = "\\d+") @RequestParam("bid") long bid);
    @DeleteMapping("book/delete")
    ResponseEntity<Long> deleteBook(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid);
}
