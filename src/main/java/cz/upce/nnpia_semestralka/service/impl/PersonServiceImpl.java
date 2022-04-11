package cz.upce.nnpia_semestralka.service.impl;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Person;
import cz.upce.nnpia_semestralka.Repository.PersonRepository;
import cz.upce.nnpia_semestralka.dto.FilmDto;
import cz.upce.nnpia_semestralka.dto.PersonDto;
import cz.upce.nnpia_semestralka.service.interfaces.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final ModelMapper mapper;
    private final PersonRepository personRepository;

    public PersonServiceImpl(ModelMapper mapper, PersonRepository personRepository) {
        this.mapper = mapper;
        this.personRepository = personRepository;
    }

    @Override
    public Person deletePerson(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            personRepository.deleteById(id);
            return person;
        }else
            throw new NoSuchElementException("Film not found.");
    }

    @Override
    public Person createPerson(String firstName, String lastName, Date birthDate, String birthPlace) {
       Person person = new Person();
       person.setFirstName(firstName);
       person.setLastName(lastName);
       person.setBirthDate(birthDate);
       person.setBirthPlace(birthPlace);
       personRepository.save(person);
       return person;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public PersonDto getPersonDetail(Long id) {
        PersonDto personDto;
        Optional<Person> optionalPerson = personRepository.findById(id);
        //check if there is a value inside the Optional object
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            //todo add person
            PersonDto map = mapper.map(person, PersonDto.class);
            return map;
        }else
            throw new NoSuchElementException("Film not found.");
    }
}
