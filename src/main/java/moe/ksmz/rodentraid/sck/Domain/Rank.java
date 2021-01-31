package moe.ksmz.rodentraid.sck.Domain;

public class Rank {
    private Long points;

    public Rank(Long points) {
        this.points = points;
    }

    public Long getPoints() {
        return points;
    }

    public String getRankTitle() {
        return "";
    }

    public float getPercentage() {
        return 0;
    }
}
