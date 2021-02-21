package moe.ksmz.rodentraid.sck.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Service.Contracts.LocationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationService implements LocationManager {
    List<Location> locations = new ArrayList<>();

    @Value("classpath:locations.txt")
    Resource locationResource;

    @Override
    public void loadEntries() throws IOException {
        try (var stream =
                new BufferedReader(new InputStreamReader(locationResource.getInputStream()))) {
            while (stream.ready()) {
                var line = stream.readLine().trim();
                if (!line.equals("")) {
                    locations.add(new Location(line));
                }
            }
        }

        log.info("Loaded {} locations", locations.size());
    }

    @Override
    public List<Location> all() {
        return locations;
    }

    @Override
    public Optional<Location> find(String locationName) {
        var toFind = new Location(locationName);
        return locations.stream().filter(location -> location.equals(toFind)).findAny();
    }
}
