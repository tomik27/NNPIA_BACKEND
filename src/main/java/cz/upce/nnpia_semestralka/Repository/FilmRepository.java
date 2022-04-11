package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.Film;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film,Long> {
    @EntityGraph(attributePaths = {"personsInFilm","ratingByUsers"})
    Optional<Film> findById(Long id);

    @EntityGraph(attributePaths = {"personsInFilm","ratingByUsers"})
    Optional<Film> findByName(String name);

}
