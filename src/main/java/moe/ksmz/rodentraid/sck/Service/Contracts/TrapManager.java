package moe.ksmz.rodentraid.sck.Service.Contracts;

import java.util.List;
import java.util.Optional;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.sck.Domain.Base;
import moe.ksmz.rodentraid.sck.Domain.Cat;
import moe.ksmz.rodentraid.sck.Domain.Weapon;

public interface TrapManager extends Loadable<Weapon> {

    Weapon getWeaponFor(User user);

    List<Cat> allTraps();

    List<Base> allBases();

    Optional<Cat> getCat(String catName);

    Optional<Base> getBase(String baseName);
}
