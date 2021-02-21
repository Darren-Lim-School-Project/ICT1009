package moe.ksmz.rodentraid.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import moe.ksmz.rodentraid.Models.User;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartyResponse {
    private String room;
    private List<User> users;
}
