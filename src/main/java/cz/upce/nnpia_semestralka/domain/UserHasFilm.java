package cz.upce.nnpia_semestralka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class UserHasFilm {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int rating;

    @Column(length = 300)
    private String comment;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private Film film;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private User user;


}
