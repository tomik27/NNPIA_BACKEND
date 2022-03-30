package cz.upce.nnpia_semestralka.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Film {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    private Genre genre;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

   @OneToMany(mappedBy = "id",fetch = FetchType.LAZY)
    private Set<FilmHasPerson> personsInFilm;

    @OneToMany(mappedBy = "id",fetch = FetchType.LAZY)
    private Set<FilmHasPerson> ratingByUsers;

 /*   @ManyToOne
    private FilmLibrary filmLibrary;*/
}
