package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;

public interface LocationManager extends Loadable<Location> {
    List<Location> canBeFoundIn(Mice mice);

    List<Location> allMiceIn(Location location);
}
