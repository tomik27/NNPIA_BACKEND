package cz.upce.nnpia_semestralka.dto;

import cz.upce.nnpia_semestralka.Entity.Genre;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AddOrEditFilmDto {
    private Long id;
    private Genre genre;
    //  @Column(columnDefinition = )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String name;
}
