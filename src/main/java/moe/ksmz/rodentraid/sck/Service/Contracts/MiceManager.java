package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;

public interface MiceManager extends Loadable<Mice> {
    Optional<Mice> getMouse(String mouseName);

    Optional<Mice> getRandomMiceForLocation(String location);

    Optional<Mice> getRandomMiceForLocation(Location location);

    List<Mice> allMiceForLocation(String location);

    List<Mice> allMiceForLocation(Location location);

    List<Mice> all();
}
