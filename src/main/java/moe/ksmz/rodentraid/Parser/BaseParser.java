package moe.ksmz.rodentraid.Parser;

import java.io.InputStream;
import moe.ksmz.rodentraid.sck.Domain.Base;

public class BaseParser extends AbstractParser<Base> {
    public BaseParser(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    Base parseLine(String line) {
        var split = line.split(",");
        return new Base(
                split[0],
                Long.parseLong(split[1]),
                Long.parseLong(split[2]),
                Long.parseLong(split[4]),
                Integer.parseInt(split[5]));
    }
}
