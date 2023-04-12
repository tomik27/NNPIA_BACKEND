package cz.upce.nnpia_semestralka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Film {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(length = 30)
    private String name;
    @Lob
    @Column(name = "image", columnDefinition = " LONGBLOB")
    private byte[] image;
    @Column
    private Genre genre;
    @Column
    private Integer releaseYear;
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<FilmHasPerson> personsInFilm = Collections.emptyList();

    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<UserHasFilm> ratingByUsers= Collections.emptyList();

    public Film(Long id, String name, byte[] image, Genre genre, Integer releaseYear) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
}
