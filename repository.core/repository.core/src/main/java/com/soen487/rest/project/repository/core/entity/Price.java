package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tab_price")
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="p_pid")
    @JsonIgnore
    private long pid;
    @Column(name="p_price", nullable = false)
    private double price;
    @Column(name="p_purchase_url", nullable = false)
    private String purchaseUrl;
    @Column(name="p_afflicate_url")
    private String afflicateUrl;
    @ManyToOne
    @JoinColumn(name="p_bid")
    @JsonIgnore
    private com.soen487.rest.project.repository.core.entity.Book book;
    @ManyToOne
    @JoinColumn(name="p_sid")
    private com.soen487.rest.project.repository.core.entity.Seller seller;
    @OneToMany(mappedBy = "price")
    private List<com.soen487.rest.project.repository.core.entity.PriceHistory> historyPrices;

    public Price(){}

    public Price(double price, String purchaseUrl) {
        this.price = price;
        this.purchaseUrl = purchaseUrl;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public String getAfflicateUrl() {
        return afflicateUrl;
    }

    public void setAfflicateUrl(String afflicateUrl) {
        this.afflicateUrl = afflicateUrl;
    }

    public com.soen487.rest.project.repository.core.entity.Book getBook() {
        return book;
    }

    public void setBook(com.soen487.rest.project.repository.core.entity.Book book) {
        this.book = book;
    }

    public com.soen487.rest.project.repository.core.entity.Seller getSeller() {
        return seller;
    }

    public void setSeller(com.soen487.rest.project.repository.core.entity.Seller seller) {
        this.seller = seller;
    }

    public List<com.soen487.rest.project.repository.core.entity.PriceHistory> getHistoryPrices() {
        return historyPrices;
    }

    public void setHistoryPrices(List<com.soen487.rest.project.repository.core.entity.PriceHistory> historyPrices) {
        this.historyPrices = historyPrices;
    }
}
