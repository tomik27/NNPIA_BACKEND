package cz.upce.nnpia_semestralka.repository;

import cz.upce.nnpia_semestralka.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

   // User findByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findUserByUsername(String username);

    boolean existsByEmail(String emailAddress);
}