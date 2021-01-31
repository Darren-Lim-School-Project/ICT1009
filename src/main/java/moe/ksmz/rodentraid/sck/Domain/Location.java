package moe.ksmz.rodentraid.sck.Domain;

import java.util.List;
import java.util.Set;

public class Location {
    private String name;
    private Set<Mice> mice;

    public boolean hasMice(Mice mice) {
        return this.mice.contains(mice);
    }

    public boolean hasMice(List<Mice> mice) {
        return this.mice.containsAll(mice);
    }
}
