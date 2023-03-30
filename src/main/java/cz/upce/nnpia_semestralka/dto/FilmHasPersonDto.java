package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.domain.TypeOfPerson;

public class FilmHasPersonDto {

    private Long id;

    private TypeOfPerson typeOfPerson;

    private Long filmId;

    private Long personId;

    private String personFirstname;
    private String personLastname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfPerson getTypeOfPerson() {
        return typeOfPerson;
    }

    public void setTypeOfPerson(TypeOfPerson typeOfPerson) {
        this.typeOfPerson = typeOfPerson;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonFirstname() {
        return personFirstname;
    }

    public void setPersonFirstname(String personFirstname) {
        this.personFirstname = personFirstname;
    }

    public String getPersonLastname() {
        return personLastname;
    }

    public void setPersonLastname(String personLastname) {
        this.personLastname = personLastname;
    }
}
