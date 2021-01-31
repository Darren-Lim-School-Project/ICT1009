package moe.ksmz.rodentraid.sck.Domain;

public class Cat extends AbstractTrap {
    private final TrapTypes type;

    public Cat(String name, Long power, Long bonus, Long luck, Integer freshness, TrapTypes type) {
        super(name, power, bonus, luck, freshness);
        this.type = type;
    }
}
