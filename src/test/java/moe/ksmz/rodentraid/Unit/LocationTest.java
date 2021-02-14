package moe.ksmz.rodentraid.Unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import moe.ksmz.rodentraid.sck.Domain.Location;
import org.junit.jupiter.api.Test;

public class LocationTest {
    @Test
    void locationOverrides() {
        assertThat(new Location("Mountain")).isEqualTo(new Location("Mountain"));
        assertThat(new Location("mountain")).isEqualTo(new Location("Mountain"));

        var arr = new ArrayList<Location>();
        arr.add(new Location("Mountain"));

        assertThat(arr.contains(new Location("Mountain"))).isTrue();
        assertThat(arr).contains(new Location("Mountain"));
        assertThat(arr).contains(new Location("mountain"));
    }
}
