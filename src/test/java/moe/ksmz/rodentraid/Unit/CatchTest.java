package moe.ksmz.rodentraid.Unit;
import static org.assertj.core.api.Assertions.*;

import moe.ksmz.rodentraid.sck.Domain.*;
import org.junit.jupiter.api.Test;

public class CatchTest {
    @Test
    void attemptCatch() {
        var m = new Mice();
        m.setName("test mice");
        m.setPower(2300L);
        m.setPhysical(100L);
        var c = new Cat("test cat", 300L,1L, 1L, 1, TrapTypes.PHYSICAL);
        var b = new Base("test base", 300L, 1L, 1L, 1);

        var wep = new Weapon(c, b);
        System.out.println("AttempCatch: " + wep.attemptCatch(m));
    }
}
