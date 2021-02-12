package moe.ksmz.rodentraid.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moe.ksmz.rodentraid.sck.Domain.Rank;

@Entity
@Table(name = "`user`")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Long points;

    public User(Long id, String name, String email, String password, Long points) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.points = points;
    }

    public Rank getCurrentRank() {
        return new Rank(this.points);
    }
}
