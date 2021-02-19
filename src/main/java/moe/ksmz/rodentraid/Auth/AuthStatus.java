package moe.ksmz.rodentraid.Auth;

import java.io.Serial;
import java.io.Serializable;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AuthStatus implements Serializable {
    @Serial private static final long serialVersionUID = 42069L;

    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new UnauthorizedException();
        }

        return currentUser;
    }

    public Long id() {
        return getCurrentUser().getId();
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
