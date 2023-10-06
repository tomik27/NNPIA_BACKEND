package cz.upce.nnpia_semestralka.controller;

import cz.upce.nnpia_semestralka.repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.repository.FilmRepository;
import cz.upce.nnpia_semestralka.repository.PersonRepository;
import cz.upce.nnpia_semestralka.domain.*;
import cz.upce.nnpia_semestralka.dto.AddPersonToFilmDto;


import cz.upce.nnpia_semestralka.dto.FilmOutDto;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@SpringBootTest
class FilmControllerTest {
    private Film EXISTING = new Film(1L,"Terminator",null,Genre.HOROR,2018);

    @Autowired
    private FilmServiceImpl filmService;

    @Autowired
    private FilmController filmController;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FilmHasPersonRepository filmHasPersonRepository;


    //anotaci @Transactional na testovací metodu, což by mělo znamenat, že jak ukládání filmu a osoby, tak i vyhledání filmu jsou součástí stejné transakce
    @Test
    @Transactional
    void testGetFilm() {
        Film film = filmRepository.save(EXISTING);
        ResponseEntity<?> response = filmController.getFilmDetail(film.getId());
        FilmOutDto actual = (FilmOutDto) response.getBody();
        Assertions.assertEquals(film.getId(), actual.getId());
    }

    @Test
    @Transactional
    void testAddPersonToFilm() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(new Date());
        personRepository.save(person);
        Film film = filmRepository.save(EXISTING);

        AddPersonToFilmDto addPersonToFilmDto = new AddPersonToFilmDto();
        addPersonToFilmDto.setPersonId(person.getId());
        addPersonToFilmDto.setFilmId(film.getId());
        addPersonToFilmDto.setTypeOfPerson(TypeOfPerson.ACTOR);

        filmService.addPersonToFilm(addPersonToFilmDto);

        List<FilmHasPerson> listFilmHasPerson = filmHasPersonRepository.findByFilm_IdAndPerson_Id(film.getId(), person.getId());
        //Assertions.assertNotNull(listFilmHasPerson);

        Assertions.assertEquals(TypeOfPerson.ACTOR, listFilmHasPerson.get(0).getTypeOfPerson());
    }

    @AfterEach
    @Transactional
    void tearDown() {
        filmRepository.deleteAll();
    }
}