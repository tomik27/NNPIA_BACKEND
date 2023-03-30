package cz.upce.nnpia_semestralka.service;

import cz.upce.nnpia_semestralka.Repository.FilmHasPersonRepository;
import cz.upce.nnpia_semestralka.domain.FilmHasPerson;
import cz.upce.nnpia_semestralka.domain.Person;
import cz.upce.nnpia_semestralka.Repository.PersonRepository;
import cz.upce.nnpia_semestralka.dto.FilmHasPersonDto;
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

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public PersonDto getPersonDetail(Long id) {

        Person person = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person not found"));
        List<FilmHasPerson> listFilmsOfPerson = filmHasPersonRepository.findByPerson_Id(id);

        List<PersonHasFilmDto> map = mapper.map(listFilmsOfPerson,
                new TypeToken<List<PersonHasFilmDto>>() {
                }.getType());



        return mapper.map(person, PersonDto.class);
        //check if there is a value inside the Optional object
        /*if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            //todo add person
            PersonDto map = mapper.map(person, PersonDto.class);
            return map;
        }else
            throw new NoSuchElementException("Film not found.");*/
    }

    public Person removePerson(Long personId) {
        return personRepository.findById(personId).orElseThrow(() -> new NoSuchElementException("Person not found!"));
    }

    public Person editFilm(Long id, InputPersonDto personDto) {
        personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person id not found!"));
        Person person = mapper.map(personDto, Person.class);
        person.setId(id);
        return personRepository.save(person);
    }


}
