package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.domain.TypeOfPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputPersonDto {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String birthPlace;
}
