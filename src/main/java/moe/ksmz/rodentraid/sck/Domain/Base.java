package moe.ksmz.rodentraid.sck.Domain;

import moe.ksmz.rodentraid.sck.Domain.Contracts.Trappable;

public class Base implements Trappable {
    private final Long power;
    private final Long bonus;
    private final Long luck;

    public Base(Long power, Long bonus, Long luck) {
        this.power = power;
        this.bonus = bonus;
        this.luck = luck;
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
