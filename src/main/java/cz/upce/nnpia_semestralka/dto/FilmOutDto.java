package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmOutDto {

    private Long id;
    private String name;
    private String path_to_image;
    private Genre genre;
    private Integer releaseYear;
    private byte[] image;
    private List<FilmHasPersonDto> personsInFilms;
}
