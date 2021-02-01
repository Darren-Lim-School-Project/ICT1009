package moe.ksmz.rodentraid.sck.Domain;

import moe.ksmz.rodentraid.sck.Domain.Contracts.Trappable;

public abstract class AbstractTrap implements Trappable {
    protected final String name;
    protected final Long power;
    protected final Long bonus;
    protected final Long luck;
    protected final Freshness freshness;

    public AbstractTrap(String name, Long power, Long bonus, Long luck, Integer freshness) {
        this(name, power, bonus, luck, Freshness.of(freshness));
    }

    public AbstractTrap(String name, Long power, Long bonus, Long luck, Freshness freshness) {
        this.name = name;
        this.power = power;
        this.bonus = bonus;
        this.luck = luck;
        this.freshness = freshness;
    }

    public String getName() {
        return name;
    }

    public Freshness getFreshness() {
        return freshness;
    }

    @Override
    public Long getPower() {
        return power;
    }

    @Override
    public Long getBonus() {
        return bonus;
    }

    @Override
    public Long getLuck() {
        return luck;
    }
}
