package moe.ksmz.rodentraid.Models.Repositories;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HuntRepository extends JpaRepository<Hunt, Long> {
    Optional<Hunt> findFirstByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<List<Hunt>> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
