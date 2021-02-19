package moe.ksmz.rodentraid.sck.Service.Contracts;

import moe.ksmz.rodentraid.Models.Hunt;

import java.util.List;
import java.util.Optional;

public interface HuntManager {
    Optional<List<Hunt>> getAllHunts(Long userId);
    Optional<Hunt> getLatestHunt(Long currentId);
}
