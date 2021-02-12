package moe.ksmz.rodentraid.sck.Domain;

import com.opencsv.bean.CsvBindByName;

public class Mice {

    @CsvBindByName(column = "Mouse")
    private String name;

    @CsvBindByName(column = "Power")
    private Long power;

    @CsvBindByName(column = "Gold")
    private Long gold;

    @CsvBindByName(column = "Points")
    private Long points;

    public Mice() {
        /* Needs the default constructor for OpenCSV to work for some reason
         *  So the variables can't be final ;-; */
    }

    public Mice(String name, Long gold, Long points, Long power) {
        this.name = name;
        this.gold = gold;
        this.points = points;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Mice{"
                + "name='"
                + name
                + '\''
                + ", power="
                + power
                + ", gold="
                + gold
                + ", points="
                + points
                + '}';
    }

    // TODO: finish
    public boolean hasNonStandardEffectAgainst(TrapTypes trapType) {
        return false;
    }

    // TODO: finish
    public Long getEffectivenessAgainst(TrapTypes trapType) {
        return 0L;
    }
}
