package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.FilmHasPerson;
import cz.upce.nnpia_semestralka.domain.Genre;
import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.dto.AddPersonToFilmDto;
import cz.upce.nnpia_semestralka.dto.FilmInDto;
import cz.upce.nnpia_semestralka.dto.FilmOutDto;
import cz.upce.nnpia_semestralka.dto.FilmHasPersonDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class FilmServiceImpl  {


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

    public Film deleteFilm(Long id) throws Exception {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found."));
        filmRepository.deleteById(id);
        return film;
    }


    public FilmOutDto createFilm(MultipartFile file,FilmInDto filmInDto) throws IOException {
        // Film film = filmRepository.findByName(filmDto.getName());

       /*    Film film = new Film();
     film.setName(filmInDto.getName());
        film.setGenre(filmInDto.getGenre());
        film.setPath_to_image(filmInDto.getPath_to_image());
        film.setReleaseDate(filmInDto.getReleaseDate());*/
        Film film = mapper.map(filmInDto, Film.class);

        if (file != null)
            film.setImage(file.getBytes());

        Film savedFilm = filmRepository.save(film);
        /*playlist.setOwner(owner);

        UsersPlaylist usersPlaylist = new UsersPlaylist();
        usersPlaylist.setPlaylist(playlist);
        usersPlaylist.setUser(owner);

        Playlist result = playlistRepository.save(playlist);
        usersPlaylistsRepository.save(usersPlaylist);
        return mapper.map(result, PlaylistDto.class);*/
        FilmOutDto filmOutDto = mapper.map(savedFilm, FilmOutDto.class);

      /*  if (savedFilm.getImage() != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(savedFilm.getImage());
            BufferedImage image = ImageIO.read(bis);

            // write the image to a byte array output stream
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);

            // set the PNG image data to the FilmOutDto object
            filmOutDto.setImage(bos.toByteArray());

            return filmOutDto;
        }*/
        return filmOutDto;
    }

    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }


    public List<Film> getAll() {
        return filmRepository.findAll();
    }


    public Page<Film> getAll(Integer pageNumber, Integer pageSize,String sortBy, Sort.Direction sortDirection) {

        return filmRepository.findAll(PageRequest.of(pageNumber,pageSize,sortDirection,"name"));
    }

    public Film getFilmDetail(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found."));

        //todo add person
        List<FilmHasPerson> filmHasPersonSet = filmHasPersonRepository.findByFilm_Id(film.getId());
        //todo zkontrolovat
      //  List<FilmHasPersonDto> filmDtoList = getMap(filmHasPersonSet);

     //   FilmOutDto filmOutDto = mapper.map(film, FilmOutDto.class);
       /* filmDto.setPersonsInFilms(filmDtoList);
        filmDto.setGenre(film.getGenre());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setName(film.getName());
        filmDto.setPath_to_image(film.getPath_to_image());*/
        return film;
    }

    private List<FilmHasPersonDto> getMap(Set<FilmHasPerson> filmHasPersonSet) {
        return mapper.map(filmHasPersonSet,
                new TypeToken<List<FilmHasPersonDto>>() {
                }.getType());
    }


    public void delete(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film id not found!"));
       // userHasFilmRepository.deleteUserHasFilmByFilmId(id);
        filmRepository.deleteById(id);
      //  filmHasPersonRepository.deleteFilmHasPersonByFilmId(id);

    }

    public Film editFilm(Long id, FilmInDto filmInDto) {
        filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film id not found!"));
        Film film = mapper.map(filmInDto, Film.class);
        film.setId(id);
       return filmRepository.save(film);
    }

    public void addPersonToFilm(AddPersonToFilmDto addPersonToFilmDto) {

    }
}
