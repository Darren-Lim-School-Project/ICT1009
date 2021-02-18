package moe.ksmz.rodentraid.sck.Domain;

import java.util.LinkedHashMap;
import java.util.Map;
import moe.ksmz.rodentraid.Foundation.Builder;

public class Rank {
    private final Long points;

    private static final Map<String, Long> rankTable = new LinkedHashMap<>();

    static {
        // If you don't *meet* this point requirement, you're still "on" this rank
        new Builder<>(rankTable)
                .put("novice", 2000L)
                .put("recruit", 3000L)
                .put("apprentice", 7500L)
                .put("initiate", 18750L)
                .put("journeyman", 34190L)
                .put("master", 72373L)
                .put("grandmaster", 165375L)
                .put("legendary", 363825L)
                .put("hero", 800415L)
                .put("knight", 1760913L)
                .put("lord", 3874008L)
                .put("baron", 8522819L)
                .put("count", 18750202L)
                .put("duke", 41250443L)
                .put("grandduke", 90750976L)
                .put("archduke", 199652147L)
                .put("viceroy", 439234723L)
                .put("elder", 966316389L)
                .put("sage", 2125896058L);
    }

    public Rank(Long points) {
        this.points = points;
    }

    public Long getPoints() {
        return points;
    }

    public String getRankTitle() {
        for (var rank : rankTable.entrySet()) {
            if (points <= rank.getValue()) {
                return rank.getKey();
            }
        }

        return "sage";
    }

    public float getPercentage() {
        var lastRank = 0L;
        for (var rank : rankTable.entrySet()) {
            var current = rank.getValue();
            if (points < current) {
                return (points - lastRank) / (float) (current - lastRank);
            }

            lastRank = current;
        }

        return 0;
    }
}
