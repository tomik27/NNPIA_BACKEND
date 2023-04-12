package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.PersonRepository;
import cz.upce.nnpia_semestralka.domain.*;
import cz.upce.nnpia_semestralka.dto.AddPersonToFilmDto;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
   private Film  EXISTING = new Film(1L,"Terminator",null,Genre.HORROR,2018);


    @InjectMocks
    private FilmController filmController;

    @Autowired
    private FilmServiceImpl filmService;


    @Autowired
     private FilmRepository filmRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FilmHasPersonRepository filmHasPersonRepository;


    @Test
    @Transactional
    void testGetFilm()  {
        var expected =filmRepository.save(EXISTING);
        var actual = filmService.getFilmDetail(1L);
        assertEquals(expected,actual);
    }

    @Test
    void testAddPersonToFilm() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthDate(new Date());
        personRepository.save(person);

        filmRepository.save(EXISTING);

        AddPersonToFilmDto addPersonToFilmDto = new AddPersonToFilmDto();
        addPersonToFilmDto.setPersonId(person.getId());
        addPersonToFilmDto.setFilmId(EXISTING.getId());
        addPersonToFilmDto.setTypeOfPerson(TypeOfPerson.ACTOR);

        filmService.addPersonToFilm(addPersonToFilmDto);

        FilmHasPerson filmHasPerson = filmHasPersonRepository.findByFilm_IdAndPerson_Id(EXISTING.getId(), person.getId());
        assertNotNull(filmHasPerson);
       /* assertEquals(EXISTING, filmHasPerson.getFilm());
        assertEquals(person, filmHasPerson.getPerson());*/
        assertEquals(TypeOfPerson.ACTOR, filmHasPerson.getTypeOfPerson());
    }

    @AfterEach
    void tearDown() {
    filmRepository.deleteAll();
    }
    /* List<Film> films = Arrays.asList(new Film("Title1", "Genre1", "Director1"),
                new Film("Title2", "Genre2", "Director2"));
        when(filmService.getAll()).thenReturn(films);
        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("Genre1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].director").value("Director1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("Genre2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].director").value("Director2"));
    }*/

}