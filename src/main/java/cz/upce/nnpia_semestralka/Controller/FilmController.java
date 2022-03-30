package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Repository.FilmRepository;
import cz.upce.nnpia_semestralka.dto.AddOrEditFilmDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/")
    public String showAllFilm(Model model){
        model.addAttribute("filmList",filmRepository.findAll());
        return "film-list";
    }

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
        }
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
