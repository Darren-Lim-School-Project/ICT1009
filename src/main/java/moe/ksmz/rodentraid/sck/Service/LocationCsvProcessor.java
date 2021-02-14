package moe.ksmz.rodentraid.sck.Service;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.util.LinkedHashSet;
import java.util.Set;
import moe.ksmz.rodentraid.sck.Domain.Location;

public class LocationCsvProcessor extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String rawLocations)
            throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Set<Location> newLocations = new LinkedHashSet<>();
        for (var location : rawLocations.split(",")) {
            newLocations.add(new Location(location));
        }

        return newLocations;
    }
}
