package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.domain.TypeOfPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPersonToFilmDto {
    private Long filmId;
    private Long personId;
    private TypeOfPerson typeOfPerson;
}
