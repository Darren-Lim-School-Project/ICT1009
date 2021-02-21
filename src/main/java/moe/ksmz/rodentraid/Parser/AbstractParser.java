package moe.ksmz.rodentraid.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParser<T> {
    InputStream inputStream;

    public AbstractParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    abstract T parseLine(String line);

    public List<T> parse() {
        BufferedReader csvReader = null;
        String csvRecord;
        List<T> csvList = new ArrayList<>();

        try {
            csvReader =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            // skip 1st line
            csvReader.readLine();
            while ((csvRecord = csvReader.readLine()) != null) {
                csvList.add(parseLine(csvRecord));
            }
        } catch (IOException e) {
            throw new RuntimeException("Reading CSV failed.", e);
        } finally {
            if (csvReader != null)
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return csvList;
    }
}
