package moe.ksmz.rodentraid.sck.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MiceService implements MiceManager {
    private boolean loaded = false;
    private List<Mice> miceList;

    @Value("classpath:mouse-power-effs1.csv")
    Resource resourceFile;

    @Override
    public List<Mice> loadEntries() throws IOException {
        miceList =
                new CsvToBeanBuilder<Mice>(new FileReader(resourceFile.getFile()))
                        .withType(Mice.class)
                        .build()
                        .parse();
        loaded = true;
        return null;
    }
}
