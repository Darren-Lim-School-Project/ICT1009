package moe.ksmz.rodentraid.sck.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import org.springframework.stereotype.Service;

@Service
public class HuntService implements HuntManager {
    private final HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    public boolean canHuntAgain(Long userId) {
        var latest = getLatestHunt(userId);
        if (latest.isEmpty()) {
            return true;
        }

        var latestHunt = latest.get().getCreatedAt();
        return Duration.between(latestHunt.toInstant(), Instant.now())
                        .compareTo(Duration.of(10, ChronoUnit.SECONDS))
                > 0;
    }

    public Optional<List<Hunt>> getAllHunts(Long userId) {
        return huntRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Hunt> getLatestHunt(Long userId) {
        return huntRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }
}
