package moe.ksmz.rodentraid.Parser;

import java.io.InputStream;
import moe.ksmz.rodentraid.sck.Domain.Cat;
import moe.ksmz.rodentraid.sck.Domain.TrapTypes;

public class CatParser extends AbstractParser<Cat> {

    public CatParser(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    Cat parseLine(String line) {
        var split = line.split(",");
        var parsedTrap =
                switch (split[1]) {
                    case "Tactical" -> TrapTypes.TACTICAL;
                    case "Physical" -> TrapTypes.PHYSICAL;
                    case "Hydro" -> TrapTypes.HYDRO;
                    case "Shadow" -> TrapTypes.SHADOW;
                    default -> throw new RuntimeException("invalid type");
                };

        return new Cat(
                split[0],
                Long.parseLong(split[2]),
                Long.parseLong(split[3]),
                Long.parseLong(split[5]),
                Integer.parseInt(split[6]),
                parsedTrap);
    }
}
