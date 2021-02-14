package moe.ksmz.rodentraid.sck.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MiceService implements MiceManager {
    private List<Mice> mice;

    @Value("classpath:out.csv")
    Resource resourceFile;

    @Override
    public void loadEntries() throws IOException {
        mice =
                new CsvToBeanBuilder<Mice>(new FileReader(resourceFile.getFile()))
                        .withType(Mice.class)
                        .build()
                        .parse();
        log.info("Loaded {} mice", mice.size());
    }
}
