package moe.ksmz.rodentraid.Unit;

import static org.assertj.core.api.Assertions.assertThat;

import moe.ksmz.rodentraid.sck.Domain.Rank;
import org.junit.jupiter.api.Test;

public class RankTest {
    Rank buildRank(Long points) {
        return new Rank(points);
    }

    @Test
    void getRankTitle() {
        assertThat(buildRank(7500L).getRankTitle()).isEqualTo("apprentice");
        assertThat(buildRank(18749L).getRankTitle()).isEqualTo("initiate");
        assertThat(buildRank(2125896057L).getRankTitle()).isEqualTo("sage");
        assertThat(buildRank(2125896059L).getRankTitle()).isEqualTo("sage");
    }
}
