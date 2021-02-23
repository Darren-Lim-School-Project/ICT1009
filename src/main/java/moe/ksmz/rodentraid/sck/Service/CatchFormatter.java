package moe.ksmz.rodentraid.sck.Service;

import moe.ksmz.rodentraid.sck.Domain.CatchState;
import moe.ksmz.rodentraid.sck.Domain.Mice;

public class CatchFormatter {
    public static String getMessage(CatchState catchState, Mice mice, Long weight) {
        var messageBuffer = new StringBuilder();
        messageBuffer.append("I sounded the Hunter's horn ");
        var outcome =
                switch (catchState) {
                    case LUCK -> "and got lucky!";
                    case POWER -> "and was successful in the hunt!";
                    case FAILED -> "but the mouse was too powerful!";
                };
        messageBuffer.append(outcome);

        var catchIfAny =
                switch (catchState) {
                    case POWER, LUCK -> String.format(
                            " I caught a %d gram %s mouse worth %d points and %d gold!",
                            weight, mice.getName(), mice.getPoints(), mice.getGold());
                    case FAILED -> String.format(
                            " A(n) %s mouse ate my bait and managed to get away. Better luck next time!",
                            mice.getName());
                };
        messageBuffer.append(catchIfAny);

        return messageBuffer.toString();
    }
}
