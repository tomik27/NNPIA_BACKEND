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
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.ROLE_USER;

    @OneToMany(mappedBy = "id",fetch = FetchType.LAZY)
    private Set<UserHasFilm> userRatingFilms;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Set<UserHasFilm> getUserRatingFilms() {
        return userRatingFilms;
    }

    public void setUserRatingFilms(Set<UserHasFilm> userRatingFilms) {
        this.userRatingFilms = userRatingFilms;
    }
}
