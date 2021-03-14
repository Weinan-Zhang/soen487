//package com.soen487.rest.project.repository.core.entity;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class Artist implements Serializable {
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    private Long id;
//    @NotNull
//    @Column(unique=true)
//    private String nickname;
//    private String firstname;
//    private String lastname;
//    private String biography;
//    @OneToMany(fetch=FetchType.LAZY, mappedBy="artist")
//    private Set<com.soen487.rest.project.repository.core.entity.Album> albums = new HashSet<>();
//
//    public Artist(){}
//    public Artist(String nickname, String firstname, String lastname, String biography) {
//        this.nickname = nickname;
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.biography = biography;
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
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getBiography() {
//        return biography;
//    }
//
//    public void setBiography(String biography) {
//        this.biography = biography;
//    }
//
//    @Override
//    public String toString() {
//        return "Artist{" +
//                "id=" + id +
//                ", nickname='" + nickname + '\'' +
//                ", firstname='" + firstname + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", biography='" + biography + '\'' +
//                ", albums=" + albums +
//                '}';
//    }
//}
