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



}
