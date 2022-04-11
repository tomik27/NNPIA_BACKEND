package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.Entity.Person;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.PersonDto;
import cz.upce.nnpia_semestralka.service.interfaces.PersonService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper mapper;

    public PersonController(PersonService personService, ModelMapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }
    @GetMapping
    public ResponseEntity<?> getAllPerson(){
        //  try{
        List<Person> all = personService.getAll();
        return ResponseEntity.ok(mapper.map(all, new TypeToken<List<PersonDto>>(){}.getType()));
    }

    //bude potreba?
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonDetail(@PathVariable Long id) {
        PersonDto personDto = personService.getPersonDetail(id);
        return ResponseEntity.ok(personDto);
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestParam String firstName,@RequestParam String lastName,@RequestParam Date birthDate,@RequestParam String birthPlace) {
        try {
//String firstName, String lastName, Date birthDate, String birthPlace
            Person newPerson = personService.createPerson(firstName,lastName,birthDate,birthPlace);
            return ResponseEntity.ok(mapper.map(newPerson, PersonDto.class));
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating an film.");
        }
    }
}
