package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.FilmHasPerson;
import cz.upce.nnpia_semestralka.Entity.UserHasFilm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserHasFilmRepository extends JpaRepository<UserHasFilm,Long> {
    @EntityGraph(attributePaths = {"user","film"})
    Set<UserHasFilm> findByUserId(Long id);

    void deleteUserHasFilmByFilmId(Long id);
}
