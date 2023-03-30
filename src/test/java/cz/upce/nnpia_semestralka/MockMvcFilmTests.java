package cz.upce.nnpia_semestralka;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.Genre;
import cz.upce.nnpia_semestralka.dto.FilmOutDto;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.hamcrest.Matchers.is;

/*@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ComponentScan*/
//@ExperimentalStdlibApi
@SpringBootTest
@AutoConfigureMockMvc
class MockMvcFilmTests {
/*

        @Autowired
       private   MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

        @MockBean
        private FilmServiceImpl filmService;


        @Test
       void  createNewFilm() throws Exception{
            FilmOutDto filmOutDto = new FilmOutDto();
            filmOutDto.setPath_to_image("image.jpg");
            filmOutDto.setName("Film");
          //  filmOutDto.setReleaseDate(new Date());
            filmOutDto.setGenre(Genre.HORROR);

            String dataString = objectMapper.writeValueAsString(filmOutDto);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/film")
                    .content(dataString)
                    .contentType(MediaType.APPLICATION_JSON))

                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is(filmOutDto.getName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.genre", Is.is(filmOutDto.getGenre())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate", Is.is(filmOutDto.getReleaseDate())));

        }
//page problem
  /*      @Test
      //  @WithAnonymousUser
        void getAllFilmsTest() throws Exception {
                List<Film> filmList = new ArrayList();
                filmList.add(createFilm());
                filmList.add(createFilm());

                Mockito.when(filmService.getAll()).thenReturn(filmList);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/film"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("NameOfFilm")))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre", is("Genre.HORROR")));
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
*/
        }
