package cz.upce.nnpia_semestralka.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserHasFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Column(length = 300)
    private String comment;

    @ManyToOne
    private Film film;

    @ManyToOne
    private User user;


}
