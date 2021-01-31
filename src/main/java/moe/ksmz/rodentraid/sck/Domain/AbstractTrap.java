package moe.ksmz.rodentraid.sck.Domain;

import moe.ksmz.rodentraid.sck.Domain.Contracts.Trappable;

public abstract class AbstractTrap implements Trappable {
    protected final String name;
    protected final Long power;
    protected final Long bonus;
    protected final Long luck;

    public AbstractTrap(String name, Long power, Long bonus, Long luck) {
        this.name = name;
        this.power = power;
        this.bonus = bonus;
        this.luck = luck;
    }

    public String getName() {
        return name;
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
