package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tab_category")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cid", scope = Category.class)
public class Category implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="c_cid")
    private long cid;
    @Column(name="c_name")
    private String name;
    @Column(name="c_level", columnDefinition="integer")
    private int level;
    @ManyToOne
    @JoinColumn(name="parent_c_cid")
    @JsonIgnore
    private Category parentCategory;
    @OneToMany(mappedBy="parentCategory")
    private List<Category> categories;

    public Category() {}

    public Category(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
