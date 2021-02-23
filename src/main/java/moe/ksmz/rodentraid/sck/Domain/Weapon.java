package moe.ksmz.rodentraid.sck.Domain;

public class Weapon {
    // Trap
    private final Cat cat;
    private final Base base;

    public Weapon(Cat cat, Base base) {
        this.cat = cat;
        this.base = base;
    }

    public CatchState attemptCatch(Mice mice) {
        // 1st attempt
        if (shouldBeCaught(mice)) {
            return CatchState.POWER;
        }

        // 2nd attempt
        return feelingLucky(getTotalTrapLuck()) ? CatchState.LUCK : CatchState.FAILED;
    }

    public boolean shouldBeCaught(Mice mice) {
        var micePower = mice.getPower();
        var adjustedTrapPower = mice.getEffectivenessFor(cat.getType()) * getTotalTrapPower();

        var roll = Math.random();
        // TODO: scaling bonus
        if (micePower < adjustedTrapPower) {
            // roll a flat 90% success + bonus
            return roll <= 0.9;
        } else {
            // roll a flat 10% success + bonus
            return roll <= 0.1;
        }
    }

    public boolean feelingLucky(Long luck) {
        if (luck <= 0) {
            return false;
        }

        double chanceToNotCatch;
        // limit to 50
        if (luck > 50) {
            luck = 50L;
        }

        chanceToNotCatch = 1 - (luck / 100d);

        var roll = Math.random();
        return !(roll < chanceToNotCatch);
    }

    private Long rawPower() {
        return cat.getPower() + base.getPower();
    }

    private Long rawBonus() {
        return cat.getBonus() + base.getBonus();
    }

    public Long getTotalTrapPower() {
        var adjustedPower = rawPower() * (1 + (rawBonus() / 100d));

        return Math.round(adjustedPower);
    }

    public Long getTotalTrapLuck() {
        return cat.getLuck() + base.getLuck();
    }

    public Cat getCat() {
        return cat;
    }

    public Base getBase() {
        return base;
    }
}
