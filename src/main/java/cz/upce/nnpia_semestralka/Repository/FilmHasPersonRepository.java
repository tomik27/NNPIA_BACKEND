package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FilmHasPersonRepository extends JpaRepository<FilmHasPerson,Long> {
    @EntityGraph(attributePaths = {"person","film"})
    Set<FilmHasPerson> findByFilmId(Long id);

    void  deleteFilmHasPersonByFilmId(Long id);
}
