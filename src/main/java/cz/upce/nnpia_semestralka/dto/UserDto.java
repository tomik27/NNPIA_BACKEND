package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.Entity.UserHasFilm;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private int role;
    private List<UserHasFilmDto> userRatingFilms;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<UserHasFilmDto> getUserRatingFilms() {
        return userRatingFilms;
    }

    public void setUserRatingFilms(List<UserHasFilmDto> userRatingFilms) {
        this.userRatingFilms = userRatingFilms;
    }
}
