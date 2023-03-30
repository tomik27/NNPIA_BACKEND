package cz.upce.nnpia_semestralka.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Person {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Date birthDate;
    @Column
    private String birthPlace;

    @OneToMany(mappedBy = "id",cascade = CascadeType.REMOVE)
    private Set<FilmHasPerson> filmsWithPerson;

}
