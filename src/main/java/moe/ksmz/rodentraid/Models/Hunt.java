package moe.ksmz.rodentraid.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moe.ksmz.rodentraid.sck.Domain.CatchState;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Hunt implements Serializable {
    @Serial private static final long serialVersionUID = 42069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mouse;

    @Column(nullable = false)
    private Long weight;

    private String catchOutcome;

    private CatchState catchState;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
}
