package moe.ksmz.rodentraid.sck.Service;

import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.Models.Repositories.HuntRepository;
import moe.ksmz.rodentraid.sck.Service.Contracts.HuntManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuntService implements HuntManager {
    private final HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    public Optional<List<Hunt>> getAllHunts(Long userId) {
        return huntRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Hunt> getLatestHunt(Long userId) {
        return huntRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }
}
