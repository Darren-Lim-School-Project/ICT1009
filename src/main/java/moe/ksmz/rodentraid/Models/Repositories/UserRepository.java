package moe.ksmz.rodentraid.Models.Repositories;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByOrderByPointsDesc();
}
