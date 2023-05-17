package cz.upce.nnpia_semestralka.service;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserHasFilmRepository;
import cz.upce.nnpia_semestralka.Repository.UserRepository;
import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.User;
import cz.upce.nnpia_semestralka.domain.UserHasFilm;
import cz.upce.nnpia_semestralka.dto.AddFilmToUserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserHasFilmRepository userHasFilmRepository;

    @Autowired
    private UserService userService;

    private User user;
    private Film film;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("Test User");
        userRepository.save(user);

        film = new Film();
        film.setName("Test Film");
        filmRepository.save(film);
    }

    @AfterEach
    public void tearDown() {
        userHasFilmRepository.deleteUserHasFilmByFilmId(film.getId());
        userRepository.delete(user);
        filmRepository.delete(film);
    }


    @Test
    @Transactional
    @DirtiesContext
    public void testAddFilmToUser() {

        AddFilmToUserDto addFilmToUserDto = new AddFilmToUserDto();
        addFilmToUserDto.setUserId(user.getId());
        addFilmToUserDto.setFilmId(film.getId());
        addFilmToUserDto.setComment("Test comment");
        addFilmToUserDto.setRating(5);

        userService.addFilmToUser(addFilmToUserDto);

        UserHasFilm userHasFilm = userHasFilmRepository.findByUser_IdAndFilm_Id(user.getId(), film.getId());
        assertNotNull(userHasFilm);
        assertEquals(user.getId(), userHasFilm.getUser().getId());
        assertEquals(film.getId(), userHasFilm.getFilm().getId());
        assertEquals("Test comment", userHasFilm.getComment());
        assertEquals(5, userHasFilm.getRating());
    }
}
