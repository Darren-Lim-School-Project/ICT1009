package moe.ksmz.rodentraid.Auth;

import java.io.Serializable;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AuthStatus implements Serializable {
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
