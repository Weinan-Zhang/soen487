package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tab_seller")
public class Seller implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="s_sid")
    private long sid;
    @Column(name="s_name", nullable=false)
    private String name;
    @Column(name="s_website_url", nullable=false)
    private String websiteUrl;
    @Column(name="s_website_logo_img")
    private String websiteLogoImg;
    @OneToMany(mappedBy = "seller")
    @JsonIgnore
    private List<com.soen487.rest.project.repository.core.entity.Price> prices;

    public Seller(){}

    public Seller(String name, String websiteUrl) {
        this.name = name;
        this.websiteUrl = websiteUrl;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteLogoImg() {
        return websiteLogoImg;
    }

    public void setWebsiteLogoImg(String websiteLogoImg) {
        this.websiteLogoImg = websiteLogoImg;
    }

    public List<com.soen487.rest.project.repository.core.entity.Price> getPrices() {
        return prices;
    }

    public void setPrices(List<com.soen487.rest.project.repository.core.entity.Price> prices) {
        this.prices = prices;
    }
}
