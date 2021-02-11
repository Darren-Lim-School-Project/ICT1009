package moe.ksmz.rodentraid.sck.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import moe.ksmz.rodentraid.sck.Domain.Mice;
import moe.ksmz.rodentraid.sck.Service.Contracts.MiceManager;
import org.springframework.stereotype.Service;

@Service
public class MiceService implements MiceManager {
    private boolean loaded = false;
    private List<Mice> miceList = null;
    private static final String path = "C:\\Users\\voltz\\IdeaProjects\\ict1009-team64-2021\\assets\\csv\\mouse-power-effs1.csv";

    @Override
    public List<Mice> loadEntries() {
        try {
            miceList = new CsvToBeanBuilder<Mice>(new FileReader(path))
                    .withType(Mice.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loaded = true;
        return null;
    }
}
