package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.UserHasFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasFilmRepository extends JpaRepository<UserHasFilm,Long> {
}
