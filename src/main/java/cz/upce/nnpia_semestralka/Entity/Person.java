package cz.upce.nnpia_semestralka.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String birthPlace;

    @OneToMany(mappedBy = "id",fetch = FetchType.LAZY)
    private Set<FilmHasPerson> filmsWithPerson;

}
