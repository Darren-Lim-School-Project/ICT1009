package moe.ksmz.rodentraid.Response.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.sck.Domain.Rank;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String name;
    private final String email;
    private final Long gold;
    private final Rank rank;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getName(), user.getEmail(), user.getGold(), user.getCurrentRank());
    }
}
