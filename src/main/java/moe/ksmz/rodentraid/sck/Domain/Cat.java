package moe.ksmz.rodentraid.sck.Domain;

import moe.ksmz.rodentraid.sck.Domain.Contracts.HasPower;

public class Cat implements HasPower {
    private Long power;
    private Long bonus;
    private Long luck;

    public Cat(Long power, Long bonus, Long luck) {
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
