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

    private Long weight;

    @CsvBindAndSplitByName(
            elementType = Location.class,
            splitOn = ",",
            converter = LocationCsvProcessor.class)
    private Set<Location> locations;

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

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Long getWeight() {
        if (this.weight == null) {
            this.weight = (new Faker()).number().numberBetween(1L, 100L);
        }

        return this.weight;
    }
}
