package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.service.interfaces.FilmService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
//@Validated
@RequestMapping("/api/film")
public class FilmController {

   private final FilmService filmService;

    private final ModelMapper mapper;

    public FilmController(FilmService filmService, ModelMapper mapper) {
        this.filmService = filmService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllFilm(){
      //  try{
        List<Film> all = filmService.getAll();
        return ResponseEntity.ok(mapper.map(all, new TypeToken<List<FilmDto>>(){}.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmDetail(@PathVariable Long id) {
        FilmDto filmDto = filmService.getFilmDetail(id);
        return ResponseEntity.ok(filmDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable Long id) {
        try {
            filmService.delete(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
/*
    @PostMapping("/api/film")
    public ResponseEntity<?> createFilm(@RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String name, @RequestParam(required = false) Genre genre,@RequestParam(required = false) Date date) {
        try {

            Film newFilm = filmService.createFilm(name,genre,file,date);
            return ResponseEntity.ok(mapper.map(newFilm, FilmDto.class));

        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating an film.");
        }
    }*/

@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<?> createFilm(@RequestBody FilmDto filmDto) {
    try {

        FilmDto film = filmService.createFilm(filmDto);
        return ResponseEntity.ok(mapper.map(film, FilmDto.class));

    } catch (Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating an film.");
    }
}
 /*
    @GetMapping(value={"/film-form","/film-form/{id}"})
    //nepovinne
    public String showFilmForm(@PathVariable(required = false) Long id, Model model){
      if(id!=null){
            //or else, kdyz id nenajdu tak pridam novy product
            Film byId =  filmRepository.findById(id).orElse(new Film());
            AddOrEditFilmDto dto= new AddOrEditFilmDto();
            dto.setId( byId.getId());
            dto.setName(byId.getName());
            //   dto.setGenre(byId.setGenre());
            dto.setReleaseDate(byId.getReleaseDate());
            //dto.set
            model.addAttribute("film",dto);
        }else{
            model.addAttribute("film",new AddOrEditFilmDto());

        return "film-form";
    }
    @GetMapping("/film/{id}")
    public String showFilm(@PathVariable Long id,Model model){
        //ctr alt t
        Film film =filmRepository.findById(id) .get();
        model.addAttribute("film",film);
        return "film";
    }
    @PostMapping("/film-form-process")
    public String filmFormProcess(AddOrEditFilmDto addOrEditFilmDto){
        Film film = new Film();
        //ssave rozpozna, ze v sobe ma identifikator, takze provedeu update
        film.setName(addOrEditFilmDto.getName());
        film.setId(addOrEditFilmDto.getId());

      //  film.setDirector(addOrEditFilmDto.getDirector());
        film.setReleaseDate(addOrEditFilmDto.getReleaseDate());
        filmRepository.save(film);
        return "redirect:/";
    }
          }
  */
}
