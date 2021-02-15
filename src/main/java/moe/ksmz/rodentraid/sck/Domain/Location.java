package moe.ksmz.rodentraid.sck.Domain;

import java.util.Locale;
import java.util.Objects;

public class Location {
    private String name;

    public Location(String name) {
        this.name = name.toLowerCase(Locale.ROOT);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return name.equalsIgnoreCase(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(Locale.ROOT));
    }
}
