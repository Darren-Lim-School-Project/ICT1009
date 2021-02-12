package moe.ksmz.rodentraid.Auth;

import java.util.Optional;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AuthStatus {
    private Optional<User> currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = Optional.of(user);
    }

    public Optional<User> getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser.isPresent();
    }
}
