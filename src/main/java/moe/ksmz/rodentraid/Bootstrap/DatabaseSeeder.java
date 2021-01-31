package moe.ksmz.rodentraid.Bootstrap;

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
        var users = new ArrayList<User>();
        for (var i = 0; i < 5; i++) {
            var user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().safeEmailAddress());
            users.add(user);
        }

        userRepository.saveAll(users);
        log.info("Database seeded.");
    }
}
