package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, Long id);

    User findByUsername(String username);

    List<User> findUserByUsername(String username);

}