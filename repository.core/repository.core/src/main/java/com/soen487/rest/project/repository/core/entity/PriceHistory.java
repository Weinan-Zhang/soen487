package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;



@Entity
@Table(name="tab_price_history")
public class PriceHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ph_lid")
    private long id;
    @Column(name="ph_log_time", nullable = false)
    private Timestamp logTime;
    @Column(name="ph_price_modified", nullable = false)
    private double priceModified;
    @ManyToOne
    @JoinColumn(name="ph_pid")
    @JsonIgnore
    private com.soen487.rest.project.repository.core.entity.Price price;

    public PriceHistory(){}

    public PriceHistory(Timestamp logTime, double priceModified) {
        this.logTime = logTime;
        this.priceModified = priceModified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public double getPriceModified() {
        return priceModified;
    }

    public void setPriceModified(double priceModified) {
        this.priceModified = priceModified;
    }

    public com.soen487.rest.project.repository.core.entity.Price getPrice() {
        return price;
    }

    public void setPrice(com.soen487.rest.project.repository.core.entity.Price price) {
        this.price = price;
    }
}
