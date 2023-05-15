package cz.upce.nnpia_semestralka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHasFilmDto {

    private Long id;
    private int rating;
    private String comment;
    private Long userId;
    private Long filmId;
    private String filmName;
    private String userName;
}
