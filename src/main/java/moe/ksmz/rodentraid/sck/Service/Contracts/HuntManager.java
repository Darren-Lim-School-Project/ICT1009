package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.Hunt;

public interface HuntManager {
    boolean canHuntAgain(Long userId);

    Optional<List<Hunt>> getAllHunts(Long userId);

    Optional<Hunt> getLatestHunt(Long currentId);
}
