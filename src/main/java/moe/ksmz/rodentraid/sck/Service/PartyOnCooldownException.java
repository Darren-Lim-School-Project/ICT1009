package moe.ksmz.rodentraid.sck.Service;

import java.util.List;
import java.util.stream.Collectors;
import moe.ksmz.rodentraid.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PartyOnCooldownException extends RuntimeException {
    public List<User> users;

    public PartyOnCooldownException(List<User> users) {
        super(
                "Not all members can sound the horn: "
                        + users.stream().map(User::getName).collect(Collectors.joining(",")));
        this.users = users;
    }
}
