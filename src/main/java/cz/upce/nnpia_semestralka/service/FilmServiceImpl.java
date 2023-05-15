package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.Repository.PersonRepository;
import cz.upce.nnpia_semestralka.domain.*;
import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.dto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl  {


    private final FilmHasPersonRepository filmHasPersonRepository;
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    private final UserHasFilmRepository userHasFilmRepository;


    public FilmServiceImpl(FilmHasPersonRepository filmHasPersonRepository, FilmRepository filmRepository, PersonRepository personRepository, ModelMapper mapper, UserHasFilmRepository userHasFilmRepository) {
        this.filmHasPersonRepository = filmHasPersonRepository;
        this.filmRepository = filmRepository;
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.userHasFilmRepository = userHasFilmRepository;
    }

    public Film deleteFilm(Long id) throws Exception {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found."));
        filmRepository.deleteById(id);
        return film;
    }


    public FilmOutDto createFilm(MultipartFile file,FilmInDto filmInDto) throws IOException {

        Film film = mapper.map(filmInDto, Film.class);

        if (file != null)
            film.setImage(file.getBytes());

        Film savedFilm = filmRepository.save(film);

        FilmOutDto filmOutDto = mapper.map(savedFilm, FilmOutDto.class);
        return filmOutDto;
    }

    public List<Film> getAll() {
        return filmRepository.findAll();
    }


    public Page<Film> getAll(Integer pageNumber, Integer pageSize,String sortBy, Sort.Direction sortDirection) {

        return filmRepository.findAll(PageRequest.of(pageNumber,pageSize,sortDirection,"name"));
    }

    public FilmOutDto getFilmDetail(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Film not found."));
        return mapFilmToDto(film);
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
        Person person = personRepository.findById(addPersonToFilmDto.getPersonId()).orElseThrow(() -> new NoSuchElementException("Person not found!"));
        Film film = filmRepository.findById(addPersonToFilmDto.getFilmId()).orElseThrow(() -> new NoSuchElementException("Film not found!"));

      //  FilmHasPerson filmHasPerson = filmHasPersonRepository.findByFilm_IdAndPerson_Id(addPersonToFilmDto.getFilmId(),addPersonToFilmDto.getPersonId());
        FilmHasPerson newFilmHasPerson = new FilmHasPerson();
        newFilmHasPerson.setPerson(person);
        newFilmHasPerson.setFilm(film);
        newFilmHasPerson.setTypeOfPerson(addPersonToFilmDto.getTypeOfPerson());

       /* if(filmHasPerson!=null)
            newFilmHasPerson.setId(filmHasPerson.getId());*/

        filmHasPersonRepository.save(newFilmHasPerson);
    }

    /*    'Akční',
    'Animovaný',
    'Dobrodružný',
    'Dokumentární',
    'Drama',
    'Fantasy',
    'Historický',
    'Horor',
    'Komedie',
    'Krimi',
    'Mysteriózní',
    'Romantický',
    'Sci-fi',
    'Thriller',
    'Válečný',
    'Western',
*/
    public  FilmOutDto mapFilmToDto(Film film) {
        FilmOutDto filmDto = new FilmOutDto();

        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setGenre(film.getGenre());
        filmDto.setReleaseYear(film.getReleaseYear());
        filmDto.setImage(film.getImage());

        // Assuming you have a method that maps FilmHasPerson to FilmHasPersonDto
        filmDto.setPersonsInFilms(film.getPersonsInFilm().stream()
                .map(filmHasPerson -> mapFilmHasPersonToDto(filmHasPerson))
                .collect(Collectors.toList()));

        // Assuming you have a method that maps UserHasFilm to UserHasFilmDto
        filmDto.setRatingByUsers(film.getRatingByUsers().stream()
                .map(userHasFilm -> mapUserHasFilmToDto(userHasFilm))
                .collect(Collectors.toList()));

        return filmDto;
    }

    public  FilmHasPersonDto mapFilmHasPersonToDto(FilmHasPerson filmHasPerson) {
        FilmHasPersonDto dto = new FilmHasPersonDto();

        dto.setId(filmHasPerson.getId());
        dto.setTypeOfPerson(filmHasPerson.getTypeOfPerson());
        dto.setFilmId(filmHasPerson.getFilm().getId());
        dto.setPersonId(filmHasPerson.getPerson().getId());
        dto.setPersonFirstname(filmHasPerson.getPerson().getFirstName());
        dto.setPersonLastname(filmHasPerson.getPerson().getLastName());

        return dto;
    }

    public UserHasFilmDto mapUserHasFilmToDto(UserHasFilm userHasFilm) {
        UserHasFilmDto dto = new UserHasFilmDto();

        dto.setId(userHasFilm.getId());
        dto.setRating(userHasFilm.getRating());
        dto.setComment(userHasFilm.getComment());
        dto.setUserId(userHasFilm.getUser().getId());
        dto.setFilmName(userHasFilm.getFilm().getName());
        dto.setUserName(userHasFilm.getUser().getUsername());

        return dto;
    }
}
