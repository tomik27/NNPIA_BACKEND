package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.PersonRepository;
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

import static org.springframework.test.util.AssertionErrors.assertNotNull;


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

    @BeforeEach
    public void setUp() {
        filmRepository.save(EXISTING);
        filmRepository.flush(); //zajišťuje, že všechny neuložené změny v paměti (tzv. persistence context nebo session) jsou okamžitě odeslány do databáze
    }

    //anotaci @Transactional na testovací metodu, což by mělo znamenat, že jak ukládání filmu a osoby, tak i vyhledání filmu jsou součástí stejné transakce
    @Test
    @Transactional
    void testGetFilm() {
        ResponseEntity<?> response = filmController.getFilmDetail(EXISTING.getId());
        FilmOutDto actual = (FilmOutDto) response.getBody();
        Assertions.assertEquals(EXISTING.getId(), actual.getId());
    }

    @Test
    @Transactional
    void testAddPersonToFilm() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(new Date());
        personRepository.save(person);

        AddPersonToFilmDto addPersonToFilmDto = new AddPersonToFilmDto();
        addPersonToFilmDto.setPersonId(person.getId());
        addPersonToFilmDto.setFilmId(EXISTING.getId());
        addPersonToFilmDto.setTypeOfPerson(TypeOfPerson.ACTOR);

        filmService.addPersonToFilm(addPersonToFilmDto);

        List<FilmHasPerson> listFilmHasPerson = filmHasPersonRepository.findByFilm_IdAndPerson_Id(EXISTING.getId(), person.getId());
        //Assertions.assertNotNull(listFilmHasPerson);

        Assertions.assertEquals(TypeOfPerson.ACTOR, listFilmHasPerson.get(0).getTypeOfPerson());
    }

    @AfterEach
    @Transactional
    void tearDown() {
        filmRepository.deleteAll();
    }
}