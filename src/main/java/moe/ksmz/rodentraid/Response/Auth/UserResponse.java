package moe.ksmz.rodentraid.Response.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.sck.Domain.Rank;
import moe.ksmz.rodentraid.sck.Domain.Weapon;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final Long gold;
    private final String location;
    private final Long bait;
    private final Rank rank;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Weapon weapon;

    public static UserResponse fromUser(User user, Weapon weapon) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getGold(),
                user.getLocation(),
                user.getBait(),
                user.getCurrentRank(),
                weapon);
    }
}
