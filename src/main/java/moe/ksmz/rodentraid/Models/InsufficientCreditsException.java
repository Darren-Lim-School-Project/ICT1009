package moe.ksmz.rodentraid.Models;

public class InsufficientCreditsException extends RuntimeException {
    InsufficientCreditsException() {
        super("Insufficient credits");
    }
}
