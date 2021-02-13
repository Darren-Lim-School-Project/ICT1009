package moe.ksmz.rodentraid.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthStatus authStatus;

    public AuthInterceptor(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (authStatus.isLoggedIn()) {
            return true;
        }

        throw new UnauthorizedException();
    }
}
