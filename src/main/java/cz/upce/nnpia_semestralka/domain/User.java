package cz.upce.nnpia_semestralka.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name="AppUser")
public class User {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.ROLE_USER;

    @OneToMany(mappedBy = "id", cascade = CascadeType.REMOVE)
    private List<UserHasFilm> userRatingFilms= Collections.emptyList();
}
