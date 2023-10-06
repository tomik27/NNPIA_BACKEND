package cz.upce.nnpia_semestralka.repository;

import cz.upce.nnpia_semestralka.domain.FilmHasPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmHasPersonRepository extends JpaRepository<FilmHasPerson,Long> {
        //@EntityGraph(attributePaths = {"person","film"})
        List<FilmHasPerson> findByFilm_Id(Long id);
        List<FilmHasPerson> findByPerson_Id(Long id);


    void  deleteFilmHasPersonByFilmId(Long id);

    List<FilmHasPerson> findByFilm_IdAndPerson_Id(Long filmId, Long personId);

}
