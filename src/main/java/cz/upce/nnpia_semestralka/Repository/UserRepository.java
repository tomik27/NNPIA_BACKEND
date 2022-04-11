package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @EntityGraph(attributePaths = "userRatingFilms")
    User findByUsername(String username);
    @EntityGraph(attributePaths = "userRatingFilms")
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = "userRatingFilms")
    User findUserByUsername(String username);

}
