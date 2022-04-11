package cz.upce.nnpia_semestralka;

import cz.upce.nnpia_semestralka.Controller.FilmController;
import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmApplicationTests {

    //mozna dopsat localhost:
    public static final String API_FILM = "/api/film";

    @Autowired
    private FilmController filmController;

    @Autowired
   private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void postFilm_whenFilmIsValid_receiveOK() {
        Film film = createFilm();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_FILM, film, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    void postFilm_whenFilmIsValid_receiveOK2() {
        Film film = createFilm();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Film> request = new HttpEntity<>(film, headers);

        //ResponseEntity<String> result = this..postForEntity(uri, request, String.class);


        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_FILM,request, Object.class);
     //   Assert.assertEquals(201, result.getStatusCodeValue());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private Film createFilm(){
        Film film = new Film();
        film.setName("NameOfFilm");
        film.setReleaseDate(new Date());
        film.setId(1L);
        film.setGenre(Genre.HORROR);
        film.setPath_to_image("");
        return film;
    }
    private User createUser() {
        User user = new User();
        user.setUsername ("test-user");
        user.setPassword("P4assword");
        return user;
    }
}
