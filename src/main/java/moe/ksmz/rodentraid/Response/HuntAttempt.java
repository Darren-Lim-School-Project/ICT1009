package moe.ksmz.rodentraid.Response;

import static moe.ksmz.rodentraid.sck.Service.HuntService.HUNT_INTERVAL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import moe.ksmz.rodentraid.Models.Hunt;
import moe.ksmz.rodentraid.sck.Domain.CatchState;
import moe.ksmz.rodentraid.sck.Domain.Mice;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class HuntAttempt {
    private Long tooEarly;
    private Mice mice;
    private Long weight;
    private CatchState catchState;

    public static HuntAttempt tooEarly(Hunt lastHunt) {
        var diffInSeconds =
                ChronoUnit.SECONDS.between(lastHunt.getCreatedAt().toInstant(), Instant.now());

        return new HuntAttempt(HUNT_INTERVAL - diffInSeconds, null, null, null);
    }

    public static HuntAttempt success(Hunt hunt, Mice mice) {
        return new HuntAttempt(null, mice, hunt.getWeight(), hunt.getCatchState());
    }
}
