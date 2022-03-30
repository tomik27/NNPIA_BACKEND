package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
