package moe.ksmz.rodentraid.sck.Service;

import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.LocationManager;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements LocationManager {
    private boolean loaded = false;

    @Override
    public void loadEntries() {
        loaded = true;
    }

    @Override
    public List<Location> canBeFoundIn(Mice mice) {
        return null;
    }

    @Override
    public List<Location> allMiceIn(Location location) {
        return null;
    }
}
