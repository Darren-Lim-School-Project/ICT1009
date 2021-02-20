package moe.ksmz.rodentraid.Request.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String email;
    private String password;
}
