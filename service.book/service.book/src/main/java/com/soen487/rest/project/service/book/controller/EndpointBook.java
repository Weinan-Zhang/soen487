package com.soen487.rest.project.service.book.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import com.soen487.rest.project.repository.core.entity.Book;
import com.soen487.rest.project.repository.core.entity.Category;
import com.soen487.rest.project.service.book.model.ws.BookChangeLogDto;
import com.soen487.rest.project.service.book.model.ws.BookChangeLogResponse;
import com.soen487.rest.project.service.book.model.ws.GetLogResponse;
import com.soen487.rest.project.service.book.model.ws.LogType;
import com.soen487.rest.project.service.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EndpointBook {
    private static int num_instace_cnt;
    @Autowired
    private RepositoryProxy repositoryProxy;
    @Autowired
    private BookChangLogClient bookChangLogClient;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String viewWelcome(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());
        return "index";
    }

    @RequestMapping(value="/addbook", method=RequestMethod.GET)
    public String viewAddBook(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());
        return "addbook";
    }

    @RequestMapping(value="/book/add", method=RequestMethod.POST)
    public ModelAndView addBook(@RequestParam("title") String title,
                          @RequestParam("category") String category,
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
        com.soen487.rest.project.repository.core.entity.Category bookCategory = new Category(category, 1);
        bookCategory.setCid(2);
        book.setCategory( bookCategory);
        book.setAuthors(authorList);
        book.setPublisher(publisher);
        book.setOriginalPrice(10.01);
        book.setLanguage(language);
        book.setIsbn13(isbn);
        book.setFormat(format);
        book.setDescription(description);
        if(file!=null && !file.isEmpty()){
            book.setCoverImg(file.getOriginalFilename());
        }

        Gson gsonMaper = new Gson();
        String bookStr = gsonMaper.toJson(book);
        com.soen487.rest.project.repository.core.configuration.ServiceResponse response = this.repositoryProxy.addBook(bookStr);
        if(response.getCode()<200 || response.getCode()>299){
            String info = response.getMessage();
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }
        long bid = (long) response.getPayload();

        if(book.getCoverImg()!=null && !"".equals(book.getCoverImg())){
            BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            byte[] bytes = file.getBytes();
            String base64String = encoder.encode(bytes).trim();
            String originalFilename = file.getOriginalFilename();
            Gson gsonMapper = new Gson();
            response =
                    this.repositoryProxy.uploadImg(gsonMapper.toJson(new com.soen487.rest.project.repository.core.DTO.EncodedImg(base64String, originalFilename)));

            if(response.getCode()<200 || response.getCode()>299){
                String info = response.getMessage();
                ModelAndView model = new ModelAndView("error");
                ModelMap modelMap = new ModelMap();
                modelMap.addAttribute("info", info);
                model.addAllObjects(modelMap);
                return  model;
            }
        }

        BookChangeLogResponse soapResponse = this.bookChangLogClient.bookChangeLog(LogType.CREATE, bid);
        if(soapResponse.getLid()==-1){
            String info = "error occured during book log insertion!";
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }

        return new ModelAndView("redirect:" + "/bookdetail/" + String.valueOf(bid));
    }

    @RequestMapping(value="listbook", method=RequestMethod.GET)
    public String listBook(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<Book>> serviceResponse =
                this.repositoryProxy.listBooks();
        if(serviceResponse.getCode()<200 || serviceResponse.getCode()>299){
            String info = serviceResponse.getMessage();
            model.addAttribute("info", info);
            return "error";
        }
        List<Book> booksMap = serviceResponse.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = mapper.convertValue(booksMap, new TypeReference<List<Book>>() { });

        model.addAttribute("books", books);
        return "booklist";
    }

    @RequestMapping(value="bookdetail/{bid}", method=RequestMethod.GET)
    public String detailBook(@PathVariable("bid") long bid, Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Book> serviceResponse = this.repositoryProxy.detailBook(bid);
        if(serviceResponse.getCode()<200 || serviceResponse.getCode()>299){
            String info = serviceResponse.getMessage();
            model.addAttribute("info", info);
            return "error";
        }
        Book bookMap = serviceResponse.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(bookMap, new TypeReference<Book>() { });
        model.addAttribute("book", book);
        return "bookdetail";
    }

    @RequestMapping(value="/modifybook/{bid}", method=RequestMethod.GET)
    public String viewModifyBook(@PathVariable("bid") long bid, Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Book> serviceResponse = this.repositoryProxy.detailBook(bid);
        if(serviceResponse.getCode()<200 || serviceResponse.getCode()>299){
            String info = serviceResponse.getMessage();
            model.addAttribute("info", info);
            return "error";
        }

        Book bookMap = serviceResponse.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.convertValue(bookMap, new TypeReference<Book>() { });
        String authors = book.getAuthors().stream().map(author->author.toString()).collect(Collectors.joining(","));

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
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
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Book> persistedBookResponse =
                this.repositoryProxy.detailBook(bid);
        if(persistedBookResponse.getCode()<200 || persistedBookResponse.getCode()>299){
            String info = persistedBookResponse.getMessage();
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }
        Book persistedBook = persistedBookResponse.getPayload();
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
            Gson gsonMapper = new Gson();
            com.soen487.rest.project.repository.core.configuration.ServiceResponse<String> imgUpResponse = this.repositoryProxy.uploadImg(gsonMapper.toJson(new com.soen487.rest.project.repository.core.DTO.EncodedImg(base64String, originalFilename)));

            if(imgUpResponse.getCode()<200 || imgUpResponse.getCode()>299){
                String info = persistedBookResponse.getMessage();
                ModelAndView model = new ModelAndView("error");
                ModelMap modelMap = new ModelMap();
                modelMap.addAttribute("info", info);
                model.addAllObjects(modelMap);
                return  model;
            }
        }

        Gson gsonMaper = new Gson();
        String persistedBookStr = gsonMaper.toJson(persistedBook);
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> response =
                this.repositoryProxy.updateBook(persistedBookStr, bid);

        if(response.getCode()<200 || response.getCode()>299){
            String info = response.getMessage();
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }

        BookChangeLogResponse soapResponse = this.bookChangLogClient.bookChangeLog(LogType.MODIFY, bid);
        if(soapResponse.getLid()==-1){
            String info = "error occured during book log update!";
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }
        return new ModelAndView("redirect:" + "/bookdetail/" + String.valueOf(bid));
    }

    @RequestMapping(value="deletebook", method=RequestMethod.DELETE)
    public ModelAndView deleteBook(@RequestParam("bid") long bid){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> response = this.repositoryProxy.deleteBook(bid);
        if(response.getCode()<200 || response.getCode()>299){
            String info = response.getMessage();
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }
        BookChangeLogResponse soapResponse = this.bookChangLogClient.bookChangeLog(LogType.DELETE, bid);
        if(soapResponse.getLid()==-1){
            String info = "error occured during book log deletion!";
            ModelAndView model = new ModelAndView("error");
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute("info", info);
            model.addAllObjects(modelMap);
            return  model;
        }
        return new ModelAndView("redirect:/listbook");
    }

    @RequestMapping(value="listbook/author/{aid}", method=RequestMethod.GET)
    public String listBookByAuthor(Model model, @PathVariable(name="aid") long aid){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Author> response = this.repositoryProxy.authorDetail(aid);
        if(response.getCode()<200 || response.getCode()>299) {
            String info = response.getMessage();
            model.addAttribute("info", info);
            return "error";
        }
        com.soen487.rest.project.repository.core.entity.Author authorMap = response.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        com.soen487.rest.project.repository.core.entity.Author author = mapper.convertValue(authorMap, new TypeReference<com.soen487.rest.project.repository.core.entity.Author>() { });
        model.addAttribute("author", author);
        return "booklistbyauthor";
    }

    @RequestMapping(value="signup", method=RequestMethod.GET)
    public String userSignup(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String userLogin(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }

    @RequestMapping(value="/list/category/{categoryid}", method=RequestMethod.GET)
    public String listCategory(@PathVariable("categoryid") long categoryId, Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        String info = "We are working hard on this functionality, be patient!";
        model.addAttribute("info", info);
        return "error";
    }

    @RequestMapping(value="/listlog", method=RequestMethod.GET)
    public String viewLogs(Model model){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        GetLogResponse response = this.bookChangLogClient.getLog(null,null,null);

        if(response.getLogDtos()==null || response.getLogDtos().size()==0){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        List<BookChangeLogDto> logDtos = response.getLogDtos();
        model.addAttribute("logs", logDtos);
        return "loglist";
    }

    @RequestMapping(value="/queryloglist", method=RequestMethod.POST)
    public String queryLogs(Model model,
                          @RequestParam(name="stime", required=false) String stimeStr,
                          @RequestParam(name="etime", required=false) String etimeStr,
                          @RequestParam(name="logType", required=false) String logTypeStr) throws DatatypeConfigurationException {
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        XMLGregorianCalendar stimeVar = stimeStr.equals("") ? null : com.soen487.rest.project.repository.core.util.CommonUtils.formatXMLGregorianCalendar(stimeStr);
        XMLGregorianCalendar etimeVar = etimeStr.equals("") ? null : com.soen487.rest.project.repository.core.util.CommonUtils.formatXMLGregorianCalendar(etimeStr);
        LogType logType = logTypeStr.equals("") ? LogType.valueOf("ALL") : LogType.valueOf(logTypeStr);
        GetLogResponse response = this.bookChangLogClient.getLog(stimeVar, etimeVar, logType);

        if(response.getLogDtos()==null || response.getLogDtos().size()==0){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        List<BookChangeLogDto> logDtos = response.getLogDtos();
        model.addAttribute("logs", logDtos);
        return "loglist";
    }

    @RequestMapping(value="/book/search", method=RequestMethod.POST)
    public String queryLogs(Model model,
                            @RequestParam(name="keyword", required=false) String keyword){
        com.soen487.rest.project.repository.core.configuration.ServiceResponse categoryResponse = this.categoryService.retriveCategories();
        model.addAttribute("categories", categoryResponse.getPayload());

        com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<Book>> response =
                this.repositoryProxy.searchByTitleOrAuthorName(keyword);
        if(response.getCode()<200 || response.getCode()>299) {
            String info = response.getMessage();
            model.addAttribute("info", info);
            return "error";
        }

        List<Book> booksMap = response.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = mapper.convertValue(booksMap, new TypeReference<List<Book>>() { });

        model.addAttribute("books", books);
        return "booklist";
    }

    @RequestMapping("/login")
    public String userLogin(){
        return "hello user";
    }
}
