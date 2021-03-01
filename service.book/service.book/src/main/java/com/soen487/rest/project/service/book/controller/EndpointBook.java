package com.soen487.rest.project.service.book.controller;


import com.google.gson.Gson;
import com.soen487.rest.project.repository.core.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EndpointBook {
    @Autowired
    private static int num_instace_cnt;
    @Autowired
    private RepositoryProxy repositoryProxy;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String viewWelcome(){
        return "index";
    }

    @RequestMapping(value="/addbook", method=RequestMethod.GET)
    public String viewAddBook(){
        return "addbook";
    }

    @RequestMapping(value="/book/add", method=RequestMethod.POST)
    public ModelAndView addBook(@RequestParam("title") String title,
                          @RequestParam("authors") String authors,
                          @RequestParam("publisher") String publisher,
                          @RequestParam("format") String format,
                          @RequestParam("language") String language,
                          @RequestParam("isbn") String isbn,
                          @RequestParam("description") String description,
                          @RequestParam("up_img") MultipartFile file) throws IOException {

        int test_case_cnt = this.num_instace_cnt++;
        com.soen487.rest.project.repository.core.entity.Seller seller =
                new com.soen487.rest.project.repository.core.entity.Seller("test_seller_" + test_case_cnt, "test_seller url_" + test_case_cnt);
        com.soen487.rest.project.repository.core.entity.Price currentPrice =
                new com.soen487.rest.project.repository.core.entity.Price(10.00, "test_price"+test_case_cnt);
        List<com.soen487.rest.project.repository.core.entity.Author> authorList = new ArrayList<>();
        if(authors.contains(",")){
            Arrays.asList(authors.split(",")).stream().forEach(s -> { authorList.add(new com.soen487.rest.project.repository.core.entity.Author(s, "test_lastname_"+test_case_cnt)); });
        }
        else {
            authorList.add(new com.soen487.rest.project.repository.core.entity.Author(authors, "test_lastname_"+test_case_cnt));
        }
        com.soen487.rest.project.repository.core.entity.Book book =
                new com.soen487.rest.project.repository.core.entity.Book(title, seller, currentPrice);
        book.setAuthors(authorList);
        book.setPublisher(publisher);
        book.setOriginalPrice(10.01);
        book.setLanguage(language);
        book.setIsbn13(isbn);
        book.setFormat(format);
        book.setDescription(description);
        if(file!=null){
            book.setCoverImg(file.getOriginalFilename());

            BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            byte[] bytes = file.getBytes();
            String base64String = encoder.encode(bytes).trim();
            String originalFilename = file.getOriginalFilename();

            this.repositoryProxy.uploadImg(base64String, originalFilename);
        }

        Gson gsonMaper = new Gson();
        String bookStr = gsonMaper.toJson(book);
        ResponseEntity response = this.repositoryProxy.addBook(bookStr);
        long bid = (long) response.getBody();
        return new ModelAndView("redirect:" + "/bookdetail/" + String.valueOf(bid));
    }

    @RequestMapping(value="listbook", method=RequestMethod.GET)
    public String listBook(ModelMap modelMap){
        ResponseEntity books = this.repositoryProxy.listBooks();
        modelMap.addAttribute("books", books.getBody());
        return "booklist";
    }

    @RequestMapping(value="bookdetail/{bid}", method=RequestMethod.GET)
    public String detailBook(@PathVariable("bid") long bid, ModelMap modelMap){
        ResponseEntity book = this.repositoryProxy.detailBook(bid);
        modelMap.addAttribute("book", book.getBody());
        return "bookdetail";
    }

    @RequestMapping(value="/modifybook/{bid}", method=RequestMethod.GET)
    public String viewModifyBook(@PathVariable("bid") long bid, ModelMap modelMap){
        ResponseEntity book = this.repositoryProxy.detailBook(bid);
        modelMap.addAttribute("book", book.getBody());
        com.soen487.rest.project.repository.core.entity.Book bookObj = (Book) book.getBody();
        String authors = bookObj.getAuthors().stream().map(author->author.toString()).collect(Collectors.joining(","));
        modelMap.addAttribute("authors", authors);
        return "modifybook";
    }

    @RequestMapping(value="/book/modify", method=RequestMethod.PUT)
    public ModelAndView modifyBook(@RequestParam("title") String title,
                          @RequestParam("authors") String authors,
                          @RequestParam("publisher") String publisher,
                          @RequestParam("format") String format,
                          @RequestParam("language") String language,
                          @RequestParam("isbn") String isbn,
                          @RequestParam("description") String description,
                          @RequestParam("bid") long bid,
                          @RequestParam("up_img") MultipartFile file) throws IOException {
        ResponseEntity persistedBookResponse = this.repositoryProxy.detailBook(bid);
        Book persistedBook = (com.soen487.rest.project.repository.core.entity.Book) persistedBookResponse.getBody();
        int test_case_cnt = this.num_instace_cnt++;
        List<com.soen487.rest.project.repository.core.entity.Author> authorList = new ArrayList<>();
        if(authors.contains(",")){
            Arrays.asList(authors.split(",")).stream().forEach(s -> { authorList.add(new com.soen487.rest.project.repository.core.entity.Author(s, "test_lastname_"+test_case_cnt)); });
        }
        else {
            authorList.add(new com.soen487.rest.project.repository.core.entity.Author(authors, "test_lastname_"+test_case_cnt));
        }
        persistedBook.setTitle(title);
        persistedBook.setAuthors(authorList);
        persistedBook.setPublisher(publisher);
        persistedBook.setLanguage(language);
        persistedBook.setIsbn13(isbn);
        persistedBook.setFormat(format);
        persistedBook.setDescription(description);
        if(file!=null){
            persistedBook.setCoverImg(file.getOriginalFilename());

            BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            byte[] bytes = file.getBytes();
            String base64String = encoder.encode(bytes).trim();
            String originalFilename = file.getOriginalFilename();

            this.repositoryProxy.uploadImg(base64String, originalFilename);
        }

        Gson gsonMaper = new Gson();
        String persistedBookStr = gsonMaper.toJson(persistedBook);
        this.repositoryProxy.updateBook(persistedBookStr, bid);

        return new ModelAndView("redirect:" + "/bookdetail/" + String.valueOf(bid));
    }

    @RequestMapping(value="deletebook", method=RequestMethod.DELETE)
    public ModelAndView deleteBook(@RequestParam("bid") long bid){
        this.repositoryProxy.deleteBook(bid);
        return new ModelAndView("redirect:/listbook");
    }

    @RequestMapping(value="signup", method=RequestMethod.GET)
    public String userSignup(Model model){
        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String userLogin(Model model){
        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }

    @RequestMapping(value="/categorylist/{categoryid}", method=RequestMethod.GET)
    public String listCategory(@PathVariable("categoryid") long categoryId, Model model){
        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }
}
