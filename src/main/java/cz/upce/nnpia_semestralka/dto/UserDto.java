package cz.upce.nnpia_semestralka.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private int role;
    private List<UserHasFilmDto> userRatingFilms;
}
