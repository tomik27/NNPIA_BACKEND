package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.domain.Film;
import cz.upce.nnpia_semestralka.domain.Genre;
import cz.upce.nnpia_semestralka.dto.AddFilmToUserDto;
import cz.upce.nnpia_semestralka.dto.AddPersonToFilmDto;
import cz.upce.nnpia_semestralka.dto.FilmInDto;
import cz.upce.nnpia_semestralka.dto.FilmOutDto;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
//@RequestBody FilmDto filmDto @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createFilm(@RequestParam String name, @RequestParam Genre genre, @RequestParam(required = false) Integer releaseDate, @RequestParam(name="imageFile",required = false) MultipartFile imageFile) throws IOException {
        FilmInDto filmInDto = new FilmInDto(name,genre,releaseDate);
        FilmOutDto film = filmService.createFilm(imageFile,filmInDto);
        return ResponseEntity.ok(film);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable Long id,@RequestBody FilmInDto filmInDto) {
        return ResponseEntity.ok(filmService.editFilm(id,filmInDto));
    }

    @PostMapping("/addPerson")
      @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addPersonToFilm(@RequestBody @Valid AddPersonToFilmDto addPersonToFilmDto) {
        filmService.addPersonToFilm(addPersonToFilmDto);
        return ResponseEntity.ok().build();
    }
    /*

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FilmOutDto> createFilm(@RequestPart("film") FilmInDto filmInDto, @RequestPart("image") MultipartFile imageFile) throws IOException {
        FilmOutDto film = filmService.createFilm(imageFile, filmInDto);
        return ResponseEntity.ok(film);
    }
*/


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
