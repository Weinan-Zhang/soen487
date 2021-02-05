package com.soen487.rest.project.repository.core.entity.projection;

import java.util.Objects;

public class AlbumDto {
    private String isrc;
    private String title;

    public AlbumDto(String isrc, String title) {
        this.isrc = isrc;
        this.title = title;
    }

    public String getIsrc() {
        return isrc;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumDto albumDto = (AlbumDto) o;
        return Objects.equals(isrc, albumDto.isrc) &&
                Objects.equals(title, albumDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isrc, title);
    }

    @Override
    public String toString() {
        return "AlbumDto{" +
                "ISRC='" + isrc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
