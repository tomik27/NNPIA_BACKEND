package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film,Long> {
}
