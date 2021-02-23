package moe.ksmz.rodentraid.Bootstrap;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;

    public DatabaseSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        var faker = new Faker();

        for (var name : new String[] {"a", "b", "c", "x"}) {
            var h = new User();
            h.setName(faker.name().fullName());
            h.setPassword(BCrypt.withDefaults().hashToString(12, "secret".toCharArray()));
            h.setEmail(name);
            h.setPoints(faker.number().numberBetween(100L, 1000000L));
            h.setGold(faker.number().numberBetween(100L, 1000000L));
            h.setTrap("High Tension Spring");
            h.setBase("Wooden Base with Target");
            h.setLocation("Meadow");
            userRepository.save(h);
        }

        var users = new ArrayList<User>();
        for (var i = 0; i < 5; i++) {
            var user = new User();
            user.setName(faker.name().fullName());
            user.setPassword(BCrypt.withDefaults().hashToString(12, "secret".toCharArray()));
            user.setEmail(faker.internet().safeEmailAddress());
            user.setPoints(faker.number().numberBetween(100L, 1000000L));
            user.setGold(faker.number().numberBetween(100L, 1000000L));
            user.setTrap("High Tension Spring");
            user.setBase("Wooden Base with Target");
            user.setLocation("Meadow");
            users.add(user);
        }

        userRepository.saveAll(users);
        log.info("Database seeded.");
    }
}
