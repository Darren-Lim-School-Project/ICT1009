package moe.ksmz.rodentraid.sck.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.Models.User;
import moe.ksmz.rodentraid.Parser.BaseParser;
import moe.ksmz.rodentraid.Parser.CatParser;
import moe.ksmz.rodentraid.sck.Domain.Base;
import moe.ksmz.rodentraid.sck.Domain.Cat;
import moe.ksmz.rodentraid.sck.Domain.Weapon;
import moe.ksmz.rodentraid.sck.Service.Contracts.TrapManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrapService implements TrapManager {
    private List<Cat> cats;

    private List<Base> bases;

    @Value("classpath:weapons.csv")
    Resource catResource;

    @Value("classpath:bases.csv")
    Resource baseResource;

    @Override
    public void loadEntries() throws IOException {
        var catParser = new CatParser(catResource.getInputStream());
        cats = catParser.parse();
        var baseParser = new BaseParser(baseResource.getInputStream());
        bases = baseParser.parse();
        log.info("Loaded {} cats", cats.size());
        log.info("Loaded {} bases", bases.size());
    }

    @Override
    public Weapon getWeaponFor(User user) {
        return new Weapon(getCat(user.getTrap()).get(), getBase(user.getBase()).get());
    }

    @Override
    public List<Cat> allTraps() {
        return cats;
    }

    @Override
    public List<Base> allBases() {
        return bases;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Cat> getCat(String catName) {
        return cats.stream().filter(cat -> cat.getName().equalsIgnoreCase(catName)).findAny();
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Base> getBase(String baseName) {
        return bases.stream().filter(base -> base.getName().equalsIgnoreCase(baseName)).findAny();
    }
}
