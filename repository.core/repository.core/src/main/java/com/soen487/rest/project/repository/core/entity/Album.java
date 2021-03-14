//package com.soen487.rest.project.repository.core.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.List;
//
//@Entity
//public class Album implements Serializable {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//    @NotNull
//    private String isrc;
//    @NotNull
//    private String title;
//    private String description;
//    private int released_year;
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(referencedColumnName="nickname")
//    private com.soen487.rest.project.repository.core.entity.Artist artist;
//
//    public Album(){}
//
//    public Album(String isrc, String title, String description, int released_year) {
//        this.isrc = isrc;
//        this.title = title;
//        this.description = description;
//        this.released_year = released_year;
//        this.artist = null;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getISRC() {
//        return isrc;
//    }
//
//    public void setISRC(String ISRC) {
//        this.isrc = ISRC;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getReleased_year() {
//        return released_year;
//    }
//
//    public void setReleased_year(int released_year) {
//        this.released_year = released_year;
//    }
//
//    public com.soen487.rest.project.repository.core.entity.Artist getArtist() {
//        return artist;
//    }
//
//    public void setArtist(com.soen487.rest.project.repository.core.entity.Artist artist) {
//        this.artist = artist;
//    }
//
//    @Override
//    public String toString() {
//        return "Album{" +
//                "id=" + id +
//                ", ISRC='" + isrc + '\'' +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", released_year=" + released_year +
//                ", artist=" + artist +
//                '}';
//    }
//}
