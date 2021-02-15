package moe.ksmz.rodentraid.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    UnauthorizedException() {
        super("Not logged in");
    }
}
