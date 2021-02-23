package moe.ksmz.rodentraid.Models;

public class InsufficientBaitException extends RuntimeException {
    InsufficientBaitException() {
        super("There is insufficient bait to deduct");
    }
}
