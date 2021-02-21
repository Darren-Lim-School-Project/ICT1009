package moe.ksmz.rodentraid.sck.Service;

public class NoMiceInLocationException extends RuntimeException {
    public NoMiceInLocationException(String location) {
        super("No mice found in " + location);
    }
}
