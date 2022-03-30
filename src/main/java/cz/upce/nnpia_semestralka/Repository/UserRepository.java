package cz.upce.nnpia_semestralka.Repository;

import cz.upce.nnpia_semestralka.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
