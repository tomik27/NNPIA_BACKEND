package cz.upce.nnpia_semestralka.service.impl;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.FilmHasPersonDto;
import cz.upce.nnpia_semestralka.service.interfaces.FilmService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class FilmServiceImpl implements FilmService {


    private final FilmHasPersonRepository filmHasPersonRepository;
    private final FilmRepository filmRepository;
    private final ModelMapper mapper;

    private final UserHasFilmRepository userHasFilmRepository;


    public FilmServiceImpl(FilmHasPersonRepository filmHasPersonRepository, FilmRepository filmRepository, ModelMapper mapper, UserHasFilmRepository userHasFilmRepository) {
        this.filmHasPersonRepository = filmHasPersonRepository;
        this.filmRepository = filmRepository;
        this.mapper = mapper;
        this.userHasFilmRepository = userHasFilmRepository;
    }

    @Override
    public Film deleteFilm(Long id) throws Exception {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if(optionalFilm.isPresent()){
            Film film = optionalFilm.get();
                  filmRepository.deleteById(id);
            return film;
        }else
            throw new NoSuchElementException("Film not found.");
    }

    @Override
    public Film createFilm(String name, Genre genre, MultipartFile image, Date date) {

       Film film = new Film();
       film.setName(name);
       film.setGenre(genre);
       film.setReleaseDate(date);
       return filmRepository.save(film);
    }

    @Override
    public FilmDto createFilm(FilmDto filmDto) {
       // Film film = filmRepository.findByName(filmDto.getName());

        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setGenre(filmDto.getGenre());
        film.setPath_to_image(filmDto.getPath_to_image());
        film.setReleaseDate(filmDto.getReleaseDate());
        Film save = filmRepository.save(film);
        /*playlist.setOwner(owner);

        UsersPlaylist usersPlaylist = new UsersPlaylist();
        usersPlaylist.setPlaylist(playlist);
        usersPlaylist.setUser(owner);

        Playlist result = playlistRepository.save(playlist);
        usersPlaylistsRepository.save(usersPlaylist);
        return mapper.map(result, PlaylistDto.class);*/
        return mapper.map(save,FilmDto.class);
    }

    @Override
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public FilmDto getFilmDetail(Long id) {
        FilmDto filmDto;
        Optional<Film> optFilm = filmRepository.findById(id);
      //check if there is a value inside the Optional object
        if(optFilm.isPresent()){
         Film film = optFilm.get();
       //todo add person
            Set<FilmHasPerson> filmHasPersonSet = filmHasPersonRepository.findByFilmId(film.getId());
           //todo zmenit FilmHasPerson na FilmHasPersonDto, aby tam nebyl film
            List<FilmHasPersonDto> filmDtoList = mapper.map(filmHasPersonSet,
                    new TypeToken<List<FilmHasPersonDto>>() {
                    }.getType());

             filmDto = mapper.map(film, FilmDto.class);
            filmDto.setPersonsInFilms(filmDtoList);
            filmDto.setGenre(film.getGenre());
            filmDto.setReleaseDate(film.getReleaseDate());
            filmDto.setName(film.getName());
            filmDto.setPath_to_image(film.getPath_to_image());
            return filmDto;
        }else
            throw new NoSuchElementException("Film not found.");

    }

    @Override
    public FilmHasPersonDto addPersonToFilm(FilmHasPersonDto filmHasPersonDto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        if(!filmRepository.findById(id).isPresent())
            throw new NoSuchElementException("Film not found.");

        userHasFilmRepository.deleteUserHasFilmByFilmId(id);
        filmRepository.deleteById(id);
        filmHasPersonRepository.deleteFilmHasPersonByFilmId(id);

    }
}
