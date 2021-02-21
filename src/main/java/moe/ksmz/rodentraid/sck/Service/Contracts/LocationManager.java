package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.sck.Domain.Location;

public interface LocationManager extends Loadable<Location> {
    List<Location> all();

    Optional<Location> find(String location);
}
