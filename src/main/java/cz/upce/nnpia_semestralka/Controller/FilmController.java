package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.FilmHasPerson;
import cz.upce.nnpia_semestralka.domain.Genre;
import cz.upce.nnpia_semestralka.domain.UserHasFilm;
import cz.upce.nnpia_semestralka.dto.*;
import cz.upce.nnpia_semestralka.service.FilmServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@SecurityRequirement(name = "NNPIA_API")
@RequestMapping("/film")
@AllArgsConstructor
public class FilmController {

   private final FilmServiceImpl filmService;

        private final ModelMapper mapper;

 /*   @GetMapping("/film")
    public List<Film> getAllFilm(
        @RequestParam(defaultValue = "0") Integer pageNumber,
        @RequestParam(defaultValue = "10") Integer pageSize,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection){
        return filmService.getAll(pageNumber,pageSize,sortBy,sortDirection).stream().collect(Collectors.toList());
        //return ResponseEntity.ok(mapper.map(all, new TypeToken<List<FilmDto>>(){}.getType()));
    }*/


    @GetMapping("")
    public ResponseEntity<List<FilmOutDto>> getAllFilm(){
        List<Film> all = filmService.getAll();
        return ResponseEntity.ok(mapper.map(all, new TypeToken<List<FilmOutDto>>(){}.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmDetail(@PathVariable final Long id) {
        return ResponseEntity.ok(filmService.getFilmDetail(id));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFilm(@PathVariable Long id) {
            filmService.delete(id);
            return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createFilm(@RequestParam String name, @RequestParam Genre genre, @RequestParam(required = false) Integer releaseDate, @RequestParam(name="imageFile",required = false) MultipartFile imageFile) throws IOException {
        FilmInDto filmInDto = new FilmInDto(name,genre,releaseDate);
        FilmOutDto film = filmService.createFilm(imageFile,filmInDto);
        return ResponseEntity.ok(film);
    }
/*
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFilm(@RequestParam Long id,@RequestBody FilmInDto filmInDto, @RequestParam(name="imageFile",required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(filmService.editFilm(id,imageFile,filmInDto));
    }*/

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value ="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFilm(
            @RequestParam Long id,
            @ModelAttribute FilmInDto filmInDto,
            @RequestParam(name="imageFile",required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(filmService.editFilm(id,imageFile,filmInDto));
    }

    @PostMapping("/addPerson")
      @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addPersonToFilm(@RequestBody @Valid AddPersonToFilmDto addPersonToFilmDto) {
        filmService.addPersonToFilm(addPersonToFilmDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletePerson")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> deletePersonFromFilm(@RequestBody @Valid AddPersonToFilmDto addPersonToFilmDto) {
        filmService.deletePersonFromFilm(addPersonToFilmDto);
        return ResponseEntity.ok().build();
    }

}
