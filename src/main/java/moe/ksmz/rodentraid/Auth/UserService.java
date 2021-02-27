package moe.ksmz.rodentraid.Auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.Repositories.UserRepository;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(List<Long> IDs) {
        return userRepository.findAllById(IDs);
    }

    public Optional<User> attemptLogin(String email, String password) {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        var dbPassword = user.get().getPassword();
        var result = BCrypt.verifyer().verify(password.toCharArray(), dbPassword);

        if (result.verified) {
            return user;
        }

        return Optional.empty();
    }

    public Optional<User> register(String name, String email, String password) {
        var userExists = userRepository.findByEmail(email);
        if (userExists.isPresent()) {
            return Optional.empty();
        }

        var hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        var newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(hashed);
        newUser.setPoints(1L);
        newUser.setGold(10000L);
        newUser.setTrap("High Tension Spring");
        newUser.setBase("Wooden Base with Target");
        newUser.setBait(10L);
        newUser.setLocation("Meadow");

        userRepository.save(newUser);

        return Optional.of(newUser);
    }
}
