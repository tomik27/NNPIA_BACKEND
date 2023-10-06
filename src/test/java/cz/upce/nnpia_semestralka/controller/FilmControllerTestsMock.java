package cz.upce.nnpia_semestralka.controller;

import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.dto.FilmOutDto;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTestsMock {

    FilmServiceImpl filmService;

    ModelMapper modelMapper;
    FilmController filmController;

    @BeforeEach
    void setUp() {
        filmService = Mockito.mock(FilmServiceImpl.class);
        modelMapper = new ModelMapper();
        filmController = new FilmController(filmService, modelMapper);
    }

    @Test
    void getAllFilm() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Test Film");
        Mockito.when(filmService.getAll()).thenReturn(Arrays.asList(film));

        ResponseEntity<List<FilmOutDto>> response = filmController.getAllFilm();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Film", response.getBody().get(0).getName());
    }

    @Test
    void getFilmDetail() {
        FilmOutDto filmOutDto = new FilmOutDto();
        filmOutDto.setId(1L);
        filmOutDto.setName("Test Film");
        Mockito.when(filmService.getFilmDetail(1L)).thenReturn(filmOutDto);

        ResponseEntity<?> response = filmController.getFilmDetail(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Film", ((FilmOutDto) response.getBody()).getName());
    }
}






