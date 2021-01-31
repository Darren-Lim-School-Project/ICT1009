package moe.ksmz.rodentraid.sck.Domain;

public class Mice {
    private final String name;
    private final Long weight;
    private final Long gold;
    private final Long points;
    private final Long power;

    public Mice(String name, Long weight, Long gold, Long points, Long power) {
        this.name = name;
        this.weight = weight;
        this.gold = gold;
        this.points = points;
        this.power = power;
    }

    // TODO: finish
    public Long getEffectivenessAgainst(TrapTypes trapType) {
        return 0L;
    }
}
