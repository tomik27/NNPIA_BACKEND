package cz.upce.nnpia_semestralka.repository;

import cz.upce.nnpia_semestralka.domain.UserHasFilm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserHasFilmRepository extends JpaRepository<UserHasFilm,Long> {
    @EntityGraph(attributePaths = {"user","film"})
    Set<UserHasFilm> findByUserId(Long id);


    void deleteUserHasFilmByFilmId(Long id);

    UserHasFilm findByUser_IdAndFilm_Id(Long userId,Long filmId);

}
