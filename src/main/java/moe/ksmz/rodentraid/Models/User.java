package moe.ksmz.rodentraid.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moe.ksmz.rodentraid.sck.Domain.Rank;

@Entity
@Table(name = "`user`")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User implements Serializable {
    @Serial private static final long serialVersionUID = 42069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Long gold;

    private Long points;

    private String trap;

    private String base;

    private String location;

    private Long bait;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    @OrderBy("createdAt DESC")
    private List<Hunt> hunts = new ArrayList<>();

    public User(
            Long id,
            String name,
            String email,
            String password,
            Long gold,
            Long points,
            String trap,
            String base,
            String location,
            Long bait) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gold = gold;
        this.points = points;
        this.trap = trap;
        this.base = base;
        this.location = location;
        this.bait = bait;
    }

    public Rank getCurrentRank() {
        return new Rank(this.points);
    }

    public void increaseGold(Long by) {
        this.gold += by;
    }

    public void increasePoints(Long by) {
        this.points += by;
    }

    public void buyBait(Long by) {
        // 1 bait costs 200
        var total = by * 200;
        var startingGold = this.gold;

        startingGold -= total;
        if (startingGold < 0) {
            throw new InsufficientCreditsException();
        }

        this.gold = startingGold;
        this.bait += by;
    }

    public void decrementBait() {
        if (!sufficientBait()) {
            throw new InsufficientBaitException();
        }

        this.bait--;
    }

    public boolean sufficientBait() {
        return this.bait > 0;
    }
}
