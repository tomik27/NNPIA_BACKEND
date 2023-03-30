package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmInDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Genre cannot be null")
    private Genre genre;

    @NotNull(message = "Release cannot be null")
    private Integer releaseYear;
}
