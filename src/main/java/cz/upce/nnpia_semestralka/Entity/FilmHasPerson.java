package cz.upce.nnpia_semestralka.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FilmHasPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeOfPerson typeOfPerson;

    @ManyToOne
    private Film film;

    @ManyToOne
    private Person person;


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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
