package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.domain.Film;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film,Long> {

    @EntityGraph(attributePaths = {"personsInFilm"})
    List<Film> findAll();

    @EntityGraph(attributePaths = {"personsInFilm","ratingByUsers"})
    Optional<Film> findByName(String name);

}
