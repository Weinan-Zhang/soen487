package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tab_author")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="a_aid")
    private long aid;
    @Column(name="a_firstname", nullable=false)
    private  String firstname;
    @Column(name="a_lastname", nullable=false)
    private String lastname;
    @Column(name="a_middlename")
    private String middlename;
    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<com.soen487.rest.project.repository.core.entity.Book> books;

    public Author(){}

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<com.soen487.rest.project.repository.core.entity.Book> getBooks() {
        return books;
    }

    public void setBooks(List<com.soen487.rest.project.repository.core.entity.Book> books) {
        this.books = books;
    }

    public String getMiddlename() { return middlename; }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String toString(){
        return this.firstname;
    }
}
