package moe.ksmz.rodentraid.sck.Service;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import moe.ksmz.rodentraid.sck.Domain.Location;

public class LocationCsvProcessor extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String rawLocations)
            throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return new Location(rawLocations);
    }
}
