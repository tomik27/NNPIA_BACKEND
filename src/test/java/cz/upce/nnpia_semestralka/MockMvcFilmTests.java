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

import java.util.ArrayList;
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



@Test
public void testGetAllFilm() throws Exception {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        film.setReleaseYear(2022);

        List<Film> filmList = new ArrayList<>();
        filmList.add(film);

        given(filmService.getAll()).willReturn(filmList);

        mockMvc.perform(get("/film"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(film.getId().intValue())))
        .andExpect(jsonPath("$[0].name", is(film.getName())))
        .andExpect(jsonPath("$[0].releaseYear", is(film.getReleaseYear())));
        }

@Test
public void testGetFilmDetail() throws Exception {
         Film  EXISTING = new Film(1L,"Terminator",null,Genre.HOROR,2018);

        FilmOutDto filmOutDto = mapper.map(EXISTING, FilmOutDto.class);
        given(filmService.getFilmDetail(1L)).willReturn(filmOutDto);

        mockMvc.perform(get("/film/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(EXISTING.getId().intValue())))
        .andExpect(jsonPath("$.name", is(EXISTING.getName())))
        .andExpect(jsonPath("$.releaseYear", is(EXISTING.getReleaseYear())));
        }

        }
