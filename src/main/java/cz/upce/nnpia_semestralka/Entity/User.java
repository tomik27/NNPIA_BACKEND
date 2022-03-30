package cz.upce.nnpia_semestralka.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int role;

    @OneToMany(mappedBy = "id",fetch = FetchType.LAZY)
    private Set<UserHasFilm> userRatingFilms;

}
