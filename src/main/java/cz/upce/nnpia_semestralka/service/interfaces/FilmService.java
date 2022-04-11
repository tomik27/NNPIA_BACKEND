package cz.upce.nnpia_semestralka.service.interfaces;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.FilmHasPersonDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film deleteFilm(Long id) throws Exception;
    Film createFilm(String name, Genre genre, MultipartFile image, Date date);
    FilmDto createFilm(FilmDto filmDto);
    public Optional<Film> findById(Long id);
    public List<Film> getAll();
    public FilmDto getFilmDetail(Long id);
    public FilmHasPersonDto addPersonToFilm(FilmHasPersonDto filmHasPersonDto);

    void delete(Long id);


    /*
    private String name;
    private Genre genre;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
     */

}
