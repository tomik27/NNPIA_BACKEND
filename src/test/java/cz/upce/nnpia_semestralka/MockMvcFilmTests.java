package cz.upce.nnpia_semestralka;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.Genre;
import cz.upce.nnpia_semestralka.dto.FilmOutDto;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
        import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class MockMvcFilmTests{

        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        @Autowired
private MockMvc mockMvc;

@MockBean
private FilmServiceImpl filmService;

@Autowired
private ModelMapper mapper;

        private Film  EXISTING = new Film(1L,"Terminator",null,Genre.HORROR,2018);

/*
@Test
public void testGetAllFilm() throws Exception {
        Film film = new Film();
        film.setId(1L);
        film.setTitle("Test Film");
        film.setYear(2022);

        List<Film> filmList = new ArrayList<>();
        filmList.add(film);

        given(filmService.getAll()).willReturn(filmList);

        mockMvc.perform(get("/film"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(film.getId().intValue())))
        .andExpect(jsonPath("$[0].title", is(film.getTitle())))
        .andExpect(jsonPath("$[0].year", is(film.getYear())));
        }*/

@Test
public void testGetFilmDetail() throws Exception {


        given(filmService.getFilmDetail(1L)).willReturn(EXISTING);

        mockMvc.perform(get("/film/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(EXISTING.getId().intValue())))
        .andExpect(jsonPath("$.name", is(EXISTING.getName())))
        .andExpect(jsonPath("$.releaseYear", is(EXISTING.getReleaseYear())));
        }

@Test
public void testDeleteFilm() throws Exception {
        mockMvc.perform(delete("/film/1"))
        .andExpect(status().isOk()).
       // .andExpect(content().string(""));
        }
        }


