package com.soen487.rest.project.repository.core.entity.projection;

import java.util.Objects;

public class ArtistDto {
    private String nickname;
    private String firstname;
    private String lastname;

    public ArtistDto(String nickname, String firstname, String lastname) {
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDto artistDto = (ArtistDto) o;
        return nickname.equals(artistDto.nickname) &&
                firstname.equals(artistDto.firstname) &&
                lastname.equals(artistDto.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, firstname, lastname);
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
                "nickname='" + nickname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
