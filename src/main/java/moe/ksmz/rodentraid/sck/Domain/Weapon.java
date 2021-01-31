package moe.ksmz.rodentraid.sck.Domain;

public class Weapon {
    private final Cat cat;
    private final Base base;

    public Weapon(Cat cat, Base base) {
        this.cat = cat;
        this.base = base;
    }

    public boolean attemptCatch(Mice mice) {
        // 1st attempt
        if (shouldBeCaught(mice)) {
            return true;
        }

        // 2nd attempt
        return feelingLucky(totalTrapLuck());
    }

    // TODO: finish
    public boolean shouldBeCaught(Mice mice) {
        return false;
        //        if (mice.getPower() > totalTrapPower()) {
        //
        //        }
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

    public Long totalTrapPower() {
        return (cat.getPower() + base.getPower()) * (1 + (cat.getBonus() + base.getBonus()));
    }

    public Long totalTrapLuck() {
        return cat.getLuck() + base.getLuck();
    }
}
