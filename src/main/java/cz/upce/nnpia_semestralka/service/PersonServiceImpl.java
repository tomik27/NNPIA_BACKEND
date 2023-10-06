package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.domain.FilmHasPerson;
import cz.upce.nnpia_semestralka.domain.Person;
import cz.upce.nnpia_semestralka.repository.PersonRepository;
import cz.upce.nnpia_semestralka.dto.InputPersonDto;
import cz.upce.nnpia_semestralka.dto.PersonDto;
import cz.upce.nnpia_semestralka.dto.PersonHasFilmDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl {

    private final ModelMapper mapper;
    private final PersonRepository personRepository;
    private final FilmHasPersonRepository filmHasPersonRepository;

    public PersonServiceImpl(ModelMapper mapper, PersonRepository personRepository, FilmHasPersonRepository filmHasPersonRepository) {
        this.mapper = mapper;
        this.personRepository = personRepository;
        this.filmHasPersonRepository = filmHasPersonRepository;
    }

    public Person deletePerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            personRepository.deleteById(id);
            return person;
        }else
            throw new NoSuchElementException("Film not found.");
    }

    public Person createPerson(InputPersonDto personDto) {
       Person person = mapper.map(personDto,Person.class);
        Person save = personRepository.save(person);
        return save;
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public List<PersonDto> getAll() {
        List<Person> all = personRepository.findAll();
        List<PersonDto> personDtos = all.stream()
                .map(person -> mapPersonToDto(person))
                .collect(Collectors.toList());

        return personDtos;
    }

    public PersonDto getPersonDetail(Long id) {

        Person person = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person not found"));
        List<FilmHasPerson> listFilmsOfPerson = filmHasPersonRepository.findByPerson_Id(id);

        List<PersonHasFilmDto> personHasFilmDtos = mapper.map(listFilmsOfPerson,
                new TypeToken<List<PersonHasFilmDto>>() {
                }.getType());

        PersonDto personDto = mapper.map(person, PersonDto.class);
        personDto.setPersonHasFilmDtos(personHasFilmDtos);
        return personDto;
    }

    public Person removePerson(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new NoSuchElementException("Person not found with id: " + personId));
        personRepository.delete(person);
        return person;
    }

    public Person editFilm(Long id, InputPersonDto personDto) {
        personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person id not found!"));
        Person person = mapper.map(personDto, Person.class);
        person.setId(id);
        return personRepository.save(person);
    }

    public PersonDto mapPersonToDto(Person person) {
        List<PersonHasFilmDto> personHasFilmDtos = person.getFilmsWithPerson().stream()
                .map(filmHasPerson -> new PersonHasFilmDto(
                        filmHasPerson.getTypeOfPerson(),
                        filmHasPerson.getFilm().getId(),
                        filmHasPerson.getPerson().getId(),
                        filmHasPerson.getFilm().getName()
                ))
                .collect(Collectors.toList());

        return new PersonDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getBirthDate(),
                person.getBirthPlace(),
                personHasFilmDtos
        );
    }
}
