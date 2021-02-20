package moe.ksmz.rodentraid.sck.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javafaker.Faker;
import com.opencsv.bean.*;
import java.util.Set;
import lombok.ToString;
import moe.ksmz.rodentraid.sck.Service.LocationCsvProcessor;

@ToString
@JsonIgnoreProperties("randWeight")
public class Mice {

    @CsvBindByName(column = "mouse")
    private String name;

    @CsvBindByName private Long power;

    @CsvBindByName private Long gold;

    @CsvBindByName private Long points;

    @CsvBindAndSplitByName(
            elementType = Location.class,
            splitOn = ",",
            converter = LocationCsvProcessor.class)
    private Set<Location> locations;

    @CsvBindByName private Long physical;
    @CsvBindByName private Long tactical;
    @CsvBindByName private Long shadow;
    @CsvBindByName private Long hydro;

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

    public void setPhysical(Long physical) {
        this.physical = physical;
    }

    public void setTactical(Long tactical) {
        this.tactical = tactical;
    }

    public void setShadow(Long shadow) {
        this.shadow = shadow;
    }

    public void setHydro(Long hydro) {
        this.hydro = hydro;
    }

    public Long getEffectivenessFor(TrapTypes type) {
        return switch (type) {
                    case PHYSICAL -> physical;
                    case TACTICAL -> tactical;
                    case SHADOW -> shadow;
                    case HYDRO -> hydro;
                }
                / 100;
    }

    public Long getRandWeight() {
        return (new Faker()).number().numberBetween(1L, 100L);
    }
}
