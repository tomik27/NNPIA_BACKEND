package cz.upce.nnpia_semestralka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class FilmHasPerson {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
   // @Enumerated(EnumType.STRING)
    private TypeOfPerson typeOfPerson=TypeOfPerson.ACTOR;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Film film;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Person person;

}
