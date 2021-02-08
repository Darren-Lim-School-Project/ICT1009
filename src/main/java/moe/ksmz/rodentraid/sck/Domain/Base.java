package moe.ksmz.rodentraid.sck.Domain;

public class Base extends AbstractTrap {
    public Base(String name, Long power, Long bonus, Long luck, Integer freshness) {
        super(name, power, bonus, luck, freshness);
    }

    @Override
    public Long getBonus() {
        return bonus / 2;
    }

    @Override
    public Long getLuck() {
        return luck / 2;
    }
}
