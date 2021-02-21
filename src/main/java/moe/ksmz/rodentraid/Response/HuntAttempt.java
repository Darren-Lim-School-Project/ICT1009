package moe.ksmz.rodentraid.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moe.ksmz.rodentraid.sck.Domain.Mice;

@Getter
@AllArgsConstructor
public class CatchAttempt {
    private Mice mice;
    private Long weight;
    private boolean becauseOfLuck;
}
