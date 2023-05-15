package cz.upce.nnpia_semestralka.Controller;

import cz.upce.nnpia_semestralka.domain.Person;
import cz.upce.nnpia_semestralka.dto.FilmInDto;
import cz.upce.nnpia_semestralka.dto.InputPersonDto;
import cz.upce.nnpia_semestralka.dto.PersonDto;
import cz.upce.nnpia_semestralka.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/person")
@SecurityRequirement(name = "NNPIA_API")
@AllArgsConstructor
public class PersonController {

    private final PersonServiceImpl personService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<?> getAllPerson(){
        List<PersonDto> allDto = personService.getAll();
        return ResponseEntity.ok(allDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonDetail(@PathVariable Long id) {
        PersonDto personDto = personService.getPersonDetail(id);
        return ResponseEntity.ok(personDto);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<?> removePerson(@PathVariable Long personId) {
        return ResponseEntity.ok(personService.removePerson(personId));
    }

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody @Valid InputPersonDto personDto) {
            Person newPerson = personService.createPerson(personDto);
            return ResponseEntity.ok(mapper.map(newPerson, PersonDto.class));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id,@RequestBody InputPersonDto personDto) {
        return ResponseEntity.ok(personService.editFilm(id,personDto));
    }
}
