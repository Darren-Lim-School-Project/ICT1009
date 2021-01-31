package moe.ksmz.rodentraid.sck.Domain;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Objects;

public class Freshness {
    public Integer effect;

    private static final Map<Integer, String> effectTable =
            Map.ofEntries(
                    entry(-6, "Über Stale"),
                    entry(-5, "Ultimately Stale"),
                    entry(-4, "Insanely Stale"),
                    entry(-3, "Extremely Stale"),
                    entry(-2, "Very Stale"),
                    entry(-1, "Stale"),
                    entry(0, "No Effect"),
                    entry(1, "Fresh"),
                    entry(2, "Very Fresh"),
                    entry(3, "Extremely Fresh"),
                    entry(4, "Insanely Fresh"),
                    entry(5, "Ultimately Fresh"),
                    entry(6, "Über Fresh"));

    public Freshness(int freshness) {
        this.effect = freshness;
    }

    /**
     * Create a new instance of freshness
     *
     * @param freshness Int from -6 to +6
     * @return The newly created instance
     */
    public static Freshness of(int freshness) {
        freshness = constrainToRange(freshness, -6, 6);
        return new Freshness(freshness);
    }

    public static Freshness combined(Freshness firstItem, Freshness secondItem) {
        var first = constrainToRange(firstItem.effect, -6, 6);
        var second = constrainToRange(secondItem.effect, -6, 6);

        return of(first + second);
    }

    public static Integer constrainToRange(Integer value, Integer min, Integer max) {
        return Math.min(Math.max(value, min), max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var freshness = (Freshness) o;

        return Objects.equals(effect, freshness.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effect);
    }

    @Override
    public String toString() {
        return effectTable.get(effect);
    }
}
