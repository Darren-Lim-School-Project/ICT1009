package moe.ksmz.rodentraid.sck.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.sck.Domain.Location;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MiceService implements MiceManager {
    private List<Mice> mice;

    @Value("classpath:out.csv")
    Resource resourceFile;

    @Override
    public void loadEntries() throws IOException {
        mice =
                new CsvToBeanBuilder<Mice>(new InputStreamReader(resourceFile.getInputStream()))
                        .withType(Mice.class)
                        .build()
                        .parse();
        log.info("Loaded {} mice", mice.size());
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Mice> getMouse(String mouseName) {
        return mice.stream().filter(mouse -> mouse.getName().equalsIgnoreCase(mouseName)).findAny();
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Mice> getRandomMiceForLocation(String location) {
        return getRandomMiceForLocation(new Location(location));
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Mice> getRandomMiceForLocation(Location location) {
        var mice = allMiceForLocation(location);
        if (mice.size() < 1) {
            return Optional.empty();
        }

        var rand = new Random();
        var randMouse = mice.get(rand.nextInt(mice.size()));

        return Optional.of(randMouse);
    }

    /** {@inheritDoc} */
    @Override
    public List<Mice> allMiceForLocation(String location) {
        return allMiceForLocation(new Location(location));
    }

    /** {@inheritDoc} */
    @Override
    public List<Mice> allMiceForLocation(Location location) {
        return mice.stream()
                .filter(mouse -> mouse.getLocations().contains(location))
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<Mice> all() {
        return mice;
    }
}
