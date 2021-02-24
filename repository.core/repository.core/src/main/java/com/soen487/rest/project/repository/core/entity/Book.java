package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tab_book", indexes={@Index(columnList="b_isbn13"),
                                @Index(columnList="b_title"),
                                @Index(columnList="b_publisher")}
    )
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="b_bid")
    private long bid;
    @Column(name="b_title", nullable=false)
    private String title;
    @Column(name="b_format")
    private String format;
    @Column(name="b_publisher")
    private String publisher;
    @Column(name="b_language")
    private String language;
    @Column(name="b_isbn13", unique=true)
    private String isbn13;
//    @Column(name="b_isbn10", unique=true)
//    private String isbn10;
    @Column(name="b_cover_img")
    private String coverImg;
    @Column(name="b_original_price")
    private double originalPrice;
    @Transient
    @JsonIgnore
    private com.soen487.rest.project.repository.core.entity.Price currentPrice;
    @Lob
    @Column(name="b_description")
    private String description;
    @Transient
    @JsonIgnore
    private com.soen487.rest.project.repository.core.entity.Seller seller;
    @ManyToMany
    @JoinTable(name = "book_author",
               joinColumns = {@JoinColumn(name="b_bid")},
               inverseJoinColumns = {@JoinColumn(name="a_aid")})
    private List<com.soen487.rest.project.repository.core.entity.Author> authors;
    @OneToMany(mappedBy="book")
    private List<com.soen487.rest.project.repository.core.entity.Price> prices;
    @OneToMany(mappedBy = "book")
    @org.hibernate.annotations.ForeignKey(name = "none")
    @JsonIgnore
    private List<com.soen487.rest.project.repository.core.entity.BookChangeLog> logs;

    public Book() {}

    public Book(String title,
                com.soen487.rest.project.repository.core.entity.Seller seller,
                com.soen487.rest.project.repository.core.entity.Price currentPrice) {
        this.title = title;
        this.seller = seller;
        this.currentPrice = currentPrice;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

//    public String getIsbn10() {
//        return isbn10;
//    }
//
//    public void setIsbn10(String isbn10) {
//        this.isbn10 = isbn10;
//    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public List<com.soen487.rest.project.repository.core.entity.Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<com.soen487.rest.project.repository.core.entity.Author> authors) { this.authors = authors; }

    public List<com.soen487.rest.project.repository.core.entity.Price> getPrices() {
        return prices;
    }

    public void setPrices(List<com.soen487.rest.project.repository.core.entity.Price> prices) {
        this.prices = prices;
    }

    public List<com.soen487.rest.project.repository.core.entity.BookChangeLog> getLogs() {
        return logs;
    }

    public void setLogs(List<com.soen487.rest.project.repository.core.entity.BookChangeLog> logs) {
        this.logs = logs;
    }

    public com.soen487.rest.project.repository.core.entity.Seller getSeller() { return seller; }

    public void setSeller(com.soen487.rest.project.repository.core.entity.Seller seller) { this.seller = seller; }

    public com.soen487.rest.project.repository.core.entity.Price getCurrentPrice() { return currentPrice; }

    public void setCurrentPrice(com.soen487.rest.project.repository.core.entity.Price currentPrice) { this.currentPrice = currentPrice; }
}
