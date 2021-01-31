package moe.ksmz.rodentraid.Unit;

import static org.assertj.core.api.Assertions.assertThat;

import moe.ksmz.rodentraid.sck.Domain.Freshness;
import org.junit.jupiter.api.Test;

class FreshnessTest {

    @Test
    void of() {
        var fresh = Freshness.of(1);
        assertThat(fresh).hasToString("Fresh");
    }

    @Test
    void combined() {
        assertThat(Freshness.combined(Freshness.of(-6), Freshness.of(6)))
                .isEqualTo(Freshness.of(0))
                .hasToString("No Effect");

        assertThat(Freshness.combined(Freshness.of(-999), Freshness.of(999)))
                .isEqualTo(Freshness.of(0))
                .hasToString("No Effect");

        assertThat(Freshness.combined(Freshness.of(1), Freshness.of(2)))
                .isEqualTo(Freshness.of(3))
                .hasToString("Extremely Fresh");

        assertThat(Freshness.combined(Freshness.of(-1), Freshness.of(6)))
                .isEqualTo(Freshness.of(5))
                .hasToString("Ultimately Fresh");
    }

    @Test
    void constrainToRange() {
        assertThat(Freshness.of(-9)).isEqualTo(Freshness.of(-6));
        assertThat(Freshness.of(9)).isEqualTo(Freshness.of(6));
        assertThat(Freshness.of(4)).isEqualTo(Freshness.of(4));
    }
}
