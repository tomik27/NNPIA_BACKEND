package cz.upce.nnpia_semestralka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFilmToUserDto {
    private int rating;
    private String comment;
    private Long userId;
    private Long filmId;
}
