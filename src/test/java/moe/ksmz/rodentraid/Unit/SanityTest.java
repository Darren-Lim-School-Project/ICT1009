package moe.ksmz.rodentraid.Unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SanityTest {
    @Test
    void IsWorking() {
        assertThat(1 + 1).isEqualTo(2);
        assertThat(1 + 1).isNotEqualTo(4);
    }
}
