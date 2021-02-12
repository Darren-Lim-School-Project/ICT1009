package moe.ksmz.rodentraid.Auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
}
