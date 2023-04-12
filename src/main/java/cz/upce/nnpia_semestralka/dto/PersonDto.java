package cz.upce.nnpia_semestralka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String birthPlace;
    private List<PersonHasFilmDto> personHasFilmDtos;

}
