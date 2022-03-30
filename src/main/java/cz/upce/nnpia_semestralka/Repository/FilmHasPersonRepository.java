package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmHasPersonRepository extends JpaRepository<FilmHasPerson,Long> {
}
