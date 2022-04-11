package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import cz.upce.nnpia_semestralka.Entity.Genre;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class FilmDto {

    private Long id;
    private String name;
    private String path_to_image;
    private Genre genre;
    private Date releaseDate;
    private List<FilmHasPersonDto> personsInFilms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<FilmHasPersonDto> getPersonsInFilms() {
        return personsInFilms;
    }

    public void setPersonsInFilms(List<FilmHasPersonDto> personsInFilms) {
        this.personsInFilms = personsInFilms;
    }
}
