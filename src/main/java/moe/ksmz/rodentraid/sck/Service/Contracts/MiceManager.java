package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;

public interface MiceManager extends Loadable<Mice> {
    Optional<Mice> getMouse(String mouseName);

    List<Mice> allMiceForLocation(String location);

    List<Mice> allMiceForLocation(Location location);

    List<Mice> all();
}
