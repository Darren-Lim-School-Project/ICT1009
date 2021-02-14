package moe.ksmz.rodentraid.sck.Domain;

import com.github.javafaker.Faker;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import java.util.Set;
import lombok.ToString;
import moe.ksmz.rodentraid.sck.Service.LocationCsvProcessor;

@ToString
public class Mice {

    @CsvBindByName(column = "mouse")
    private String name;

    @CsvBindByName(column = "power")
    private Long power;

    @CsvBindByName(column = "gold")
    private Long gold;

    @CsvBindByName(column = "points")
    private Long points;

    @CsvBindAndSplitByName(
            elementType = Location.class,
            splitOn = ",",
            converter = LocationCsvProcessor.class)
    private Set<Location> locations;

    public Mice() {}

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

    public Long getRandomWeight() {
        return (new Faker()).number().numberBetween(1L, 100L);
    }
}
