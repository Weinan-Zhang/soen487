package com.soen487.rest.project.repository.implementation.controller;

import com.google.gson.Gson;
import com.soen487.rest.project.repository.core.configuration.LogType;
import com.soen487.rest.project.repository.core.configuration.ReturnCode;
import com.soen487.rest.project.repository.core.configuration.ServiceResponse;
import com.soen487.rest.project.repository.core.entity.Author;
import com.soen487.rest.project.repository.core.entity.BookChangeLog;
import com.soen487.rest.project.repository.core.entity.PriceHistory;
import com.soen487.rest.project.repository.implementation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import javax.imageio.ImageIO;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RepositoryImplementataionController {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    PriceHistoryRepository priceHistoryRepository;
    @Autowired
    BookChangeLogRepository bookChangeLogRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    @PostMapping("book/add")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> addBook(@RequestBody String bookStr){
        final String NO_COVER_IMG = "nocover.jpg";

        Gson gsonMapper = new Gson();
        com.soen487.rest.project.repository.core.entity.Book book = gsonMapper.fromJson(bookStr, com.soen487.rest.project.repository.core.entity.Book.class);
        if(book.getIsbn13()==null || "".equals(book.getIsbn13())){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }
        if(book.getSeller()==null || ("".equals(book.getSeller().getName()))){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }
        if(book.getCategory()==null || ("".equals(book.getCategory()))){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }

        // 插入/更新图书表、作者表及图书-作者中间表
        com.soen487.rest.project.repository.core.entity.Book bookPersisted;

        if(!this.bookRepository.existsByIsbn13(book.getIsbn13())){
            if(book.getAuthors().size()>0){
                ListIterator it = book.getAuthors().listIterator();
                while(it.hasNext()){
                    com.soen487.rest.project.repository.core.entity.Author author = (Author) it.next();
                    Author authorPersisted = this.authorRepository.
                            findByFirstnameAndLastnameAndMiddlename(
                                    author.getFirstname(),
                                    author.getLastname(),
                                    author.getMiddlename());
                    if(authorPersisted==null){
                        authorPersisted = this.authorRepository.saveAndFlush(author);
                    }
                    it.set(authorPersisted);
                }
            }
            if(book.getCoverImg()==null || "".equals(book.getCoverImg()))
                book.setCoverImg(NO_COVER_IMG);

            com.soen487.rest.project.repository.core.entity.Category category = book.getCategory();
            long cid = this.categoryRepository.findByName(category.getName()).getCid();
            category.setCid(cid);

            bookPersisted = this.bookRepository.saveAndFlush(book);
        }
        else {
            // 如果图书已经存在，获取图书对象
            bookPersisted = this.bookRepository.findByIsbn13(book.getIsbn13());
        }

        // 更新书商表
        com.soen487.rest.project.repository.core.entity.Seller sellerPersisted =
                this.sellerRepository.findByName(book.getSeller().getName());
        if(sellerPersisted==null){
            sellerPersisted = this.sellerRepository.saveAndFlush(book.getSeller());
        }


        // 更新价格表
        com.soen487.rest.project.repository.core.entity.Price pricePersisted =
                this.priceRepository.findByBookAndSeller(bookPersisted, sellerPersisted);

        if(pricePersisted==null){
            // 如果当前图书的当前价格（非原始价格）的book和seller变量未空，添加当前图书与当前书商，以免价格记录中bid和sid为空。
            if(book.getCurrentPrice().getBook()==null){
                book.getCurrentPrice().setBook(bookPersisted);
            }
            if(book.getCurrentPrice().getSeller()==null){
                book.getCurrentPrice().setSeller(sellerPersisted);
            }
            pricePersisted = this.priceRepository.save(book.getCurrentPrice());
        }
        else {
            //如果当前价格与历史价格不同，更新价格历史表
            if(pricePersisted.getPrice()!=book.getCurrentPrice().getPrice()){
                PriceHistory priceHistory = new PriceHistory(
                        new Timestamp(System.currentTimeMillis()),
                        pricePersisted.getPrice());
                priceHistory.setPrice(pricePersisted);
                this.priceHistoryRepository.save(priceHistory);

                pricePersisted.setPrice(book.getCurrentPrice().getPrice());
            }
            pricePersisted.setAfflicateUrl(book.getCurrentPrice().getAfflicateUrl());
            pricePersisted.setPurchaseUrl(book.getCurrentPrice().getPurchaseUrl());
        }
        this.priceRepository.save(pricePersisted);

        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(
                ReturnCode.CREATED, bookPersisted.getBid());
    }

    /*
    *    上传图书封面图片
    *
    *
    */
    @PostMapping(value = "/img/uoload")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<String>
    uploadImg(@RequestBody String encodedImgJson) throws IOException {
        Gson gsonMapper = new Gson();
        com.soen487.rest.project.repository.core.DTO.EncodedImg encodedImg =
                gsonMapper.fromJson(encodedImgJson, com.soen487.rest.project.repository.core.DTO.EncodedImg.class);
        if((encodedImg.getEncodedString()==null|| encodedImg.getEncodedString().equals("")) || (encodedImg.getOriginalFilename()==null|| encodedImg.getOriginalFilename().equals(""))){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }

        // 将图片字符串流解码
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(encodedImg.getEncodedString());
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
        BufferedImage bi1 = ImageIO.read(bais);

        //获取项目classes/static的地址
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        //定义上传的文件名
        String coverName = "cover_" + encodedImg.getOriginalFilename();
        //定义封面本地存储的目录和文件名
        String coverFullName = "covers" + File.separator + coverName;
        //定义封面本地存储的完整路径（包括文件名）
        String coverFullPath = staticPath + File.separator + coverFullName;
        // 访问路径=静态资源路径+文件目录路径
        String visitPath = "static" + File.separator + coverFullName;

        File saveFile = new File(coverFullPath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            ImageIO.write(bi1, "jpg", saveFile);    //将临时存储的文件移动到真实存储路径下
//            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.CREATED, visitPath);
    }

    @Transactional
    @PostMapping("book/log/add")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> bookLogAdd(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid){
        com.soen487.rest.project.repository.core.entity.Book book = this.bookRepository.findByBid(bid);
        if(book==null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        com.soen487.rest.project.repository.core.entity.BookChangeLog bookChangeLog =
                new com.soen487.rest.project.repository.core.entity.BookChangeLog(
                        new Timestamp(System.currentTimeMillis()),
                        LogType.CREATE, book.getIsbn13());
        bookChangeLog.setBook(book);
        long lid = this.bookChangeLogRepository.save(bookChangeLog).getLid();

        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.CREATED, lid);
    }

    @Transactional
    @DeleteMapping("book/delete")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> deleteBook(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid){
        if(!this.bookRepository.existsByBid(bid)){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        //根据要删除的图书id获取所有价格记录
        List<com.soen487.rest.project.repository.core.entity.Price> pricesToDelete =
                this.priceRepository.findAllByBook(this.bookRepository.findByBid(bid));
        //遍历返回的价格记录
        pricesToDelete.stream().forEach(price -> {
            //逐一删除价格历史记录
            this.priceHistoryRepository.deleteByPrice(price);
            //逐一删除价格记录
            this.priceRepository.deleteById(price.getPid());
        });
        //删除图书记录
        this.bookRepository.deleteByBid(bid);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.DELETED, bid);
    }

    @Transactional
    @PostMapping("book/log/delete")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> bookLogDelete(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid){
        com.soen487.rest.project.repository.core.entity.Book book = this.bookRepository.findByBid(bid);
        final String NO_ISBN_STR = "DELETED ISBN";
        // 如果返回结果非空，说明该图书记录还未被删除，不能更新log表
        if(book!=null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.CAN_NOT_LOG_AS_DELETED);
        }
        com.soen487.rest.project.repository.core.entity.BookChangeLog bookChangeLog =
                new com.soen487.rest.project.repository.core.entity.BookChangeLog(
                        new Timestamp(System.currentTimeMillis()),
                        LogType.DELETE, NO_ISBN_STR);
        book = new com.soen487.rest.project.repository.core.entity.Book();
        book.setBid(bid);
        bookChangeLog.setBook(book);
        long lid = this.bookChangeLogRepository.save(bookChangeLog).getLid();
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.CREATED, lid);
    }

    @Transactional
    @PutMapping("book/update")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> updateBook(@RequestBody String bookStr, @Valid @Pattern(regexp = "\\d+") @RequestParam("bid") long bid){
        if("".equals(bookStr) || bookStr==null){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }
        Gson gsonMapper = new Gson();
        com.soen487.rest.project.repository.core.entity.Book book = gsonMapper.fromJson(bookStr, com.soen487.rest.project.repository.core.entity.Book.class);
        if(book==null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }
        if(book.getTitle()==null || "".equals(book.getTitle())){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
        }
        if(!this.bookRepository.existsByBid(bid)) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }

        com.soen487.rest.project.repository.core.entity.Book bookPersisted = this.bookRepository.findByBid(bid);
        if(book.getCoverImg()!=null && !book.getCoverImg().equals(""))
            bookPersisted.setCoverImg(book.getCoverImg());
        if(book.getDescription()!=null && !book.getDescription().equals(""))
            bookPersisted.setDescription(book.getDescription());
        if(book.getFormat()!=null && !book.getFormat().equals(""))
            bookPersisted.setFormat(book.getFormat());
        if(book.getIsbn13()!=null && !book.getIsbn13().equals("") && !this.bookRepository.existsByIsbn13(book.getIsbn13()))
            bookPersisted.setIsbn13(book.getIsbn13());
        if(book.getLanguage()!=null && !book.getLanguage().equals(""))
            bookPersisted.setLanguage(book.getLanguage());
        if(book.getOriginalPrice()>0 && book.getOriginalPrice()!=bookPersisted.getOriginalPrice())
            bookPersisted.setOriginalPrice(book.getOriginalPrice());
        if(book.getPublisher()!=null && !book.getPublisher().equals(""))
            bookPersisted.setPublisher(book.getPublisher());
        if(book.getTitle()!=null && !book.getTitle().equals(""))
            bookPersisted.setTitle(book.getTitle());
        if(book.getCategory()!=null && this.categoryRepository.existsByName(book.getCategory().getName()))
            bookPersisted.setCategory(book.getCategory());

        // 更新作者
        if(book.getAuthors()!=null && book.getAuthors().size()>0){
            ListIterator it = book.getAuthors().listIterator();
            while(it.hasNext()){
                com.soen487.rest.project.repository.core.entity.Author author = (Author) it.next();
                Author authorPersisted = this.authorRepository.
                        findByFirstnameAndLastnameAndMiddlename(
                                author.getFirstname(),
                                author.getLastname(),
                                author.getMiddlename());
                if(authorPersisted==null){
                    authorPersisted = this.authorRepository.saveAndFlush(author);
                }
                it.set(authorPersisted);
            }
            bookPersisted.setAuthors(book.getAuthors());
        }

        // 更新价格表
        if(book.getCurrentPrice()!=null){
            // 书商对象不能为空，否则无法级联更新价格表，如果书商为空，抛出异常
            if(book.getSeller()==null){
                throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.KEY_ARGS_NOT_PROVIDED);
            }
            // 更新书商表
            com.soen487.rest.project.repository.core.entity.Seller sellerPersisted =
                    this.sellerRepository.findByName(book.getSeller().getName());
            if(sellerPersisted==null){
                sellerPersisted = this.sellerRepository.saveAndFlush(book.getSeller());
            }
            bookPersisted.setSeller(sellerPersisted);

            com.soen487.rest.project.repository.core.entity.Price pricePersisted =
                    this.priceRepository.findByBookAndSeller(bookPersisted, bookPersisted.getSeller());

            if(pricePersisted==null){
                // 如果当前图书的当前价格（非原始价格）的book和seller变量为空，添加当前图书与当前书商，以免价格记录中bid和sid为空。
                if(book.getCurrentPrice().getBook()==null){
                    book.getCurrentPrice().setBook(bookPersisted);
                }
                if(book.getCurrentPrice().getSeller()==null){
                    book.getCurrentPrice().setSeller(bookPersisted.getSeller());
                }
                pricePersisted = this.priceRepository.saveAndFlush(book.getCurrentPrice());
                //新插入一条价格记录后，向历史价格表中也插入一条记录
                PriceHistory priceHistory = new PriceHistory(
                        new Timestamp(System.currentTimeMillis()),
                        pricePersisted.getPrice());
                priceHistory.setPrice(pricePersisted);
                this.priceHistoryRepository.save(priceHistory);
            }
            else {
                //如果当前价格与历史价格不同，更新价格历史表
                if(pricePersisted.getPrice()!=book.getCurrentPrice().getPrice()){
                    PriceHistory priceHistory = new PriceHistory(
                            new Timestamp(System.currentTimeMillis()),
                            pricePersisted.getPrice());
                    priceHistory.setPrice(pricePersisted);
                    this.priceHistoryRepository.save(priceHistory);

                    pricePersisted.setPrice(book.getCurrentPrice().getPrice());
                }
                pricePersisted.setAfflicateUrl(book.getCurrentPrice().getAfflicateUrl());
                pricePersisted.setPurchaseUrl(book.getCurrentPrice().getPurchaseUrl());
            }
            this.priceRepository.save(pricePersisted);
        }

        this.bookRepository.saveAndFlush(bookPersisted);
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.UPDATED, bookPersisted.getBid());
    }

    @Transactional
    @PostMapping("book/log/update")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<Long> bookLogUpdate(@Valid @Pattern(regexp = "\\d+") @RequestParam(name="bid") long bid){
        com.soen487.rest.project.repository.core.entity.Book book = this.bookRepository.findByBid(bid);
        // 如果返回结果是空，说明该图书不存在，逻辑上无法更新，因此也不能更新log表
        if(book==null) {
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.CAN_NOT_LOG_AS_UPDATED);
        }
        com.soen487.rest.project.repository.core.entity.BookChangeLog bookChangeLog =
                new com.soen487.rest.project.repository.core.entity.BookChangeLog(
                        new Timestamp(System.currentTimeMillis()),
                        LogType.MODIFY, book.getIsbn13());
        bookChangeLog.setBook(book);
        long lid = this.bookChangeLogRepository.save(bookChangeLog).getLid();
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.
                success(ReturnCode.UPDATED, lid);
    }

    @GetMapping("book/list")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Book>> listBooks(){
        List<com.soen487.rest.project.repository.core.entity.Book> books = this.bookRepository.findAllBy();
        if(books==null || books.size()==0){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }

        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.FOUND, books);
    }

    @GetMapping("book/{bid}")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Book> detailBook(@PathVariable("bid") long bid){
        com.soen487.rest.project.repository.core.entity.Book book = this.bookRepository.findByBid(bid);
        if(book==null){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, book);
    }

    @GetMapping("author/{aid}")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<com.soen487.rest.project.repository.core.entity.Author> authorDetail(@PathVariable("aid") long aid){
        com.soen487.rest.project.repository.core.entity.Author author = this.authorRepository.findByAid(aid);
        if(author==null){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return(com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, author));
    }

    @PostMapping("book/search")
    public ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Book>> searchByTitleOrAuthorName(@RequestParam(name="keyword") String keyword){
        List<com.soen487.rest.project.repository.core.entity.Book> booksByTitle = this.bookRepository.findAllByTitleIgnoreCaseContaining(keyword);
        List<com.soen487.rest.project.repository.core.entity.Author> authorsByName = this.authorRepository.findAllByFirstnameIgnoreCaseContainingOrLastnameIgnoreCaseContainingOrMiddlenameIgnoreCaseContaining(keyword, keyword, keyword);
        Set<com.soen487.rest.project.repository.core.entity.Book> bookSetByAuthor = authorsByName.stream().map(Author::getBooks).flatMap(List::stream).collect(Collectors.toSet());
        Set<com.soen487.rest.project.repository.core.entity.Book> bookSetByTitle = booksByTitle.stream().collect(Collectors.toSet());
        Set<com.soen487.rest.project.repository.core.entity.Book> mergedSet = com.soen487.rest.project.repository.core.util.CommonUtils.mergeSets(bookSetByAuthor, bookSetByTitle);
        List<com.soen487.rest.project.repository.core.entity.Book> books = mergedSet.stream().collect(Collectors.toList());
        if(books==null){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return(com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.SUCCESS, books));
    }

    @GetMapping("log/list")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.BookChangeLog>> listLogs(){
        List<com.soen487.rest.project.repository.core.entity.BookChangeLog> logs = this.bookChangeLogRepository.findAllBy();
        if(logs==null || logs.size()==0){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.FOUND, logs);
    }

    @PostMapping("log/list")
    public com.soen487.rest.project.repository.core.configuration.ServiceResponse<List<com.soen487.rest.project.repository.core.entity.BookChangeLog>>
    listLogsByTimeAndType(@RequestParam(name="stime") Timestamp stime,
                          @RequestParam(name="etime") Timestamp etime,
                          @RequestParam(name="logType") LogType logType){
        List<com.soen487.rest.project.repository.core.entity.BookChangeLog> logs = this.bookChangeLogRepository.findAll((Specification<BookChangeLog>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("logTime"), stime);
            Predicate p2 = criteriaBuilder.lessThanOrEqualTo(root.get("logTime"), etime);
            Predicate p3 = null;
            if(logType!=null && logType.getCode()!=3){
                p3 = criteriaBuilder.equal(root.get("logType"), logType.getCode());
            }
            if(p3!=null)
                return criteriaBuilder.and(p1,p2,p3);
            else
                return criteriaBuilder.and(p1,p2);
        });
        if(logs==null){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return com.soen487.rest.project.repository.core.configuration.ServiceResponse.success(ReturnCode.FOUND, logs);
    }

    @GetMapping("list/category")
    ServiceResponse<List<com.soen487.rest.project.repository.core.entity.Category>> getCategoryList(){
        List<com.soen487.rest.project.repository.core.entity.Category> categories = this.categoryRepository.findAll((Specification< com.soen487.rest.project.repository.core.entity.Category>) (root, criteriaQuery, criteriaBuilder) ->{
            Predicate p1 = criteriaBuilder.equal(root.get("level"), 2);
            return criteriaBuilder.and(p1);
        });
        if(categories==null || categories.size()==0){
            throw new com.soen487.rest.project.repository.core.exception.ServiceException(ReturnCode.NOT_FOUND);
        }
        return ServiceResponse.success(ReturnCode.SUCCESS, categories);
    }
}
