package cz.upce.nnpia_semestralka.service.interfaces;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Genre;
import cz.upce.nnpia_semestralka.Entity.Person;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.PersonDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Person deletePerson(Long id);
    Person createPerson(String firstName, String lastName, Date birthDate, String birthPlace);
    public Optional<Person> findById(Long id);
    public List<Person> getAll();
    public PersonDto getPersonDetail(Long id);
}
