package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.Film;
import cz.upce.nnpia_semestralka.Entity.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {
    @EntityGraph(attributePaths = "filmsWithPerson")
    Optional<Person> findById(Long id);
}
