package moe.ksmz.rodentraid.Response.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moe.ksmz.rodentraid.sck.Domain.Rank;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String name;
    private final String email;
    private final Rank rank;
}